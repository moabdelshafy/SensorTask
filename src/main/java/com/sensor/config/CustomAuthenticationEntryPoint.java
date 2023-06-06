package com.sensor.config;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensor.exceptions.ResponseMessage;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("customAuthenticationEntryPoint")
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final MessageSource messageSource;

	@Autowired
	public CustomAuthenticationEntryPoint(MessageSource messageSource) {
		super();
		this.messageSource = messageSource;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String errorMessage = messageSource.getMessage("SENSOR1004", null, LocaleContextHolder.getLocale());
		String errorCode = "SENSOR1004";

		ResponseMessage responseMessage = new ResponseMessage(errorCode, errorMessage);
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType(APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), responseMessage);
	}
}
