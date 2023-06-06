package com.sensor.config;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@ConfigurationProperties(prefix = "application.jwt")
@Component
public class JwtConfig {

	private String username;
	private String password;
	private String key;
	private String tokenPrefix;

	public JwtConfig() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public void setTokenPrefix(String tokenPrefix) {
		this.tokenPrefix = tokenPrefix;
	}

	@Bean
	public Algorithm algorithm() {
		return Algorithm.HMAC256(getKey().getBytes());
	}

	public String getAuthorizationHeader() {
		return HttpHeaders.AUTHORIZATION;
	}

	public String generateRefreshToken(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) {
		String refresh_token = JWT.create().withSubject(authResult.getName())
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).withIssuedAt(new Date())
				.withIssuer(request.getRequestURL().toString()).sign(algorithm());
		return refresh_token;
	}

	public String generateAccessToken(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) {

		String access_token = JWT.create().withSubject(authResult.getName())
				.withClaim("authorities",
						authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000)).withIssuedAt(new Date())
				.withIssuer(request.getRequestURL().toString()).sign(algorithm());

		return access_token;
	}

}
