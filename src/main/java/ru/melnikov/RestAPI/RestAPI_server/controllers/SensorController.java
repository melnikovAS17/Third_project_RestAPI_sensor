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
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor.IncorrectEntry;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor.SensorAddException;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor.SensorErrorResponse;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor.SensorNotFoundException;
import ru.melnikov.RestAPI.RestAPI_server.utils.groups.AreaOfVisibility;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.enums.ExMethodSignature;
import ru.melnikov.RestAPI.RestAPI_server.utils.converters.ErrorConvertMessageLogic;
import ru.melnikov.RestAPI.RestAPI_server.utils.converters.ConverterDTO;
import ru.melnikov.RestAPI.RestAPI_server.utils.validations.sensor.UniqueNameSensorValidator;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final ErrorConvertMessageLogic errorConvertMessageLogic;
    private final SensorService sensorService;
    private final ConverterDTO converterDTO;

    private final UniqueNameSensorValidator validator;
    @Autowired
    public SensorController(ErrorConvertMessageLogic errorConvertMessageLogic, SensorService sensorService,
                            ConverterDTO converterDTO, UniqueNameSensorValidator validator) {
        this.errorConvertMessageLogic = errorConvertMessageLogic;
        this.sensorService = sensorService;
        this.converterDTO = converterDTO;
        this.validator = validator;
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

    //Добавил метод поиска, тело пустое, передаю имя в параметр, далее конвертирую в DTO все
    //полученные элементы и собираю в коллекцию
    @GetMapping("/search/{name}")
    public ResponseEntity<List<SensorDTO>> searchSensorByName(@PathVariable("name") String name){
        return ResponseEntity.ok(sensorService.getSensorInfoByName(name).stream()
                .map(converterDTO::convertToSensorDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> addSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                BindingResult notAdded){
        //Валидация уникальности имени сенсора
        validator.validate(converterDTO.convertToSensor(sensorDTO),notAdded);
        //Вынес логику коннкотенации сообщ об ошибки в отдельный класс
        //Для того чтобы хендлер ловил нужное исключение добавил костыль(enum.Метод)
        if(notAdded.hasErrors()) errorConvertMessageLogic.exceptionMessage(notAdded, ExMethodSignature.ADD_SENSOR);
        sensorService.addSensor(converterDTO.convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> editSensorName(@PathVariable("id") int id, @RequestBody @Valid SensorDTO sensorDTO,BindingResult notEdit){
        if(notEdit.hasErrors()) errorConvertMessageLogic.exceptionMessage(notEdit, ExMethodSignature.EDIT_SENSOR);
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
                "Not added: " + e.getMessage() + "!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    //For method - editSensorName
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(IncorrectEntry e){
        SensorErrorResponse response = new SensorErrorResponse(
               "Not edit: " + e.getMessage() + "!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
