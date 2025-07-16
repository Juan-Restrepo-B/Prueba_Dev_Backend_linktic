package com.inventario.exception.menssage;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionMenssageTest {

    @Test
    void testConstantesMensajes() {
        assertEquals("Producto no encontrado con id: ", BusinessExceptionMenssage.ERROR_PRODUCTO_NO_ENCONTRADO);
        assertEquals("Stock insuficiente para productoId: ", BusinessExceptionMenssage.ERROR_STOCK_INSUFICIENTE);
    }

    @Test
    void testConstantesCodigos() {
        assertEquals("P-401", BusinessExceptionMenssage.PRODUCTO_NO_ENCONTRADO);
        assertEquals("P-404", BusinessExceptionMenssage.STOCK_INSUFICIENTE);
    }

    @Test
    void testConstructorPrivadoLanzaExcepcion() throws Exception {
        Constructor<BusinessExceptionMenssage> constructor = BusinessExceptionMenssage.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        try {
            constructor.newInstance();
            fail("Se esperaba UnsupportedOperationException");
        } catch (Exception ex) {
            // ✅ Esperamos InvocationTargetException con causa UnsupportedOperationException
            Throwable causa = ex.getCause();
            assertNotNull(causa);
            assertTrue(causa instanceof UnsupportedOperationException);
            assertEquals("Clase utilitaria, no instanciable", causa.getMessage());
        }
    }
}