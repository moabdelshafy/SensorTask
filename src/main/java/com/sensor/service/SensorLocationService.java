package com.sensor.service;

import java.util.List;

import com.sensor.dto.SensorLocationDTO;
import com.sensor.entity.SensorLocation;

public interface SensorLocationService {
	
	public SensorLocationDTO addSensorsToLocation(SensorLocationDTO sensorLocationDTO);
	public SensorLocationDTO findByLocationIdAndSensorId(SensorLocationDTO sensorLocationDTO);
	public List<SensorLocation> findAll();
	public List<SensorLocation> findByLocationId(Long locationId);
		


}
