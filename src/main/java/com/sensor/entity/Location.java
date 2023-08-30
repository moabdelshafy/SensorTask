package com.sensor.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Location", uniqueConstraints = { @UniqueConstraint(columnNames = "code", name = "code_unique") })
public class Location extends BaseEntity<Long> {

	private static final long serialVersionUID = -6991980441404489278L;

	@Column(name = "code", nullable = false)
	private String code;

	@OneToMany(mappedBy = "location")
	private Set<SensorLocation> locationSensors;

}
