package com.producto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.producto.controller.ProductoController;
import com.producto.dto.*;
import com.producto.service.IProductoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    private final String API_KEY = "secreta123";

    @Test
    void testCrearProducto() throws Exception {
        NewProductoDTO dto = new NewProductoDTO("Test", new BigDecimal("123.45"), 5L);
        Mockito.when(productoService.crearProducto(any())).thenReturn(ResponseEntity.ok("creado"));

        mockMvc.perform(post("/productos/crear")
                        .header("X-API-KEY", API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetById() throws Exception {
        ProductoDto dto = new ProductoDto(2L, "Monitor", new BigDecimal("100.45"));
        Mockito.when(productoService.findById(eq(2L))).thenReturn(
                ResponseEntity.ok(new JsonApiResponse<>(dto))
        );

        mockMvc.perform(get("/productos/2")
                        .header("X-API-KEY", API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.nombre").value("Monitor"));
    }

    @Test
    void testActualizarProducto() throws Exception {
        NewProductoDTO dto = new NewProductoDTO("Nuevo", BigDecimal.ONE, 3L);
        Mockito.when(productoService.actualizarProducto(eq(1L), any())).thenReturn(ResponseEntity.ok("ok"));

        mockMvc.perform(put("/productos/1")
                        .header("X-API-KEY", API_KEY)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void testEliminarProducto() throws Exception {
        Mockito.when(productoService.eliminarProducto(1L)).thenReturn(ResponseEntity.ok("ok"));

        mockMvc.perform(delete("/productos/1")
                        .header("X-API-KEY", API_KEY))
                .andExpect(status().isOk());
    }

    @Test
    void testListarProductos() throws Exception {
        List<ProductoDto> lista = List.of(new ProductoDto(1L, "P1", BigDecimal.TEN));
        JsonApiResponse<List<ProductoDto>> response = new JsonApiResponse<>(lista);
        Mockito.when(productoService.listarProductos(0, 10)).thenReturn(ResponseEntity.ok(response));

        mockMvc.perform(get("/productos/listar")
                        .header("X-API-KEY", API_KEY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].nombre").value("P1"));
    }

}
