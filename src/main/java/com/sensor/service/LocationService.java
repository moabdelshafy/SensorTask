package com.sensor.service;

import java.util.List;

import com.sensor.dto.LocationDTO;
import com.sensor.entity.Location;

public interface LocationService {

	public LocationDTO addOrUpdateLocation(LocationDTO locationDTO);
	public LocationDTO findById(Long id);
	public List<Location> findAll(); 
}
