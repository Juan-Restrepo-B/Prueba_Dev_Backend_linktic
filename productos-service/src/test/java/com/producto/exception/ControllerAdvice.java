package com.producto.exception;

import com.producto.dto.ErrorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ControllerAdviceTest {

    private final ControllerAdvice advice = new ControllerAdvice();

    @Test
    void shouldHandleRuntimeException() {
        RuntimeException ex = new RuntimeException("Error inesperado");
        ResponseEntity<ErrorDTO> response = advice.runtimeExceptionHandler(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("P-500", response.getBody().getCode());
        assertEquals("Error inesperado", response.getBody().getMessage());
    }

    @Test
    void shouldHandleRequestException() {
        RequestException ex = new RequestException("R-400", "Error de request");
        ResponseEntity<ErrorDTO> response = advice.runtimeRequestHandler(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("R-400", response.getBody().getCode());
        assertEquals("Error de request", response.getBody().getMessage());
    }

    @Test
    void shouldHandleBusinessException() {
        BusinessException ex = new BusinessException("Regla de negocio", "P-401");
        ResponseEntity<ErrorDTO> response = advice.businessExceptionHandler(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("P-401", response.getBody().getCode());
        assertEquals("Regla de negocio", response.getBody().getMessage());
    }
}
