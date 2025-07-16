package com.inventario.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MovimientosStockTest {

    @Test
    void testSettersYGetters() {
        LocalDateTime ahora = LocalDateTime.now();
        MovimientosStock m = new MovimientosStock();

        m.setId(1L);
        m.setIdProducto(99L);
        m.setCantidadAnterior(50L);
        m.setCantidadNueva(30L);
        m.setTipoMovimiento("SALIDA");
        m.setDescripcion("Venta");
        m.setRealizadoEn(ahora);

        assertEquals(1L, m.getId());
        assertEquals(99L, m.getIdProducto());
        assertEquals(50L, m.getCantidadAnterior());
        assertEquals(30L, m.getCantidadNueva());
        assertEquals("SALIDA", m.getTipoMovimiento());
        assertEquals("Venta", m.getDescripcion());
        assertEquals(ahora, m.getRealizadoEn());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime fecha = LocalDateTime.now();

        MovimientosStock m = new MovimientosStock(
                1L, 2L, 3L, 4L, "ENTRADA", "Compra",
                fecha
        );

        assertEquals(1L, m.getId());
        assertEquals(2L, m.getIdProducto());
        assertEquals(3L, m.getCantidadAnterior());
        assertEquals(4L, m.getCantidadNueva());
        assertEquals("ENTRADA", m.getTipoMovimiento());
        assertEquals("Compra", m.getDescripcion());
        assertEquals(fecha, m.getRealizadoEn());
    }

    @Test
    void testEqualsYHashCode() {
        LocalDateTime fecha = LocalDateTime.now();

        MovimientosStock m1 = new MovimientosStock(1L, 2L, 3L, 4L,
                "X", "Y", fecha);
        MovimientosStock m2 = new MovimientosStock(1L, 2L, 3L, 4L,
                "X", "Y", fecha);

        assertEquals(m1, m2);
        assertEquals(m1.hashCode(), m2.hashCode());
    }

    @Test
    void movimientosStock_constructorYSetters() {
        LocalDateTime now = LocalDateTime.now();

        MovimientosStock m = new MovimientosStock();
        m.setId(1L);
        m.setIdProducto(2L);
        m.setCantidadAnterior(50L);
        m.setCantidadNueva(20L);
        m.setTipoMovimiento("SALIDA");
        m.setDescripcion("test");
        m.setRealizadoEn(now);

        assertEquals(1L, m.getId());
        assertEquals(2L, m.getIdProducto());
        assertEquals(50L, m.getCantidadAnterior());
        assertEquals(20L, m.getCantidadNueva());
        assertEquals("SALIDA", m.getTipoMovimiento());
        assertEquals("test", m.getDescripcion());
        assertEquals(now, m.getRealizadoEn());
    }

}
