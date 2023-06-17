package com.sensor.service;

import java.util.List;

import com.sensor.dto.SensorDTO;
import com.sensor.entity.Sensor;

public interface SensorService {

	public SensorDTO addSensor(SensorDTO sensorDTO);
	public List<Sensor> findAll();
}
