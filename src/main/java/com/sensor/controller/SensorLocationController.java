package com.sensor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sensor.dto.SensorLocationDTO;
import com.sensor.service.SensorLocationService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/sensorLocation")
public class SensorLocationController {

	@Autowired
	private SensorLocationService locationSensorService;

	@PostMapping("/addSensorsToLocation")
	public SensorLocationDTO addSensorsToLocation(@RequestBody @Valid SensorLocationDTO locationSensorDTO) {
		return locationSensorService.addSensorsToLocation(locationSensorDTO);
	}

}
