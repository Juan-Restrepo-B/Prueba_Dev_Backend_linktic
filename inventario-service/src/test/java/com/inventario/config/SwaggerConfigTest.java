package com.inventario.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

    private final SwaggerConfig config = new SwaggerConfig();

    @Test
    void shouldCreateOpenAPIWithExpectedInfo() {
        OpenAPI openAPI = config.customOpenAPI();

        assertNotNull(openAPI);
        assertEquals("Producto API", openAPI.getInfo().getTitle());
        assertEquals("1.0", openAPI.getInfo().getVersion());
        assertEquals("Documentación de la API de Productos", openAPI.getInfo().getDescription());
    }

    @Test
    void shouldCreateGroupedOpenApi() {
        assertNotNull(config.publicApi());
        assertEquals("producto", config.publicApi().getGroup());
    }
}
