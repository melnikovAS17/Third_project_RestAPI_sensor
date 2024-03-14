package ru.melnikov.RestAPI.RestAPI_server.utils.converters;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.melnikov.RestAPI.RestAPI_server.dto.MeasurementDTO;
import ru.melnikov.RestAPI.RestAPI_server.dto.SensorDTO;
import ru.melnikov.RestAPI.RestAPI_server.models.Measurement;
import ru.melnikov.RestAPI.RestAPI_server.models.Sensor;

@Component
public class ConverterDTO {

    private final ModelMapper modelMapper;
    @Autowired
    public ConverterDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO,Sensor.class);
    }

    public SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor,SensorDTO.class);
    }

    //Мапить можно и так
    public MeasurementDTO toDto(Integer count)  {
        MeasurementDTO measurementDTO = new MeasurementDTO();
        measurementDTO.setRainyDaysCount(count);
        return measurementDTO;
    }

    public Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }

    public MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
