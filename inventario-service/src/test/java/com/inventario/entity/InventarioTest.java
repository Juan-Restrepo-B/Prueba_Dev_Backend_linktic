package com.inventario.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventarioTest {

    @Test
    void testConstructorConParametros() {
        Inventario inventario = new Inventario(5L, 20L);

        assertEquals(5L, inventario.getIdProducto());
        assertEquals(20L, inventario.getCantidad());
    }

    @Test
    void testSettersYGetters() {
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setIdProducto(10L);
        inventario.setCantidad(100L);

        assertEquals(1L, inventario.getId());
        assertEquals(10L, inventario.getIdProducto());
        assertEquals(100L, inventario.getCantidad());
    }

    @Test
    void testAllArgsConstructor() {
        Inventario inventario = new Inventario(1L, 2L, 3L);
        assertEquals(1L, inventario.getId());
        assertEquals(2L, inventario.getIdProducto());
        assertEquals(3L, inventario.getCantidad());
    }

    @Test
    void testEqualsYHashCode() {
        Inventario i1 = new Inventario(1L, 100L);
        Inventario i2 = new Inventario(1L, 100L);

        assertEquals(i1, i2);
        assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    void inventario_constructorYGetters() {
        Inventario inventario = new Inventario(1L, 50L);
        assertEquals(1L, inventario.getIdProducto());
        assertEquals(50L, inventario.getCantidad());
    }

    @Test
    void inventario_settersFuncionan() {
        Inventario inventario = new Inventario();
        inventario.setId(10L);
        inventario.setIdProducto(5L);
        inventario.setCantidad(99L);

        assertEquals(10L, inventario.getId());
        assertEquals(5L, inventario.getIdProducto());
        assertEquals(99L, inventario.getCantidad());
    }

}
