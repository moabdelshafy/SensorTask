package com.sensor.dto;

import java.io.Serializable;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public class BaseDTO implements Serializable {

	private static final long serialVersionUID = -7857705912640986780L;

	private Long id;
}
