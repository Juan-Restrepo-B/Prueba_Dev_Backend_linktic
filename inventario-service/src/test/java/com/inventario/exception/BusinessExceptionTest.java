package com.inventario.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BusinessExceptionTest {

    @Test
    void shouldCreateBusinessExceptionWithMessageAndCode() {
        BusinessException exception = new BusinessException("Negocio inválido", "P-400");

        assertEquals("Negocio inválido", exception.getMessage());
        assertEquals("P-400", exception.getCode());
    }

    @Test
    void businessException_mensajeYCodigo() {
        BusinessException ex = new BusinessException("Error msg", "E-001");

        assertEquals("Error msg", ex.getMessage());
        assertEquals("E-001", ex.getCode());
    }

}
