package com.example.springreactor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("GET", "POST", "PUT", "DELETE") // Autoriser ces méthodes HTTP
				.allowedHeaders("*") // Autoriser tous les headers
				.allowCredentials(false); // Ne pas autoriser les credentials
	}
}
