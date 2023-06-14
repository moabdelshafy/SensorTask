package com.sensor.serviceImpl;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sensor.dto.LocationDTO;
import com.sensor.entity.Location;
import com.sensor.exceptions.SensorTaskException;
import com.sensor.repository.LocationRepository;
import com.sensor.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private ModelMapper mapper;

	@Override
	public LocationDTO addOrUpdateLocation(LocationDTO locationDTO) {
		Location location = new Location();
		if (locationDTO.getId() != null) { // check if in update case or not
			location = locationRepository.findById(locationDTO.getId())
					.orElseThrow(() -> new SensorTaskException("SENSOR1000", new Object[] { locationDTO.getId() }));
		}
		location = mapper.map(locationDTO, Location.class);
		locationRepository.save(location);
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

}
