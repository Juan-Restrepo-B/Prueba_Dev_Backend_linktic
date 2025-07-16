package com.inventario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class InventarioApplicationTest {
    @Test
    void main_shouldRunWithoutErrors() {
        assertDoesNotThrow(() -> InventarioApplication.main(new String[]{}));
    }
}
