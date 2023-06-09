package com.sensor.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends BaseDTO {

	private static final long serialVersionUID = 5892494909024049049L;

	private String username;
	private String token;
}
