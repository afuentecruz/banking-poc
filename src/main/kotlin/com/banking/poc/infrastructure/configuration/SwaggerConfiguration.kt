package com.banking.poc.infrastructure.configuration

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import org.springframework.context.annotation.Configuration


@Configuration
@io.swagger.v3.oas.annotations.security.SecurityScheme(
    name = "Bearer Authentication",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
class SwaggerConfiguration
