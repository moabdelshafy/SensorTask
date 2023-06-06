package com.sensor.exceptions;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseMessage {
	
	@JsonInclude(value = Include.NON_NULL)
	private String errorCode;
	
	@JsonInclude(value = Include.NON_NULL)
	private String errorMessage;

	@JsonInclude(value = Include.NON_NULL)
	private List<String> errorMessages;

	public ResponseMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public ResponseMessage(List<String> errorMessages) {
		super();
		this.errorMessages = errorMessages;
	}

	public ResponseMessage(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

}
