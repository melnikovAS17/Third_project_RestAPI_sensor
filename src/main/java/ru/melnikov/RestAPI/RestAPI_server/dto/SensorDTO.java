package ru.melnikov.RestAPI.RestAPI_server.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.melnikov.RestAPI.RestAPI_server.transfer.interfacesGroups.Details;
import ru.melnikov.RestAPI.RestAPI_server.transfer.interfacesGroups.EditName;
import ru.melnikov.RestAPI.RestAPI_server.transfer.interfacesGroups.New;

import java.util.Date;

//Валидировать поля лучше здесь
public class SensorDTO {

    //Для отображения нужных полей создаю интерфейсы в папке transfer чтобы объеденять поля в группы

    //Указываю, что поле name не должно быть null при создании новго сенсора

    @Size(min = 2, max = 50)//Spring сам пишет текст ошибки (да ещё и на русском)
    @NotNull(groups = New.class)
    @NotNull(groups = EditName.class)
    @JsonView(Details.class)
    private String name;
    //@JsonView - позваляет отображать нужные поля
    @JsonView(Details.class)
    private Date createdAt;

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
