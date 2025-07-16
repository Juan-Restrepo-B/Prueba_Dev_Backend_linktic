package com.inventario.controller;

import com.inventario.entity.Inventario;
import com.inventario.service.IInventarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final String API_KEY = "secreta123";

    @MockBean
    private IInventarioService inventarioService;

    @Test
    void consultar_deberiaRetornarInventario() throws Exception {
        Long productoId = 1L;
        Inventario inventario = new Inventario(productoId, 100L);
        inventario.setId(1L);

        when(inventarioService.consultarInventario(productoId)).thenReturn(inventario);

        mockMvc.perform(get("/inventarios/{productoId}", productoId)
                        .header("X-API-KEY", API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(inventario.getId()))
                .andExpect(jsonPath("$.idProducto").value(productoId))
                .andExpect(jsonPath("$.cantidad").value(100L));
    }

    @Test
    void actualizar_deberiaRetornarMensajeDeExito() throws Exception {
        Long productoId = 5L;
        Long cantidad = 10L;
        String mensajeRespuesta = "{\"mensaje\":\"Inventario actualizado correctamente\"}";

        when(inventarioService.actualizarInventario(productoId, cantidad))
                .thenReturn(ResponseEntity.ok(mensajeRespuesta));

        mockMvc.perform(put("/inventarios/{productoId}", productoId)
                        .param("cantidad", String.valueOf(cantidad))
                        .header("X-API-KEY", API_KEY))
                .andExpect(status().isOk())
                .andExpect(content().string(mensajeRespuesta));
    }
}
