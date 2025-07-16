package com.inventario.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestExceptionTest {

    @Test
    void testConstructorAndGetters() {
        RequestException exception = new RequestException("Solicitud inválida", "R-400");

        assertEquals("Solicitud inválida", exception.getMessage());
        assertEquals("R-400", exception.getCode());
    }

    @Test
    void testEqualsAndHashCode() {
        RequestException e1 = new RequestException("Solicitud inválida", "R-400");
        RequestException e2 = new RequestException("Solicitud inválida", "R-400");
        RequestException e3 = new RequestException("Otro error", "R-401");

        assertEquals(e1, e2);
        assertNotEquals(e1, e3);

        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotEquals(e1.hashCode(), e3.hashCode());
    }

    @Test
    void testToString() {
        RequestException exception = new RequestException("Algo salió mal", "R-500");
        String result = exception.toString();

        assertTrue(result.contains("R-500"));
        assertTrue(result.contains("Algo salió mal"));
    }

    @Test
    void testCanEqual() {
        RequestException e1 = new RequestException("Prueba", "R-123");
        Object other = new RequestException("Prueba", "R-123");

        boolean result = e1.equals(other) && ((RequestException) other).canEqual(e1);
        assertTrue(result);
    }

    @Test
    void testNotEqualsWithDifferentClass() {
        RequestException exception = new RequestException("Mensaje", "R-001");

        assertNotEquals(exception, new Object());
    }
}
