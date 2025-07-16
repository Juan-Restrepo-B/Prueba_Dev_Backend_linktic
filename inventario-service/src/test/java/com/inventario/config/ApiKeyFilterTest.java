package com.inventario.config;

import com.inventario.controller.InventarioController;
import com.inventario.service.IInventarioService;
import com.inventario.entity.Inventario;
import com.inventario.config.ApiKeyFilter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InventarioController.class)
@TestPropertySource(properties = "app.api-key=secreta123")
class ApiKeyFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private ApiKeyFilter apiKeyFilter;

    @MockBean
    private IInventarioService inventarioService;

    @Test
    void shouldAllowAccessWithValidApiKey() throws Exception {
        // Simula una respuesta del servicio para evitar NullPointerException
        when(inventarioService.consultarInventario(1L))
                .thenReturn(new Inventario(1L, 100L));

        mockMvc.perform(get("/inventarios/1")
                        .header("X-API-KEY", "secreta123"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBlockAccessWithoutApiKey() throws Exception {
        mockMvc.perform(get("/inventarios/1"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Unauthorized"));
    }
}
