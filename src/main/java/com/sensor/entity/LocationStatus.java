package com.sensor.entity;

import com.sensor.dto.LocationState;
import com.sensor.dto.SensorCases;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class LocationStatus extends BaseEntity<Long> {

	private static final long serialVersionUID = 7326159996945450032L;

	@ManyToOne
	@JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
	private Location location;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private LocationState status;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SensorCases message;
	
	@Column(length = 5)
	private String readingValue;

}
