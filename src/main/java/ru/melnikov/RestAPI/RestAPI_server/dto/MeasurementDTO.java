package ru.melnikov.RestAPI.RestAPI_server.dto;

import jakarta.validation.constraints.NotEmpty;

public class MeasurementDTO {

    @NotEmpty
    private int temperature;
    @NotEmpty
    private boolean raining;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }
}
