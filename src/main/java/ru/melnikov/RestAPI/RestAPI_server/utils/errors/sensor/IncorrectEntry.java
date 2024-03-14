package ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor;

public class IncorrectEntry extends RuntimeException {

    public IncorrectEntry(String message){
        super(message);
    }
}
