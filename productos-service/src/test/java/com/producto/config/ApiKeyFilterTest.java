package com.producto.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.producto.controller.ProductoController;
import com.producto.service.IProductoService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductoController.class)
@TestPropertySource(properties = "app.api-key=secreta123")
class ApiKeyFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ApiKeyFilter apiKeyFilter;

    @MockBean
    private IProductoService productoService;

    @Test
    void shouldAllowAccessWithValidApiKey() throws Exception {
        mockMvc.perform(get("/productos/listar")
                        .header("X-API-KEY", "secreta123"))
                .andExpect(status().isOk()); // No se bloquea por filtro
    }

    @Test
    void shouldBlockAccessWithoutApiKey() throws Exception {
        mockMvc.perform(get("/productos/listar"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Unauthorized"));
    }
}
