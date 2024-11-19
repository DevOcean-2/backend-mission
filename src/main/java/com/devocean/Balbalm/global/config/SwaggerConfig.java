package com.devocean.Balbalm.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI customOpenAPI() {
		SecurityScheme securityScheme = new SecurityScheme()
			.name("Authorization")
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT");
		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList("Authorization");
		return new OpenAPI()
			.components(new Components().addSecuritySchemes("Authorization", securityScheme))
			.addSecurityItem(securityRequirement)
			.info(new Info()
				.title("Balbalm API")
				.description("Balbalm 프로젝트의 API 문서입니다.")
				.version("1.0.0"));
	}
}
