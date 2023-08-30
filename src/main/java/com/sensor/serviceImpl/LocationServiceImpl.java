package com.sensor.serviceImpl;

import java.util.List;
import com.sensor.mapper.LocationMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Autowired
    private LocationStatusService locationStatusService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private LocationMapper locationMapper;


    @CacheEvict(value = {"findAllLocations"}, allEntries = true)
    @Override
    public LocationDTO addOrUpdateLocation(LocationDTO locationDTO) {
        Location location;
        if (locationDTO.getId() != null) { // check if in update case or not
            location = locationRepository.findById(locationDTO.getId())
                    .orElseThrow(() -> new SensorTaskException("SENSOR1000", new Object[]{locationDTO.getId()}));
            // Different types of mapping ->
            // location.setCode(locationDTO.getCode()); manual mapping
            // location = mapper.map(locationDTO,Location.class); mapping using ModelMapper
            location = locationMapper.mapToEntity(locationDTO, location); // mapping using MapStruct
            locationRepository.save(location);
            return locationMapper.mapToDto(location);
        }

        location = locationMapper.mapToEntity(locationDTO);
        locationRepository.save(location);
        addLocationStatus(location);
        return locationMapper.mapToDto(location);
    }

    @Override
    public LocationDTO findById(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new SensorTaskException("SENSOR1000", new Object[]{id}));
        return locationMapper.mapToDto(location);
    }

    @Cacheable(cacheNames = "findAllLocations", unless = "#result == null")
    @Override
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    public void addLocationStatus(Location location) {
        LocationStatus locationStatus = new LocationStatus();
        locationStatus.setLocation(location);
        locationStatus.setStatus(LocationState.NORMAL);
        locationStatus.setMessage(SensorCases.NORMAL_CASE);
        locationStatus.setReadingValue("0.0");
        locationStatusService.addLocationStatus(locationStatus);
    }

}
