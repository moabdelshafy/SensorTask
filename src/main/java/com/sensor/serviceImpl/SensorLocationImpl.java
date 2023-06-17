package com.sensor.serviceImpl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.sensor.dto.SensorLocationDTO;
import com.sensor.entity.Location;
import com.sensor.entity.Sensor;
import com.sensor.entity.SensorLocation;
import com.sensor.entity.SensorLocationKey;
import com.sensor.exceptions.SensorTaskException;
import com.sensor.repository.LocationRepository;
import com.sensor.repository.SensorLocationRepository;
import com.sensor.repository.SensorRepository;
import com.sensor.service.SensorLocationService;

@Service
public class SensorLocationImpl implements SensorLocationService {

	@Autowired
	private SensorLocationRepository sensorLocationRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private SensorRepository sensorRepository;

	@CacheEvict(value = {"findSensorLocationsByLocationId", "findAllSensorLocation"}, allEntries = true)
	@Override
	public SensorLocationDTO addSensorsToLocation(SensorLocationDTO sensorLocationDTO) {
		Optional<Location> location = locationRepository.findById(sensorLocationDTO.getLocationId());
		Optional<Sensor> sensor = sensorRepository.findById(sensorLocationDTO.getSensorId());
		if (validateSensorAndLocation(location, sensor, sensorLocationDTO)) {
			SensorLocation sensorLocation = mapDTOToEntity(location.get(), sensor.get(), sensorLocationDTO);
			sensorLocation = sensorLocationRepository.save(sensorLocation);
			return mapEntityToDTO(sensorLocation, sensorLocationDTO);
		}
		return null;
	}

	@Override
	public SensorLocationDTO findByLocationIdAndSensorId(SensorLocationDTO sensorLocationDTO) {
		SensorLocation sensorLocation = sensorLocationRepository.findSensorLocationByLocationIdAndSensorId(
				sensorLocationDTO.getLocationId(), sensorLocationDTO.getSensorId());
		if (sensorLocation == null) {
			throw new SensorTaskException("SENSOR1000", new Object[] { sensorLocationDTO.getLocationId() });
		}
		return mapEntityToDTO(sensorLocation, sensorLocationDTO);
	}
	
	@Cacheable("findSensorLocationsByLocationId")
	@Override
	public List<SensorLocation> findByLocationId(Long locationId) {
		return sensorLocationRepository.findByLocationId(locationId);
	}

	@Cacheable("findAllSensorLocation")
	@Override
	public List<SensorLocation> findAll() {
		return sensorLocationRepository.findAll();
	}

	private SensorLocation mapDTOToEntity(Location location, Sensor sensor, SensorLocationDTO sensorLocationDTO) {
		SensorLocationKey id = new SensorLocationKey();
		id.setLocationId(location.getId());
		id.setSensorId(sensor.getId());

		SensorLocation locationSensor = new SensorLocation();
		locationSensor.setId(id);
		locationSensor.setLocation(location);
		locationSensor.setSensor(sensor);
		locationSensor.setMin(sensorLocationDTO.getMin());
		locationSensor.setMax(sensorLocationDTO.getMax());
		return locationSensor;
	}

	private SensorLocationDTO mapEntityToDTO(SensorLocation sensorLocation, SensorLocationDTO sensorLocationDTO) {
		sensorLocationDTO.setLocationId(sensorLocation.getLocation().getId());
		sensorLocationDTO.setSensorId(sensorLocation.getSensor().getId());
		sensorLocationDTO.setMin(sensorLocation.getMin());
		sensorLocationDTO.setMax(sensorLocation.getMax());
		return sensorLocationDTO;
	}

	public boolean validateSensorAndLocation(Optional<Location> location, Optional<Sensor> sensor,
			SensorLocationDTO locationSensorDTO) {
		if (!location.isPresent()) {
			throw new SensorTaskException("SENSOR1000", new Object[] { locationSensorDTO.getLocationId() });
		}
		if (!sensor.isPresent()) {
			throw new SensorTaskException("SENSOR1000", new Object[] { locationSensorDTO.getSensorId() });
		}
		return true;
	}

}
