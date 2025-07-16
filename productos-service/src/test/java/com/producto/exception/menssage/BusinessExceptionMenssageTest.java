package com.producto.exception.menssage;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionMenssageTest {

    @Test
    void testConstantesMensajes() {
        assertEquals("Producto no encontrado con id: ", BusinessExceptionMenssage.ERROR_PRODUCTO_NO_ENCONTRADO);
        assertEquals("Inventario no encontrado con id: ", BusinessExceptionMenssage.ERROR_INVENTARIO_NO_ENCONTRADO);
    }

    @Test
    void testConstantesCodigos() {
        assertEquals("P-401", BusinessExceptionMenssage.PRODUCTO_NO_ENCONTRADO);
        assertEquals("P-403", BusinessExceptionMenssage.INVENTARIO_NO_ENCONTRADO);
    }

    @Test
    void testPrivateConstructorCoverage() throws Exception {
        Constructor<BusinessExceptionMenssage> constructor = BusinessExceptionMenssage.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        try {
            constructor.newInstance();
            fail("Se esperaba UnsupportedOperationException");
        } catch (Exception e) {
            Throwable cause = e.getCause();
            assertTrue(cause instanceof UnsupportedOperationException);
            assertEquals("Clase utilitaria, no instanciable", cause.getMessage());
        }
    }
}
