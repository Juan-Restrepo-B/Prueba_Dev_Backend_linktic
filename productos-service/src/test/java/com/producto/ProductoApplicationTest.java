package com.producto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductoApplicationTest {

    @Test
    void main_shouldRunWithoutErrors() {
        assertDoesNotThrow(() -> ProductoApplication.main(new String[]{}));
    }
}