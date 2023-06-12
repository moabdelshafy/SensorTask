package com.sensor.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sensor.dto.SensorLocationDTO;
import com.sensor.entity.Location;
import com.sensor.entity.SensorLocation;
import com.sensor.entity.SensorLocationKey;
import com.sensor.entity.Sensor;
import com.sensor.exceptions.SensorTaskException;
import com.sensor.repository.LocationRepository;
import com.sensor.repository.SensorLocationRepository;
import com.sensor.repository.SensorRepository;
import com.sensor.service.SensorLocationService;

@Service
public class SensorLocationImpl implements SensorLocationService {

	@Autowired
	private SensorLocationRepository locationSensorRepository;
	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private SensorRepository sensorRepository;

	@Override
	public SensorLocationDTO addSensorsToLocation(SensorLocationDTO locationSensorDTO) {
		Optional<Location> location = locationRepository.findById(locationSensorDTO.getLocationId());
		Optional<Sensor> sensor = sensorRepository.findById(locationSensorDTO.getSensorId());
		if (validateSensorAndLocation(location, sensor)) {
			SensorLocation locationSensor = mapDTOToEntity(location.get(), sensor.get(), locationSensorDTO);
			locationSensor = locationSensorRepository.save(locationSensor);
			return mapEntityToDTO(locationSensor, locationSensorDTO);
		}
		return null;
	}

	private SensorLocation mapDTOToEntity(Location location, Sensor sensor, SensorLocationDTO locationSensorDTO) {
		SensorLocationKey id = new SensorLocationKey();
		id.setLocationId(location.getId());
		id.setSensorId(sensor.getId());

		SensorLocation locationSensor = new SensorLocation();
		locationSensor.setId(id);
		locationSensor.setLocation(location);
		locationSensor.setSensor(sensor);
		locationSensor.setMin(locationSensorDTO.getMin());
		locationSensor.setMax(locationSensorDTO.getMax());
		return locationSensor;
	}

	private SensorLocationDTO mapEntityToDTO(SensorLocation locationSensor, SensorLocationDTO locationSensorDTO) {
		locationSensorDTO.setLocationId(locationSensor.getLocation().getId());
		locationSensorDTO.setSensorId(locationSensor.getSensor().getId());
		locationSensorDTO.setMin(locationSensor.getMin());
		locationSensorDTO.setMax(locationSensor.getMax());
		return locationSensorDTO;
	}

	public boolean validateSensorAndLocation(Optional<Location> location, Optional<Sensor> sensor) {
		if (!location.isPresent()) {
			throw new SensorTaskException("SENSOR1000", new Object[] { location.get().getId() });
		}
		if (!sensor.isPresent()) {
			throw new SensorTaskException("SENSOR1000", new Object[] { sensor.get().getId() });
		}
		return true;
	}

}
