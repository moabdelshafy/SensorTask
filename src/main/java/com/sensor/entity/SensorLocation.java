package com.sensor.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners({ AuditingEntityListener.class })
@Table(name = "sensor_location")
public class SensorLocation {

	@EmbeddedId
	private SensorLocationKey id;

	@ManyToOne
	@MapsId("locationId")
	private Location location;

	@ManyToOne
	@MapsId("sensorId")
	private Sensor sensor;

	@Column(name = "min", nullable = false)
	private Float min;

	@Column(name = "max", nullable = false)
	private Float max;

	@Embedded
	private Audit audit = new Audit();

}
