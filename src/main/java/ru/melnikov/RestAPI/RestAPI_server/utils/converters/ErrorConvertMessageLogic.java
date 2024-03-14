package ru.melnikov.RestAPI.RestAPI_server.utils.converters;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.enums.ExMethodSignature;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.measurement.MeasurementAddException;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor.IncorrectEntry;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor.SensorAddException;

import java.util.List;

@Component
public class ErrorConvertMessageLogic {

    public void exceptionMessage(BindingResult bindingResult, Enum<ExMethodSignature> s){

        StringBuilder errorsMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for(FieldError f: errors){
            errorsMsg.append(f.getField())
                    .append(" - ")
                    .append(f.getDefaultMessage())
                    .append(";");
        }
        //Костыль для передачи сообщ в нужное исключение //TODO
        if (s.equals(ExMethodSignature.ADD_SENSOR)) {
            throw new SensorAddException(errorsMsg.toString());
        } if (s.equals(ExMethodSignature.EDIT_SENSOR)) {
            throw new IncorrectEntry(errorsMsg.toString());
        } if (s.equals(ExMethodSignature.ADD_MEASUREMENT)) {
            throw new MeasurementAddException(errorsMsg.toString());
        }else {
            System.out.println("Exception not caught by any of the catchers");
        }
    }
}
