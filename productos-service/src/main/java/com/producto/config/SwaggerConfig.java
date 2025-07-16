package com.producto.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    private static final String API_KEY_NAME = "X-API-KEY";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Producto API")
                        .version("1.0")
                        .description("Documentación de la API de Productos"))
                .components(new Components().addSecuritySchemes(API_KEY_NAME,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(API_KEY_NAME)))
                .addSecurityItem(new SecurityRequirement().addList(API_KEY_NAME));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("producto")
                .pathsToMatch("/**")
                .build();
    }
}
