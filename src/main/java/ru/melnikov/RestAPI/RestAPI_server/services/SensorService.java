package ru.melnikov.RestAPI.RestAPI_server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.RestAPI.RestAPI_server.models.Sensor;
import ru.melnikov.RestAPI.RestAPI_server.repositories.SensorRepository;
import ru.melnikov.RestAPI.RestAPI_server.utils.errors.sensor.SensorNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

//Здесь работаем на уровне Sensor , а не SensorDTO - конвертация происходит в контроллере
@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;
    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }


    public List<Sensor> getAllSensors(){
        return sensorRepository.findAll();
    }

    public Sensor getSensorInfo(int id) {
        return sensorRepository.findById(id).orElseThrow(SensorNotFoundException::new);
    }

    public List<Sensor> getSensorInfoByName(String name){
        return sensorRepository.getSensorByNameStartingWith(name).orElseThrow(SensorNotFoundException::new);
    }

    @Transactional
    public void editSensorName(int id, Sensor sensor){
        //Проверка на правильность ввода id сенсора тк такого может не существовать
        getSensorInfo(id);
        sensorRepository.getSensorByIdAndUpdateName(sensor.getName(),id);
    }

    @Transactional
    public void addSensor(Sensor sensor){
        sensor.setCreatedAt(new Date());
        sensorRepository.save(sensor);
    }

    @Transactional
    public void deleteSensor(int id){
        getSensorInfo(id);
        sensorRepository.deleteById(id);
    }

    //Method for validating name unique
    public Optional<Sensor> nameUniqueChecker(String name){
        return sensorRepository.getSensorByName(name).stream().findAny();
    }
}
