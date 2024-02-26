package ru.melnikov.RestAPI.RestAPI_server.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.melnikov.RestAPI.RestAPI_server.dto.SensorDTO;
import ru.melnikov.RestAPI.RestAPI_server.models.Sensor;
import ru.melnikov.RestAPI.RestAPI_server.services.SensorService;
import ru.melnikov.RestAPI.RestAPI_server.transfer.interfacesGroups.Details;
import ru.melnikov.RestAPI.RestAPI_server.transfer.interfacesGroups.EditName;
import ru.melnikov.RestAPI.RestAPI_server.transfer.interfacesGroups.New;
import ru.melnikov.RestAPI.RestAPI_server.utils.converter.ConverterDTO;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.AddErrorConvertMessageLogic;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.SensorAddException;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.SensorErrorResponse;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.SensorNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final AddErrorConvertMessageLogic addErrorConvertMessageLogic;
    private final SensorService sensorService;
    private final ConverterDTO converterDTO;
    @Autowired
    public SensorController(AddErrorConvertMessageLogic addErrorConvertMessageLogic, SensorService sensorService, ConverterDTO converterDTO) {
        this.addErrorConvertMessageLogic = addErrorConvertMessageLogic;
        this.sensorService = sensorService;
        this.converterDTO = converterDTO;
    }
    //Данный метод сгрупирован по интерфесу Details
    @JsonView(Details.class)
    @GetMapping
    public ResponseEntity<List<SensorDTO>> getAllSensors(){
        return ResponseEntity.ok(sensorService.getAllSensors().stream()
                .map(converterDTO::convertToSensorDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorDTO> getSensorInfo(@PathVariable("id") int id){
        return ResponseEntity.ok(converterDTO.convertToSensorDTO(sensorService.getSensorInfo(id)));
    }


    //Данный метод сгрупирован по интерфейсу New (с помощью аннотации @Validated - она же позволяет
    // валидировать поля по стандартным: @Size, @NotEmpty, @Pattern и тд)
    @PostMapping
    public ResponseEntity<HttpStatus> addSensor(@RequestBody /* Валидацию нужно ставить именно здесь */@Validated(New.class) SensorDTO sensorDTO,
                                                BindingResult bindingResult){
        //Вынес логику коннкотенации сообщ об ошибки в отдельный класс
        if(bindingResult.hasErrors()) addErrorConvertMessageLogic.addExceptionMessage(bindingResult);
        sensorService.addSensor(converterDTO.convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> editSensorName(@PathVariable("id") int id, @RequestBody @Validated(EditName.class) SensorDTO sensorDTO){//TODO
        //ДОБАВИТЬ EXCEP И ДОБАВИТЬ ПРОВЕРКУ НА ID ТК МЕТОД РАБОТАЕТ ДАЖЕ ЕСЛИ ВВСЕТИ ID = 100
        sensorService.editSensorName(id,converterDTO.convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //For method - getSensorInfo
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e){
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor not found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    //For method - addSensor
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorAddException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
}
