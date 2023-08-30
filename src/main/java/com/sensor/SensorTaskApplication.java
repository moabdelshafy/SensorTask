package com.sensor;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import com.sensor.entity.Location;
import com.sensor.service.LocationService;
import com.sensor.service.SensorLocationService;
import com.sensor.service.SensorService;

@SpringBootApplication
@EnableCaching
public class SensorTaskApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SensorTaskApplication.class, args);
    }

    @Autowired
    private  LocationService locationService;
    @Autowired
    private  SensorLocationService sensorLocationService;
    @Autowired
    private  SensorService sensorService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Location> locations = locationService.findAll();
        sensorService.findAll();
        for (Location location : locations) {
            sensorLocationService.findByLocationId(location.getId());
        }

    }

}
