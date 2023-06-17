package com.sensor.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.sensor.dto.LocationDTO;
import com.sensor.dto.LocationState;
import com.sensor.dto.SensorCases;
import com.sensor.entity.Location;
import com.sensor.entity.LocationStatus;
import com.sensor.exceptions.SensorTaskException;
import com.sensor.repository.LocationRepository;
import com.sensor.service.LocationService;
import com.sensor.service.LocationStatusService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;

	private LocationStatusService locationStatusService;
	@Autowired
	private ModelMapper mapper;

	@Autowired
	public LocationServiceImpl(@Lazy LocationStatusService locationStatusService) {
		super();
		this.locationStatusService = locationStatusService;
	}

	@CacheEvict(value = { "findAllLocations" }, allEntries = true)
	@Override
	public LocationDTO addOrUpdateLocation(LocationDTO locationDTO) {
		Location location = new Location();
		if (locationDTO.getId() != null) { // check if in update case or not
			location = locationRepository.findById(locationDTO.getId())
					.orElseThrow(() -> new SensorTaskException("SENSOR1000", new Object[] { locationDTO.getId() }));
			location.setCode(locationDTO.getCode());
		} else {
			location.setCode(locationDTO.getCode());
		}

		locationRepository.save(location);
		addLocationStatus(location);
		return mapper.map(location, LocationDTO.class);
	}

	@Override
	public LocationDTO findById(Long id) {
		Optional<Location> location = locationRepository.findById(id);
		if (!location.isPresent()) {
			throw new SensorTaskException("SENSOR1000", new Object[] { id });
		}
		return mapper.map(location.get(), LocationDTO.class);
	}

	@Cacheable(cacheNames = "findAllLocations", unless = "#result == null")
	@Override
	public List<Location> findAll() {
		return locationRepository.findAll();
	}

	public void addLocationStatus(Location location) {
		LocationStatus locationStatus = new LocationStatus();
		LocationStatus existLocationStatus = locationStatusService.findByLocationId(location.getId());
		if (existLocationStatus != null) {
			locationStatus = existLocationStatus;
		}
		locationStatus.setLocation(location);
		locationStatus.setStatus(LocationState.NORMAL);
		locationStatus.setMessage(SensorCases.NORMAL_CASE);
		locationStatus.setReadingValue("0.0");
		locationStatusService.addLocationStatus(locationStatus);
	}

}
