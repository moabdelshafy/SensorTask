package com.sensor.serviceImpl;

import com.sensor.config.TestConfig;
import com.sensor.dto.LocationDTO;
import com.sensor.dto.LocationState;
import com.sensor.dto.SensorCases;
import com.sensor.entity.Location;
import com.sensor.entity.LocationStatus;
import com.sensor.mapper.LocationMapper;
import com.sensor.repository.LocationRepository;
import com.sensor.service.LocationStatusService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DataJpaTest
@Import(TestConfig.class)
@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private LocationMapper locationMapper;
    @Mock
    private LocationStatusService locationStatusService;
    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;
    private LocationDTO locationDTO;
    private LocationStatus locationStatus;


    @BeforeEach
    void setUp() {
        location = Location.builder().code("testLocation").build();
        location.setId(1L);

        locationDTO = LocationDTO.builder().code("testLocation").build();
        locationDTO.setId(1L);

        locationStatus = LocationStatus.builder().location(location)
                .status(LocationState.NORMAL).message(SensorCases.ABNORMAL_CASE)
                .readingValue("5.5").build();
    }

    @Test
    void addOrUpdateLocation() {

        // when
        when(locationRepository.findById(locationDTO.getId())).thenReturn(Optional.ofNullable(location));
        when(locationMapper.mapToEntity(locationDTO, location)).thenReturn(location);
        when(locationRepository.save(Mockito.any(Location.class))).thenReturn(location);
        when(locationMapper.mapToDto(location)).thenReturn(locationDTO);
        when(locationStatusService.findByLocationId(location.getId())).thenReturn(locationStatus);
        LocationDTO expected = locationService.addOrUpdateLocation(locationDTO);

        // then
        assertThat(expected).isNotNull();
        assertThat(expected.getCode()).isEqualTo("testLocation");
    }

    @Test
    void findById() {

        // when
        when(locationRepository.findById(location.getId())).thenReturn(Optional.ofNullable(location));
        when(locationMapper.mapToDto(location)).thenReturn(locationDTO);
        LocationDTO expected = locationService.findById(location.getId());

        // then
        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isGreaterThan(0);

    }

    @Test
    void findAll() {

        // when
        when(locationRepository.findAll()).thenReturn(Arrays.asList(location));
        List<Location> expected = locationService.findAll();

        // then
        assertThat(expected).asList().isNotEmpty();
        assertThat(expected).asList().size().isGreaterThan(0);

    }

}