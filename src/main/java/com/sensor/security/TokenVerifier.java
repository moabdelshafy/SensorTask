package com.sensor.security;

import static java.util.Arrays.stream;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sensor.config.JwtConfig;
import com.sensor.exceptions.ResponseMessage;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenVerifier extends OncePerRequestFilter {

	Logger LOG = LoggerFactory.getLogger(TokenVerifier.class);

	private JwtConfig jwtConfig;
	private Algorithm algorithm;

	@Autowired
	public TokenVerifier(JwtConfig jwtConfig, Algorithm algorithm) {
		super();
		this.jwtConfig = jwtConfig;
		this.algorithm = algorithm;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		if (request.getServletPath().equals("/sensor/login")) {
//			filterChain.doFilter(request, response);
//		} else {
		String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
		if (authorizationHeader != null && authorizationHeader.startsWith(jwtConfig.getTokenPrefix())) {
			try {
				String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), ""); // Token without Prefix
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(token);
				String username = decodedJWT.getSubject();
				String[] roles = decodedJWT.getClaim("authorities").asArray(String.class);
				List<SimpleGrantedAuthority> authorities = new ArrayList<>();
				stream(roles).forEach(role -> {
					authorities.add(new SimpleGrantedAuthority(role));
				});
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				filterChain.doFilter(request, response);
			} catch (Exception exception) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType(APPLICATION_JSON_VALUE);
				ResponseMessage responseMessage = new ResponseMessage("TOKEN_EXCEPTION", exception.getMessage());
				new ObjectMapper().writeValue(response.getOutputStream(), responseMessage);
			}
		} else {
			filterChain.doFilter(request, response);
		}
		// }
	}

}
