package com.sensor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationDTO extends BaseDTO {

	private static final long serialVersionUID = -6991980441404489278L;

	@NotBlank(message = "{SENSOR1002}")
	private String code;

}
