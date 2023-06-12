package com.sensor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sensor.entity.Sensor;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long>{

}
