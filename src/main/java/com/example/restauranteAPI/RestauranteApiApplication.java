package com.example.restauranteAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class RestauranteApiApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(RestauranteApiApplication.class, args);
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // Aplica a todas as rotas
				.allowedOrigins("https://d2wk73bhxwtu9d.cloudfront.net") // Seu domínio CloudFront
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
				.allowedHeaders("*")
				.allowCredentials(true)
				.maxAge(3600); 
	}
}
