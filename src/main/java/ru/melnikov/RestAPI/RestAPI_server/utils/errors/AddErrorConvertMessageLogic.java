package ru.melnikov.RestAPI.RestAPI_server.utils.errors;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Component
public class AddErrorConvertMessageLogic {

    public void addExceptionMessage(BindingResult bindingResult){
        StringBuilder errorsMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for(FieldError f: errors){
            errorsMsg.append(f.getField())
                    .append(" - ")
                    .append(f.getDefaultMessage())
                    .append(";");
        }
        throw new SensorAddException(errorsMsg.toString());
    }
}
