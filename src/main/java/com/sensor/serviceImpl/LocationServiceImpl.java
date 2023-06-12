package com.sensor.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sensor.dto.LocationDTO;
import com.sensor.entity.Location;
import com.sensor.repository.LocationRepository;
import com.sensor.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private ModelMapper mapper;

	@Override
	public LocationDTO addLocation(LocationDTO locationDTO) {
		Location location = mapper.map(locationDTO, Location.class);
		location = locationRepository.save(location);
		return mapper.map(location, LocationDTO.class);
	}

}
