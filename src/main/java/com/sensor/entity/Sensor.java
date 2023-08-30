package com.sensor.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.NumericBooleanConverter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "sensor", uniqueConstraints = { @UniqueConstraint(columnNames = "type", name = "type_unique") })
public class Sensor extends BaseEntity<Long> {

	private static final long serialVersionUID = -6991980441404489278L;

	@Column(name = "type", nullable = false)
	private String type;

	//@Column(nullable = false, columnDefinition = "TINYINT(1)", length = 1)
	@Convert(converter = NumericBooleanConverter.class)
	private Boolean status;

	@OneToMany(mappedBy = "sensor")
	private Set<SensorLocation> locationSensors;

}
