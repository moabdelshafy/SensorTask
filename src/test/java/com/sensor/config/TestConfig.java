package com.sensor.config;

import com.sensor.service.LocationService;
import com.sensor.service.SensorLocationService;
import com.sensor.service.SensorService;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration
public class TestConfig {


    @Bean
    public LocationService mockForlocationService() {
        return Mockito.mock(LocationService.class);
    }

    @Bean
    public SensorLocationService mockForsensorLocationService() {
        return Mockito.mock(SensorLocationService.class);
    }

    @Bean
    public SensorService mockForsensorService() {
        return Mockito.mock(SensorService.class);
    }

    // Other beans...

}
