package com.sensor.security;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensor.config.JwtConfig;
import com.sensor.dto.UserDTO;
import com.sensor.entity.AppUser;
import com.sensor.exceptions.ResponseMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JwtConfig jwtConfig;
	private MessageSource messageSource;
	private ModelMapper modelMapper;

	@Autowired
	public AuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig,
			MessageSource messageSource, ModelMapper modelMapper) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
		this.messageSource = messageSource;
		this.modelMapper = modelMapper;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {

			JwtConfig authenticationRequest = new ObjectMapper().readValue(request.getInputStream(), JwtConfig.class);
			jwtConfig.setPassword(authenticationRequest.getPassword());
			Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword());

			return authenticationManager.authenticate(authenticationToken);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		AppUser user = (AppUser) authResult.getPrincipal();
		String access_token = jwtConfig.generateAccessToken(request, response, authResult);
		user.setToken(jwtConfig.getTokenPrefix() + access_token);
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		response.setStatus(HttpServletResponse.SC_CREATED);
		response.setContentType(APPLICATION_JSON_VALUE); // send as json File in Body
		log.info("Successful Authentication By User:{}", userDto.getUsername());
		new ObjectMapper().writeValue(response.getOutputStream(), userDto);

	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		String errorMessage = messageSource.getMessage("SENSOR1001", null, LocaleContextHolder.getLocale());
		String errorCode = "SENSOR1001";
		ResponseMessage responseMessage = new ResponseMessage(errorCode, errorMessage);
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		response.setContentType(APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), responseMessage);
	}

}
