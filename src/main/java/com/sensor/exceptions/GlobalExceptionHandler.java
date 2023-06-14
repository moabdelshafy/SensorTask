package com.sensor.exceptions;

import java.util.stream.Collectors;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(SensorTaskException.class)
	public ResponseEntity<ResponseMessage> handleSensorTaskException(SensorTaskException e) {
		String errorMessage = getMessageSource().getMessage(e.getMessage(),
				e.getParams() != null ? e.getParams() : null, LocaleContextHolder.getLocale());
		String errorCode = e.getMessage();
		ResponseMessage responseMessage = new ResponseMessage(errorCode, errorMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ResponseMessage> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		ResponseMessage responseMessage = new ResponseMessage("SQL_EXCEPTION", rootCause.getMessage());
		return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String errorMessages = ex.getBindingResult().getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()).get(0);
		String errorCode = "SENSOR1002";

		return new ResponseEntity<>(new ResponseMessage(errorCode, errorMessages), HttpStatus.BAD_REQUEST);
	}

}