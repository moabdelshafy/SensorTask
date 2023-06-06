package com.sensor.exceptions;

public class SensorTaskException extends RuntimeException {

	private static final long serialVersionUID = -7465774380661260097L;

	private String errorCode;
	private String errorMessage;
	private Object[] params;

	public SensorTaskException() {
		super();
	}

	public SensorTaskException(String errorMessage) {
		super(errorMessage);
	}

	public SensorTaskException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
	}

	public SensorTaskException(String errorMessage, Object[] params) {
		super(errorMessage);
		this.params = params;
	}

	public SensorTaskException(String errorCode, String errorMessage, Object[] params) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.params = params;
	}

	public SensorTaskException(String msg, Throwable cause) {
		super(msg, cause);
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

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

}
