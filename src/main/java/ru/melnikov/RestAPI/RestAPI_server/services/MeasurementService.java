package ru.melnikov.RestAPI.RestAPI_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.RestAPI.RestAPI_server.models.Measurement;
import ru.melnikov.RestAPI.RestAPI_server.models.Sensor;
import ru.melnikov.RestAPI.RestAPI_server.repositories.MeasurementRepository;
import ru.melnikov.RestAPI.RestAPI_server.repositories.SensorRepository;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor.SensorNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;
    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
    }

    public List<Measurement> getAllMeasurements(){
        return measurementRepository.getAllMeasurementsAndTheirSensor();
    }

    public int getRainyDaysCount(){
        return measurementRepository.getAllRainyDays().size();
    }

    @Transactional
    public void addMeasurement(Measurement measurement){//TODO добавить перехватчик исключений
        float temperature = measurement.getTemperature();
        boolean raining = measurement.getRaining();
        Sensor sensor = sensorRepository.getSensorByName(measurement.getSensor().getName()).orElseThrow(SensorNotFoundException::new);
        int sensorId = sensor.getId();
        measurementRepository.addMeasurementWithSensorName(temperature,raining,sensorId);
    }
}
