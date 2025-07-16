package com.inventario.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class ProductoClientTest {

    private ProductoClient productoClient;
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        productoClient = new ProductoClient();

        var restTemplateField = ProductoClient.class.getDeclaredFields()[0];
        restTemplateField.setAccessible(true);
        try {
            restTemplateField.set(productoClient, restTemplate);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        mockServer = MockRestServiceServer.createServer(restTemplate);

        setPrivateField(productoClient, "productServiceUrl", "http://localhost:8081");
        setPrivateField(productoClient, "apiKey", "secreta123");
    }

    @Test
    void obtenerProductoPorId_deberiaRetornarRespuestaMock() {
        Long productoId = 10L;
        String respuestaMock = "Producto info mock";

        mockServer.expect(requestTo("http://localhost:8081/productos/" + productoId))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header("X-API-KEY", "secreta123"))
                .andRespond(withSuccess(respuestaMock, MediaType.APPLICATION_JSON));

        String respuesta = productoClient.obtenerProductoPorId(productoId);

        assertThat(respuesta, equalTo(respuestaMock));
        mockServer.verify();
    }

    private void setPrivateField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Error setting field: " + fieldName, e);
        }
    }
}
