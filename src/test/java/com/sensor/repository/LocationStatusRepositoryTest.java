package com.sensor.repository;

import com.sensor.config.TestConfig;
import com.sensor.dto.LocationState;
import com.sensor.dto.SensorCases;
import com.sensor.entity.Location;
import com.sensor.entity.LocationStatus;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@DataJpaTest
@Import(TestConfig.class)
class LocationStatusRepositoryTest {

    private final LocationRepository locationRepository;
    private final LocationStatusRepository locationStatusRepository;

    @Test
    void findLocationStatusByLocationId() {

        // given
        Location testLocation = locationRepository.save(new Location("testLocation", null));
        LocationStatus locationStatus = locationStatusRepository.save(
                new LocationStatus(testLocation, LocationState.NORMAL, SensorCases.ABNORMAL_CASE, "2.5")
        );

        // when
        LocationStatus expected = locationStatusRepository.findByLocationId(testLocation.getId());

        // then
        assertThat(expected).isNotNull();
        assertThat(expected.getStatus()).isEqualTo(LocationState.NORMAL);
        assertThat(Float.parseFloat(expected.getReadingValue())).isGreaterThan(1.0f);

    }
}