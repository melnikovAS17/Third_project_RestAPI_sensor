package ru.melnikov.RestAPI.RestAPI_server.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.melnikov.RestAPI.RestAPI_server.dto.MeasurementDTO;
import ru.melnikov.RestAPI.RestAPI_server.services.MeasurementService;
import ru.melnikov.RestAPI.RestAPI_server.utils.converters.ConverterDTO;
import ru.melnikov.RestAPI.RestAPI_server.utils.converters.ErrorConvertMessageLogic;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.enums.ExMethodSignature;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.measurement.MeasurementAddException;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.measurement.MeasurementsErrorResponse;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor.SensorErrorResponse;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor.SensorNotFoundException;
import ru.melnikov.RestAPI.RestAPI_server.utils.groups.AreaOfVisibility;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final ConverterDTO converterDTO;

    private final ErrorConvertMessageLogic errorConvertMessageLogic;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, ConverterDTO converterDTO, ErrorConvertMessageLogic errorConvertMessageLogic) {
        this.measurementService = measurementService;
        this.converterDTO = converterDTO;
        this.errorConvertMessageLogic = errorConvertMessageLogic;
    }

    @JsonView(AreaOfVisibility.Public.class)
    @GetMapping
    public ResponseEntity<List<MeasurementDTO>> getAllMeasurements(){
        return ResponseEntity.ok(measurementService.getAllMeasurements().stream()
                .map(converterDTO::convertToMeasurementDTO)
                .collect(Collectors.toList()));
    }

    @JsonView(AreaOfVisibility.Private.class)
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult notAdded){
        if(notAdded.hasErrors()) errorConvertMessageLogic.exceptionMessage(notAdded, ExMethodSignature.ADD_MEASUREMENT);
        measurementService.addMeasurement(converterDTO.convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @JsonView(AreaOfVisibility.RainyDays.class)
    @GetMapping("/rainyDaysCount")
    public ResponseEntity<MeasurementDTO> getRainyDaysCount() {
        return ResponseEntity.ok(converterDTO.toDto(measurementService.getRainyDaysCount()));
    }


    //For method - addMeasurement
    @ExceptionHandler
    private ResponseEntity<MeasurementsErrorResponse> handleException(MeasurementAddException e){
        MeasurementsErrorResponse response = new MeasurementsErrorResponse(
                "Not added: " + e.getMessage() + "!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    //Sensor not found
    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e){
        SensorErrorResponse response = new SensorErrorResponse(
                "Sensor not found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
