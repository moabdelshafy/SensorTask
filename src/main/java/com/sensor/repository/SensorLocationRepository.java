package com.sensor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sensor.entity.SensorLocation;
import com.sensor.entity.SensorLocationKey;

@Repository
public interface SensorLocationRepository extends JpaRepository<SensorLocation, SensorLocationKey>{

}
