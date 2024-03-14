package ru.melnikov.RestAPI.RestAPI_server.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Size;
import ru.melnikov.RestAPI.RestAPI_server.models.Measurement;
import ru.melnikov.RestAPI.RestAPI_server.utils.groups.AreaOfVisibility;

import java.util.Date;
import java.util.List;

//Валидировать поля лучше здесь
@JsonView(AreaOfVisibility.Public.class)
public class SensorDTO {

    //Для отображения нужных полей создаю интерфейсы в классе в папке transfer чтобы объеденять поля в группы

    @Size(min = 2, max = 50, message = "should between 2 to 50 characters")//Spring сам пишет текст ошибки (да ещё и на русском)
    private String name;
    //@JsonView - позваляет отображать нужные поля
    @JsonView(AreaOfVisibility.Private.class)
    private Date createdAt;

    @JsonView(AreaOfVisibility.Private.class)
    private List<Measurement> measurementList;

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    public void setMeasurementList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
