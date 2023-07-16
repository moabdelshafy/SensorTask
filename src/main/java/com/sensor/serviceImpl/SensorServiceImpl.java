package com.sensor.serviceImpl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.sensor.dto.SensorDTO;
import com.sensor.entity.Sensor;
import com.sensor.repository.SensorRepository;
import com.sensor.service.SensorService;
@RequiredArgsConstructor
@Service
public class SensorServiceImpl implements SensorService {

	private final SensorRepository sensorRepository;
	private final ModelMapper modelMapper;

	@CacheEvict(value = { "findAllSensors" }, allEntries = true)
	@Override
	public SensorDTO addSensor(SensorDTO sensorDTO) {
		Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
		sensor = sensorRepository.save(sensor);
		return modelMapper.map(sensor, SensorDTO.class);
	}

	@Cacheable(cacheNames = "findAllSensors", unless = "#result == null")
	@Override
	public List<Sensor> findAll() {
		return sensorRepository.findAll();
	}

}
