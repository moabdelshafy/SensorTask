package com.sensor.repository;

import com.sensor.config.TestConfig;
import com.sensor.entity.Location;
import com.sensor.entity.Sensor;
import com.sensor.entity.SensorLocation;
import com.sensor.entity.SensorLocationKey;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DataJpaTest
@Import(TestConfig.class)
class SensorLocationRepositoryTest {

    private final LocationRepository locationRepository;
    private final SensorRepository sensorRepository;
    private final SensorLocationRepository sensorLocationRepository;

    @Test
    void findSensorLocationByLocationIdAndSensorId() {

        // given
        Location testLocation = locationRepository.save(Location.builder().code("testLocation").build());

        Sensor testSensor = sensorRepository.save(new Sensor("testSensor", Boolean.TRUE, null));
        SensorLocationKey sensorLocationKey = new SensorLocationKey(testLocation.getId(), testSensor.getId());
        sensorLocationRepository.save(new SensorLocation(sensorLocationKey, testLocation, testSensor, 100.0f, 500.0f, null));

        // when
        SensorLocation expected = sensorLocationRepository.findSensorLocationByLocationIdAndSensorId(testLocation.getId(),testSensor.getId());

        // then
        assertThat(expected).isNotNull();

    }

    @Test
    void findSensorLocationByLocationId() {

        // given
        Location testLocation = locationRepository.save(new Location("testLocation", null));
        Sensor testSensor = sensorRepository.save(new Sensor("testSensor", Boolean.TRUE, null));
        SensorLocationKey sensorLocationKey = new SensorLocationKey(testLocation.getId(), testSensor.getId());
        sensorLocationRepository.save(new SensorLocation(sensorLocationKey, testLocation, testSensor, 100.0f, 500.0f, null));

        // when
        List<SensorLocation> expected = sensorLocationRepository.findByLocationId(testLocation.getId());

        // then
        assertThat(expected).isNotNull();
        assertThat(expected).asList().isNotEmpty();
    }
}