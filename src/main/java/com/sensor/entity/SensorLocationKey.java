package com.sensor.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable
public class SensorLocationKey implements Serializable {

	private static final long serialVersionUID = -9071310373369626360L;

	@Column(name = "location_id")
	private Long locationId;
	@Column(name = "sensor_id")
	private Long sensorId;

}
