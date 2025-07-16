package com.inventario.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JsonApiResponseTest {

    @Test
    void testConstructorConData() {
        String data = "Contenido de prueba";
        JsonApiResponse<String> response = new JsonApiResponse<>(data);

        assertEquals(data, response.getData());
        assertNull(response.getMeta());
    }

    @Test
    void testConstructorConDataYMeta() {
        String data = "Contenido";
        String meta = "Información meta";

        JsonApiResponse<String> response = new JsonApiResponse<>(data, meta);

        assertEquals(data, response.getData());
        assertEquals(meta, response.getMeta());
    }

    @Test
    void testSettersYGetters() {
        JsonApiResponse<String> response = new JsonApiResponse<>();
        response.setData("Valor");
        response.setMeta("Meta info");

        assertEquals("Valor", response.getData());
        assertEquals("Meta info", response.getMeta());
    }

    @Test
    void testToStringAndEquals() {
        JsonApiResponse<String> r1 = new JsonApiResponse<>("data", "meta");
        JsonApiResponse<String> r2 = new JsonApiResponse<>("data", "meta");

        assertEquals(r1, r2);
        assertTrue(r1.toString().contains("data"));
    }
}
