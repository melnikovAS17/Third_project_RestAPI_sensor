package ru.melnikov.RestAPI.RestAPI_server.utils.errors;

public class IncorrectEntry extends RuntimeException {

    public IncorrectEntry(String message){
        super(message);
    }
}
