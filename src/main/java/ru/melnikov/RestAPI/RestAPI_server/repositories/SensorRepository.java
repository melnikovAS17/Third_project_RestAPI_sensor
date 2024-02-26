package ru.melnikov.RestAPI.RestAPI_server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.melnikov.RestAPI.RestAPI_server.models.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor,Integer> {

    //Запрос работает тоолько в таком виде
    //Пробовал без nativeQuery - ошибка
    //без @Modifying - ошибка
    //с alias тоже запрос не работает
    @Modifying
    @Query(value = "update sensor set name = ? where id = ?", nativeQuery = true)
    void getSensorByIdAndUpdateName(@Param("name") String name, @Param("id") int id);

}
