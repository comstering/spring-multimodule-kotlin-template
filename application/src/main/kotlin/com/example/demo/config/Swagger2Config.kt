package com.example.demo.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Swagger2Config {

    @Bean
    fun publicApi() = GroupedOpenApi.builder()
        .group("swagger-api")
        .pathsToMatch("/swagger/api/**")
        .build()!!

    @Bean
    fun swaggerOpenApi() = OpenAPI()
        .components(Components())
        .info(
            Info()
                .title("Swagger API")
                .description("Show swagger api")
                .version("v1")
    )!!
}