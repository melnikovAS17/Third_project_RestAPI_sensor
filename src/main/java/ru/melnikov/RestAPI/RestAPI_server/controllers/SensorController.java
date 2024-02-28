package ru.melnikov.RestAPI.RestAPI_server.controllers;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.melnikov.RestAPI.RestAPI_server.dto.SensorDTO;
import ru.melnikov.RestAPI.RestAPI_server.services.SensorService;
import ru.melnikov.RestAPI.RestAPI_server.utils.groups.AreaOfVisibility;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.methodsEnum.Ex;
import ru.melnikov.RestAPI.RestAPI_server.utils.converter.ErrorConvertMessageLogic;
import ru.melnikov.RestAPI.RestAPI_server.utils.converter.ConverterDTO;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final ErrorConvertMessageLogic errorConvertMessageLogic;
    private final SensorService sensorService;
    private final ConverterDTO converterDTO;
    @Autowired
    public SensorController(ErrorConvertMessageLogic errorConvertMessageLogic, SensorService sensorService, ConverterDTO converterDTO) {
        this.errorConvertMessageLogic = errorConvertMessageLogic;
        this.sensorService = sensorService;
        this.converterDTO = converterDTO;
    }
    @JsonView(AreaOfVisibility.Public.class)//Видны все поля кроме Private группы
    @GetMapping
    public ResponseEntity<List<SensorDTO>> getAllSensors(){
        return ResponseEntity.ok(sensorService.getAllSensors().stream()
                .map(converterDTO::convertToSensorDTO)
                .collect(Collectors.toList()));
    }
    @JsonView(AreaOfVisibility.Private.class)//Видны все поля
    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> getSensorInfo(@PathVariable("id") int id){
        return ResponseEntity.ok( converterDTO.convertToSensorDTO(sensorService.getSensorInfo(id)));
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<SensorDTO>> redirectOnSearchPage(@PathVariable("name") String name){
        return ResponseEntity.ok(sensorService.getSensorInfoByName(name).stream()
                .map(converterDTO::convertToSensorDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping//TODO добавить спринг валидатор на уникальность имени сеснора
    public ResponseEntity<HttpStatus> addSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                BindingResult notAdded){
        //Вынес логику коннкотенации сообщ об ошибки в отдельный класс
        //Для того чтобы хендлер ловил нужное исключение добавил костыль(enum.Метод)
        if(notAdded.hasErrors()) errorConvertMessageLogic.exceptionMessage(notAdded, Ex.ADD);
        sensorService.addSensor(converterDTO.convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> editSensorName(@PathVariable("id") int id, @RequestBody @Valid SensorDTO sensorDTO,BindingResult notEdit){
        if(notEdit.hasErrors()) errorConvertMessageLogic.exceptionMessage(notEdit, Ex.EDIT);
        sensorService.editSensorName(id,converterDTO.convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSensor(@PathVariable("id") int id){
        sensorService.deleteSensor(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //For method - getSensorInfo
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e){
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor not found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

    //For method - addSensor
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorAddException e){
        SensorErrorResponse response = new SensorErrorResponse(
                "Not added: " + e.getMessage() + ".",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    //For method - editSensorName
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(IncorrectEntry e){
        SensorErrorResponse response = new SensorErrorResponse(
               "Not edit: " + e.getMessage() + ".",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
