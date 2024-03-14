package ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor;

public class SensorAddException extends RuntimeException {

    public SensorAddException(String message){
        super(message);
    }
}
