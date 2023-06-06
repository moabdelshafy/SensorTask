package com.sensor.exceptions;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.JWTVerificationException;

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
	public ResponseEntity<ResponseMessage> handleDuplicateKeyException(DataIntegrityViolationException ex) {
		String keyParam = getKeyOfUniqueConstraint(ex.getCause().getCause().getMessage());
		String errorMessage = getMessageSource().getMessage("SENSOR1003", new Object[] { keyParam },
				LocaleContextHolder.getLocale());
		String errorCode = "SENSOR1003";
		ResponseMessage responseMessage = new ResponseMessage(errorCode, errorMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.CONFLICT);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errorMessages = ex.getBindingResult().getAllErrors().stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
		String errorCode = "SENSOR1002";

		return new ResponseEntity<>(new ResponseMessage(errorCode, errorMessages.get(0)), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ResponseMessage> handleUsernameNotFoundException(UsernameNotFoundException ex) {
		return new ResponseEntity<>(new ResponseMessage(ex.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseMessage> handleGeneralException(Exception ex) {
		return new ResponseEntity<>(new ResponseMessage(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private String getKeyOfUniqueConstraint(String errorMessage) {
		String detailMessage = errorMessage.substring(errorMessage.indexOf("Detail:") + "Detail:".length()).trim();
		String specificOutput = detailMessage.substring(detailMessage.indexOf("("), detailMessage.lastIndexOf(")") + 1)
				.trim();
		return specificOutput;
	}

	@ExceptionHandler({ JWTVerificationException.class, AlgorithmMismatchException.class })
	public ResponseEntity<ResponseMessage> handleAlgorithmMismatchException(JWTVerificationException ex) {
		return new ResponseEntity<>(new ResponseMessage("test"), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}