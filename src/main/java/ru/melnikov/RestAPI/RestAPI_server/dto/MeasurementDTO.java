package ru.melnikov.RestAPI.RestAPI_server.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.melnikov.RestAPI.RestAPI_server.models.Sensor;
import ru.melnikov.RestAPI.RestAPI_server.utils.groups.AreaOfVisibility;



public class MeasurementDTO {

    @JsonView(AreaOfVisibility.Public.class)
    @Min(value = -100, message = "should be higher than -100")
    @Max(value = 100, message = "should be lower than 100")
    private float temperature;

    @JsonView(AreaOfVisibility.Public.class)
    @NotBlank(message = "shouldn't be empty")
    private boolean raining;


    private String sensorName;

    @NotBlank(message = "shouldn't be empty")
    @JsonView(AreaOfVisibility.Private.class)
    private Sensor sensor;

    @JsonView(AreaOfVisibility.RainyDays.class)
    private Integer rainyDaysCount;

    public Integer getRainyDaysCount() {
        return rainyDaysCount;
    }

    public void setRainyDaysCount(Integer rainyDaysCount) {
        this.rainyDaysCount = rainyDaysCount;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public String getSensorName() {
        return sensor.getName();
    }


    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public boolean getRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

}
