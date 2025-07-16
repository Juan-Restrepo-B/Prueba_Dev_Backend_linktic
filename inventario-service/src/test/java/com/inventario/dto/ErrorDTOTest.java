package com.inventario.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorDTOTest {

    @Test
    void testConstructorYGetters() {
        ErrorDTO error = ErrorDTO.builder()
                .code("E-001")
                .message("Error genérico")
                .build();

        assertEquals("E-001", error.getCode());
        assertEquals("Error genérico", error.getMessage());
    }

    @Test
    void testBuilder() {
        ErrorDTO error = ErrorDTO.builder()
                .code("E-002")
                .message("Error con builder")
                .build();

        assertEquals("E-002", error.getCode());
        assertEquals("Error con builder", error.getMessage());
    }

    @Test
    void testEqualsAndHashCode() {
        ErrorDTO error1 = ErrorDTO.builder().code("X").message("Y").build();
        ErrorDTO error2 = ErrorDTO.builder().code("X").message("Y").build();

        assertEquals(error1, error2);
        assertEquals(error1.hashCode(), error2.hashCode());
    }

    @Test
    void testToString() {
        ErrorDTO error = ErrorDTO.builder().code("T").message("Mensaje").build();
        assertTrue(error.toString().contains("T"));
        assertTrue(error.toString().contains("Mensaje"));
    }
}
