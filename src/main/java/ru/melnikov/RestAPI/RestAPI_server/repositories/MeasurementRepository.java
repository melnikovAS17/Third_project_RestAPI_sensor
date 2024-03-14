package ru.melnikov.RestAPI.RestAPI_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.melnikov.RestAPI.RestAPI_server.models.Measurement;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {

    @Modifying
    @Query(value = "select measurements.*, sensor.name from measurements " +
            "join sensor on sensor.id = measurements.sensor_id", nativeQuery = true)
    List<Measurement> getAllMeasurementsAndTheirSensor();

    @Modifying
    @Query(value = "insert into measurements (temperature, raining, sensor_id) " +
            "VALUES(?,?,?)" , nativeQuery = true)
    void addMeasurementWithSensorName(@Param("temperature") float temperature, @Param("raining") boolean raining,
                                      @Param("sensor_id") int sensorId);

    @Modifying
    @Query(value = "SELECT * from measurements where raining = true", nativeQuery = true)
    //TODO
    List<Measurement> getAllRainyDays();

}
