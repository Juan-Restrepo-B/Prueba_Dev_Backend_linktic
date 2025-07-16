package com.producto.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {

    @Test
    void shouldCreateBusinessExceptionWithMessageAndCode() {
        BusinessException exception = new BusinessException("Negocio inválido", "P-400");

        assertEquals("Negocio inválido", exception.getMessage());
        assertEquals("P-400", exception.getCode());
    }
}
