package com.sensor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorDTO extends BaseDTO {

	private static final long serialVersionUID = -6991980441404489278L;

	@NotBlank(message = "{SENSOR1002}")
	private String name;
	private Boolean status;

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

}
