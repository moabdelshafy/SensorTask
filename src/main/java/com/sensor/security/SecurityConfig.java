package com.sensor.security;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import com.auth0.jwt.algorithms.Algorithm;
import com.sensor.config.JwtConfig;
import com.sensor.serviceImpl.AppUserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AppUserServiceImpl appUserService;
	@Autowired
	private JwtConfig jwtConfig;
	@Autowired
	private Algorithm algorithm;
	@Autowired
	@Qualifier("customAuthenticationEntryPoint")
	private AuthenticationEntryPoint authEntryPoint;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private ModelMapper modelMapper;

	@Bean
	public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager(), jwtConfig,
				messageSource, modelMapper);
		authenticationFilter.setFilterProcessesUrl("/sensor/login");

		http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
				.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/user/addUser").permitAll()
						// .requestMatchers("/disableUser").hasAnyRole("ROLE_ADMIN","ROLE_USER")
						.anyRequest().authenticated())
				.addFilter(authenticationFilter)
				.addFilterAfter(new TokenVerifier(jwtConfig, algorithm), AuthenticationFilter.class)
				.exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(authEntryPoint));

		return http.build();

	}

	@Bean
	public AuthenticationManager authenticationManager() throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(appUserService);
		authProvider.setPasswordEncoder(passwordEncoder);
		return new ProviderManager(authProvider);
	}

}
