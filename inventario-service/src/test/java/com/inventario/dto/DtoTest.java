package com.inventario.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void testErrorDTOBuilder() {
        ErrorDTO error = ErrorDTO.builder()
                .code("ERR001")
                .message("Error de prueba")
                .build();

        assertEquals("ERR001", error.getCode());
        assertEquals("Error de prueba", error.getMessage());
    }

    @Test
    void testErrorDTOEqualsHashCodeToString() {
        ErrorDTO e1 = ErrorDTO.builder().code("A").message("B").build();
        ErrorDTO e2 = ErrorDTO.builder().code("A").message("B").build();
        ErrorDTO e3 = ErrorDTO.builder().code("X").message("Y").build();

        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotEquals(e1.hashCode(), e3.hashCode());
        assertTrue(e1.toString().contains("B"));
    }

    @Test
    void testToStringGeneraCadenaEsperada() {
        ErrorDTO error = ErrorDTO.builder()
                .code("E-123")
                .message("Mensaje de error")
                .build();

        String result = error.toString();

        assertNotNull(result);
        assertTrue(result.contains("E-123"));
        assertTrue(result.contains("Mensaje de error"));
    }

}
