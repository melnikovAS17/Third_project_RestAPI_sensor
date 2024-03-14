package ru.melnikov.RestAPI.RestAPI_server.utils.errors.measurement;

public class MeasurementAddException extends RuntimeException{

    public MeasurementAddException(String message){
        super(message);
    }
}
