package com.sensor.serviceImpl;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.google.common.collect.Range;
import com.sensor.dto.LocationState;
import com.sensor.dto.SensorCases;
import com.sensor.entity.Location;
import com.sensor.entity.LocationStatus;
import com.sensor.entity.SensorLocation;
import com.sensor.repository.LocationStatusRepository;
import com.sensor.service.LocationService;
import com.sensor.service.LocationStatusService;
import com.sensor.service.SensorLocationService;

@EnableScheduling
@Service
public class LocationStatusServiceImpl implements LocationStatusService {

	@Autowired
	private LocationStatusRepository locationStatusRepository;
	@Autowired
	private SensorLocationService sensorLocationService;
	@Autowired
	private LocationService locationService;

	private final static String Humidity_Sensor = "Humidity";
	private final static String Light_Sensor = "Light";
	private final static String Temperature_Sensor = "Temperature";

	@Scheduled(fixedDelay = 10000, initialDelay = 10000)
	public void checkAndChangeStateOfLocation() {

		List<Location> locations = locationService.findAll();
		if (locations != null && !locations.isEmpty()) {
			for (Location location : locations) {
				checkForAllSensors(location);
			}
		}
	}

	private void checkForAllSensors(Location location) {
		Map<String, Enum> states = new HashMap<>();
		List<SensorLocation> sensorLocations = sensorLocationService.findByLocationId(location.getId());
		float readingValue = generateRandomNumber();
		for (SensorLocation sensorLocation : sensorLocations) {

			if (sensorLocation.getSensor().getName().equals(Humidity_Sensor)) {
				Range<Float> range = Range.closed(sensorLocation.getMin(), sensorLocation.getMax());
				if (range.contains(readingValue)) {
					states.put(sensorLocation.getSensor().getName(), SensorCases.NORMAL_CASE);
				} else if (readingValue > sensorLocation.getMax()) {
					states.put(sensorLocation.getSensor().getName(), SensorCases.HIGH);
				} else if (readingValue < sensorLocation.getMin()) {
					states.put(sensorLocation.getSensor().getName(), SensorCases.LOW);
				}

			}

			if (sensorLocation.getSensor().getName().equals(Light_Sensor)) {
				Range<Float> range = Range.closed(sensorLocation.getMin(), sensorLocation.getMax());
				if (range.contains(readingValue)) {
					states.put(sensorLocation.getSensor().getName(), SensorCases.NORMAL_CASE);
				} else if (readingValue > sensorLocation.getMax()) {
					states.put(sensorLocation.getSensor().getName(), SensorCases.HIGH);
				} else if (readingValue < sensorLocation.getMin()) {
					states.put(sensorLocation.getSensor().getName(), SensorCases.LOW);
				}

			}

			if (sensorLocation.getSensor().getName().equals(Temperature_Sensor)) {
				Range<Float> range = Range.closed(sensorLocation.getMin(), sensorLocation.getMax());
				if (range.contains(readingValue)) {
					states.put(sensorLocation.getSensor().getName(), SensorCases.NORMAL_CASE);
				} else if (readingValue > sensorLocation.getMax()) {
					states.put(sensorLocation.getSensor().getName(), SensorCases.HIGH);
				} else if (readingValue < sensorLocation.getMin()) {
					states.put(sensorLocation.getSensor().getName(), SensorCases.LOW);
				}

			}

		}
		saveAbnormalCases(location, states, readingValue);
	}

	private void saveAbnormalCases(Location location, Map<String, Enum> states, float readingValue) {
		LocationStatus locationStatus = findByLocationId(location.getId());
		if (locationStatus != null && !states.isEmpty()) {
			if (states.get(Temperature_Sensor) == SensorCases.HIGH && states.get(Light_Sensor) == SensorCases.HIGH) {
				locationStatus.setLocation(location);
				locationStatus.setReadingValue(String.valueOf(readingValue));
				locationStatus.setStatus(LocationState.ABNORMAL);
				locationStatus.setMessage(SensorCases.ABNORMAL_TEMPERATURE);
				addLocationStatus(locationStatus);
			} else if (states.get(Humidity_Sensor) == SensorCases.HIGH
					&& states.get(Temperature_Sensor) == SensorCases.HIGH) {
				locationStatus.setLocation(location);
				locationStatus.setReadingValue(String.valueOf(readingValue));
				locationStatus.setStatus(LocationState.ABNORMAL);
				locationStatus.setMessage(SensorCases.ABNORMAL_HUMIDITY);
				addLocationStatus(locationStatus);
			} else if (states.get(Light_Sensor) == SensorCases.HIGH || states.get(Light_Sensor) == SensorCases.LOW) {
				locationStatus.setLocation(location);
				locationStatus.setReadingValue(String.valueOf(readingValue));
				locationStatus.setStatus(LocationState.ABNORMAL);
				locationStatus.setMessage(SensorCases.ABNORMAL_LIGHT);
				addLocationStatus(locationStatus);
			} else if (states.get(Light_Sensor) == SensorCases.LOW || states.get(Humidity_Sensor) == SensorCases.LOW
					|| states.get(Temperature_Sensor) == SensorCases.LOW) {
				locationStatus.setLocation(location);
				locationStatus.setReadingValue(String.valueOf(readingValue));
				locationStatus.setStatus(LocationState.ABNORMAL);
				locationStatus.setMessage(SensorCases.ABNORMAL_CASE);
				addLocationStatus(locationStatus);
			} else {
				locationStatus.setLocation(location);
				locationStatus.setReadingValue(String.valueOf(readingValue));
				locationStatus.setStatus(LocationState.NORMAL);
				locationStatus.setMessage(SensorCases.NORMAL_CASE);
				addLocationStatus(locationStatus);
			}
		}
	}

	@Cacheable("findAllLocationStatus")
	@Override
	public List<LocationStatus> findAll() {
		return locationStatusRepository.findAll();
	}

	@CacheEvict(value = { "findAllLocationStatus", "findLocationStatusByLocationId" }, allEntries = true)
	@Override
	public void addLocationStatus(LocationStatus locationStatus) {
		locationStatusRepository.save(locationStatus);
	}

	@Cacheable(cacheNames = "findLocationStatusByLocationId", unless = "#result == null")
	@Override
	public LocationStatus findByLocationId(Long locationId) {
		return locationStatusRepository.findByLocationId(locationId);
	}

	private float generateRandomNumber() {
		DecimalFormat decfor = new DecimalFormat("0.0");
		float min = 1f;
		float max = 100f;
		Random r = new Random();
		float random = min + r.nextFloat() * (max - min);
		return Float.parseFloat(decfor.format(random));
	}

}
