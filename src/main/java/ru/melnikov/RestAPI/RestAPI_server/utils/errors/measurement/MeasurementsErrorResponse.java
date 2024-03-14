package ru.melnikov.RestAPI.RestAPI_server.utils.errors.measurement;

public class MeasurementsErrorResponse {

    private String message;
    private long timeStamp;

    public MeasurementsErrorResponse(String message, long timeStamp) {
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
