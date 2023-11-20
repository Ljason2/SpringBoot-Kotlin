package com.kotlin.spring.management.configurations.swagger3

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val securitySchemeName = "JWT Token"
        return OpenAPI()
            .components(Components().addSecuritySchemes(securitySchemeName,
                SecurityScheme()
                    .name(securitySchemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("Bearer")
                    .bearerFormat("JWT")
            ))
            .info(Info().title("Swagger3").description("test version").version("alpha test 1.0.0"))
            .addSecurityItem(SecurityRequirement().addList(securitySchemeName))
    }
}