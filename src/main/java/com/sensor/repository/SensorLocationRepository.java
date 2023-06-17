package com.sensor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sensor.entity.SensorLocation;
import com.sensor.entity.SensorLocationKey;

@Repository
public interface SensorLocationRepository extends JpaRepository<SensorLocation, SensorLocationKey>{

	SensorLocation findSensorLocationByLocationIdAndSensorId(Long locationId, Long sensorId);
	List<SensorLocation> findByLocationId(Long locationId);
}
