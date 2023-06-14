package com.sensor.service;

import com.sensor.dto.LocationDTO;

public interface LocationService {

	public LocationDTO addOrUpdateLocation(LocationDTO locationDTO);
	public LocationDTO findById(Long id);
}
