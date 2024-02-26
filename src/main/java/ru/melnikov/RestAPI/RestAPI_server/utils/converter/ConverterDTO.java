package ru.melnikov.RestAPI.RestAPI_server.utils.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.melnikov.RestAPI.RestAPI_server.dto.SensorDTO;
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
}
