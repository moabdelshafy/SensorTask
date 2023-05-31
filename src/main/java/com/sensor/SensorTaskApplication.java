package com.sensor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SensorTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorTaskApplication.class, args);
	}

}
