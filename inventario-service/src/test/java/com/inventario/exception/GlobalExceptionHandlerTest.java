package com.inventario.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void shouldHandleBusinessException() {
        BusinessException ex = new BusinessException("Negocio inválido", "P-402");
        ResponseEntity<Map<String, Object>> response = handler.handleBusinessException(ex);

        assertEquals(400, response.getStatusCodeValue());
        Map<String, Object> body = response.getBody();
        assertEquals("Negocio inválido", body.get("message"));
        assertEquals("P-402", body.get("code"));
        assertNotNull(body.get("timestamp"));
    }

    @Test
    void shouldHandleGeneralException() {
        Exception ex = new Exception("Excepción general");
        ResponseEntity<Map<String, Object>> response = handler.handleGeneralException(ex);

        assertEquals(500, response.getStatusCodeValue());
        Map<String, Object> body = response.getBody();
        assertEquals("Excepción general", body.get("message"));
        assertEquals("GEN-500", body.get("code"));
        assertNotNull(body.get("timestamp"));
    }


}
