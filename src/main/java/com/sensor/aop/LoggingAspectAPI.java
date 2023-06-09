package com.sensor.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.sensor.entity.AppUser;
import com.sensor.service.AppUserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspectAPI {

	@Autowired
	private AppUserService userService;

	@Before("@within(com.sensor.aop.LoggableAPI)")
	public void logMethodStart(JoinPoint joinPoint) {

		try {

			String methodName = joinPoint.getSignature().getName();
			MDC.put("methodName", methodName);
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (principal instanceof UserDetails) {
				String username = ((UserDetails) principal).getUsername();
				if (username != null && !username.equals("anonymousUser")) {
					AppUser appUser = userService.findByUserName(username);
					MDC.put("username", appUser.getUsername());
					MDC.put("userId", appUser.getId().toString());
				}
			} else {				
				String username = principal.toString();
				if (username != null && !username.equals("anonymousUser")) {
					AppUser appUser = userService.findByUserName(username);
					MDC.put("username", appUser.getUsername());
					MDC.put("userId", appUser.getId().toString());
				}
			}

			log.info("Starting execution of method " + methodName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@After("@within(com.sensor.aop.LoggableAPI)")
	public void logMethodEnd() {
		MDC.remove("methodName");
	}
}
