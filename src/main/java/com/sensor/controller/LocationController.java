package com.sensor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sensor.dto.LocationDTO;
import com.sensor.service.LocationService;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/location")
public class LocationController {

	@Autowired
	private LocationService locationService;

	@PostMapping("/addLocation")
	public LocationDTO addLocation(@RequestBody @Valid LocationDTO locationDTO) {
		return locationService.addLocation(locationDTO);
	}

}
