package com.sensor.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationDTO extends BaseDTO {

	private static final long serialVersionUID = -6991980441404489278L;

	@NotBlank(message = "{SENSOR1002}")
	private String code;

}
