package com.cienmilsabores.backendcienmil.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "Backend Cien Mil Sabores API",
        version = "1.0",
        description = "API para la gestion de usuarios, productos, boletas y utilidades relacionadas con regiones y comunas."
    )
)
public class OpenApiConfig {
}
