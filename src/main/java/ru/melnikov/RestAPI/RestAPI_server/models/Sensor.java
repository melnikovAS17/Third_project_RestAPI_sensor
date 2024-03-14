package ru.melnikov.RestAPI.RestAPI_server.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "sensor")
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonProperty("name")
    private String name;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "sensor")
    private List<Measurement> measurementList;

    public Sensor(){}

    public Sensor(int id, String name, List<Measurement> measurementList) {
        this.id = id;
        this.name = name;
        this.measurementList = measurementList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurementList() {
        return measurementList;
    }

    public void setMeasurementList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sensor sensor = (Sensor) o;
        return id == sensor.id && Objects.equals(name, sensor.name) && Objects.equals(createdAt, sensor.createdAt) && Objects.equals(measurementList, sensor.measurementList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, createdAt, measurementList);
    }
}
