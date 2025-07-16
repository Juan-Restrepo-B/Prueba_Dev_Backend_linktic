package com.producto.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void testProductoGettersSettersEqualsHashCodeToString() {
        Producto p1 = new Producto("Laptop", BigDecimal.valueOf(1500));
        p1.setId(1L);

        Producto p2 = new Producto("Laptop", BigDecimal.valueOf(1500));
        p2.setId(1L);

        Producto p3 = new Producto("Tablet", BigDecimal.valueOf(800));
        p3.setId(2L);

        // Getters
        assertEquals(1L, p1.getId());
        assertEquals("Laptop", p1.getNombre());
        assertEquals(BigDecimal.valueOf(1500), p1.getPrecio());

        // Equals & hashCode
        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());

        // toString
        assertTrue(p1.toString().contains("Laptop"));
    }

    @Test
    void testInventarioGettersSettersEqualsHashCodeToString() {
        Inventario i1 = new Inventario(1L, 50L);
        i1.setId(10L);

        Inventario i2 = new Inventario(1L, 50L);
        i2.setId(10L);

        Inventario i3 = new Inventario(2L, 30L);
        i3.setId(11L);

        // Getters
        assertEquals(10L, i1.getId());
        assertEquals(1L, i1.getIdProducto());
        assertEquals(50L, i1.getCantidad());

        // Equals & hashCode
        assertEquals(i1, i2);
        assertNotEquals(i1, i3);
        assertEquals(i1.hashCode(), i2.hashCode());

        // toString
        assertTrue(i1.toString().contains("50"));
    }

    @Test
    void testProductoEmptyConstructorAndSetters() {
        Producto p = new Producto();
        p.setId(100L);
        p.setNombre(null);
        p.setPrecio(null);

        assertEquals(100L, p.getId());
        assertNull(p.getNombre());
        assertNull(p.getPrecio());
        assertTrue(p.toString().contains("100"));
    }

    @Test
    void testInventarioEmptyConstructorAndSetters() {
        Inventario inv = new Inventario();
        inv.setId(200L);
        inv.setIdProducto(null);
        inv.setCantidad(null);

        assertEquals(200L, inv.getId());
        assertNull(inv.getIdProducto());
        assertNull(inv.getCantidad());
        assertTrue(inv.toString().contains("200"));
    }

}
