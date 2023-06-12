package com.sensor.entity;

import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sensor", uniqueConstraints = { @UniqueConstraint(columnNames = "name", name = "name_unique") })
public class Sensor extends BaseEntity<Long> {

	private static final long serialVersionUID = -6991980441404489278L;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(nullable = false, columnDefinition = "TINYINT(1)", length = 1)
	private Boolean status;

	@OneToMany(mappedBy = "sensor")
	private Set<SensorLocation> locationSensors;

}
