package ru.melnikov.RestAPI.RestAPI_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.melnikov.RestAPI.RestAPI_server.models.Measurement;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
}
