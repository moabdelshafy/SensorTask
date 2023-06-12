package com.sensor.config;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RestApiURLScanner implements RequestMatcher {

	private final RequestMappingHandlerMapping handlerMapping;
	private final List<String> restApiUrls = new ArrayList<>();
	List<String> output = new ArrayList<>();
	String pattern = "\\[([^\\[\\]]+)\\]";

	@Autowired
	public RestApiURLScanner(RequestMappingHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	@PostConstruct
	public void init() {
		handlerMapping.getHandlerMethods().forEach((key, value) -> output.add(key.toString()));
		Pattern regexPattern = Pattern.compile(pattern);

		for (String item : output) {
			Matcher matcher = regexPattern.matcher(item);
			if (matcher.find()) {
				restApiUrls.add(matcher.group(1));
			}
		}
	}

	public List<String> getAllRestApiUrls() {
		return restApiUrls;
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		String requestUrl = request.getRequestURI();
		if (requestUrl.equals("/error")) {
			return false;
		}
		return restApiUrls.contains(requestUrl);
	}
}
