package com.producto.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RequestExceptionTest {

    @Test
    void shouldCreateRequestExceptionWithCodeAndMessage() {
        RequestException exception = new RequestException("R-400", "Solicitud inválida");

        assertEquals("R-400", exception.getCode());
        assertEquals("Solicitud inválida", exception.getMessage());
    }
}
