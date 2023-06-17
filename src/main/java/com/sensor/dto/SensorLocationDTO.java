package com.sensor.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SensorLocationDTO {

	@NotNull(message = "{SENSOR1002}")
	private Long locationId;
	@NotNull(message = "{SENSOR1002}")
	private Long sensorId;

	@Min(value = 1, message = "{SENSOR1005}")
	@NotNull(message = "{SENSOR1002}")
	private Float min;

	@Max(value = 100, message = "{SENSOR1006}")
	@NotNull(message = "{SENSOR1002}")
	private Float max;

}
