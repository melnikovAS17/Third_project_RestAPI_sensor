package ru.melnikov.RestAPI.RestAPI_server.utils.errors;

public class SensorAddException extends RuntimeException {

    public SensorAddException(String message){
        super(message);
    }
}
