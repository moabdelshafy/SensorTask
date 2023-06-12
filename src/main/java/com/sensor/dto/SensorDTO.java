package com.sensor.dto;

import java.util.Set;
import com.sensor.entity.SensorLocation;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class SensorDTO extends BaseDTO {

	private static final long serialVersionUID = -6991980441404489278L;

	@NotBlank(message = "{SENSOR1002}")
	private String name;
	private Boolean status;
	private Set<SensorLocation> locationSensors;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getStatus() {
		return this.status != null ? this.status : true;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Set<SensorLocation> getLocationSensors() {
		return locationSensors;
	}

	public void setLocationSensors(Set<SensorLocation> locationSensors) {
		this.locationSensors = locationSensors;
	}

}
