package com.telran.pizzaservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger/OpenAPI documentation.
 *
 * @author Elena Ivanishcheva
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates a custom OpenAPI object with API documentation information.
     *
     * @return the OpenAPI object
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Pizza Service API")
                        .description("API documentation for Pizza Service")
                        .version("1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
