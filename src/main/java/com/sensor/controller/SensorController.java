package com.sensor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sensor.dto.SensorDTO;
import com.sensor.service.SensorService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/sensor")
public class SensorController {

	@Autowired
	private SensorService sensorService;

	@PostMapping("/addSensor")
	public SensorDTO addSensor(@RequestBody @Valid SensorDTO sensorDTO) {
		return sensorService.addSensor(sensorDTO);
	}

}
