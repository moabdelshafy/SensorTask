package com.sensor.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

	@Pointcut("execution(* com.sensor.controller.*.*(..))")
	public void pointcut() {
	}

	@Before("pointcut()")
	public void logMethodStart(JoinPoint joinPoint) {

		try {

			String methodName = joinPoint.getSignature().getName();
			MDC.put("methodName", methodName);
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String username = principal.toString();

			if (username != null && !username.equals("anonymousUser")) {
				AppUser appUser = userService.findByUserName(username);
				MDC.put("username", appUser.getUsername());
				MDC.put("userId", appUser.getId().toString());
			}
			log.info("Starting execution of method {} ", methodName);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@After("pointcut()")
	public void logMethodEnd(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		log.info("Finished execution of method {} ", methodName);
		MDC.remove("methodName");
	}
}
