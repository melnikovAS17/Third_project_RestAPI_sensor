package ru.melnikov.RestAPI.RestAPI_server.utils.validations.sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.melnikov.RestAPI.RestAPI_server.models.Sensor;
import ru.melnikov.RestAPI.RestAPI_server.services.SensorService;

@Component
public class UniqueNameSensorValidator implements Validator {

    private final SensorService sensorService;
    @Autowired
    public UniqueNameSensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        if(sensorService.nameUniqueChecker(sensor.getName()).isPresent())
            errors.rejectValue("name", "", "already taken");
    }
}
