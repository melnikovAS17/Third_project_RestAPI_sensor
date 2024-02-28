package ru.melnikov.RestAPI.RestAPI_server.utils.converter;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.methodsEnum.Ex;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.IncorrectEntry;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.SensorAddException;

import java.util.List;

@Component
public class ErrorConvertMessageLogic {

    public void exceptionMessage(BindingResult bindingResult, Enum<Ex> s){

        StringBuilder errorsMsg = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for(FieldError f: errors){
            errorsMsg.append(f.getField())
                    .append(" - ")
                    .append(f.getDefaultMessage())
                    .append(";");
        }
        //Костыль для передачи сообщ в нужное исключение //TODO
        if (s.equals(Ex.ADD)) {
            throw new SensorAddException(errorsMsg.toString());
        } else if (s.equals(Ex.EDIT)) {
            throw new IncorrectEntry(errorsMsg.toString());
        }else {
            System.out.println("Exception not caught by any of the catchers");
        }
    }
}
