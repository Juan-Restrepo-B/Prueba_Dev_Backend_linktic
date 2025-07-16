package com.producto.dto;

import com.producto.controller.ProductoController;
import com.producto.dto.ErrorDTO;
import com.producto.dto.JsonApiResponse;
import com.producto.dto.NewProductoDTO;
import com.producto.dto.ProductoDto;
import com.producto.exception.BusinessException;
import com.producto.exception.RequestException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void testErrorDTOBuilder() {
        ErrorDTO error = ErrorDTO.builder()
                .code("ERR001")
                .message("Error de prueba")
                .build();

        assertEquals("ERR001", error.getCode());
        assertEquals("Error de prueba", error.getMessage());
    }

    @Test
    void testJsonApiResponseConstructores() {
        ProductoDto producto = new ProductoDto(1L, "Monitor", BigDecimal.valueOf(500));
        JsonApiResponse<ProductoDto> response = new JsonApiResponse<>(producto);

        assertNotNull(response.getData());
        assertNull(response.getMeta());

        Object metaInfo = "meta";
        JsonApiResponse<ProductoDto> responseConMeta = new JsonApiResponse<>(producto, metaInfo);
        assertEquals(metaInfo, responseConMeta.getMeta());
    }

    @Test
    void testNewProductoDTOGetSet() {
        NewProductoDTO dto = new NewProductoDTO("Teclado", BigDecimal.valueOf(100), 10L);

        assertEquals("Teclado", dto.getNombre());
        assertEquals(BigDecimal.valueOf(100), dto.getPrecio());
        assertEquals(10L, dto.getCantidad());

        dto.setNombre("Mouse");
        dto.setPrecio(BigDecimal.valueOf(150));
        dto.setCantidad(5L);

        assertEquals("Mouse", dto.getNombre());
        assertEquals(BigDecimal.valueOf(150), dto.getPrecio());
        assertEquals(5L, dto.getCantidad());
    }

    @Test
    void testProductoDtoGetSet() {
        ProductoDto dto = new ProductoDto();
        dto.setId(1L);
        dto.setNombre("Monitor");
        dto.setPrecio(BigDecimal.valueOf(999.99));

        assertEquals(1L, dto.getId());
        assertEquals("Monitor", dto.getNombre());
        assertEquals(BigDecimal.valueOf(999.99), dto.getPrecio());

        ProductoDto dtoConArgs = new ProductoDto(2L, "Teclado", BigDecimal.valueOf(50.0));
        assertEquals(2L, dtoConArgs.getId());
        assertEquals("Teclado", dtoConArgs.getNombre());
        assertEquals(BigDecimal.valueOf(50.0), dtoConArgs.getPrecio());
    }

    @Test
    void testProductoDtoEqualsHashCodeToString() {
        ProductoDto p1 = new ProductoDto(1L, "Monitor", BigDecimal.valueOf(100));
        ProductoDto p2 = new ProductoDto(1L, "Monitor", BigDecimal.valueOf(100));
        ProductoDto p3 = new ProductoDto(2L, "Otro", BigDecimal.valueOf(200));

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p3.hashCode());
        assertTrue(p1.toString().contains("Monitor"));
    }

    @Test
    void testNewProductoDTOEqualsHashCodeToString() {
        NewProductoDTO dto1 = new NewProductoDTO("Teclado", BigDecimal.TEN, 5L);
        NewProductoDTO dto2 = new NewProductoDTO("Teclado", BigDecimal.TEN, 5L);
        NewProductoDTO dto3 = new NewProductoDTO("Mouse", BigDecimal.ONE, 1L);

        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertEquals(dto1.hashCode(), dto2.hashCode());
        assertNotEquals(dto1.hashCode(), dto3.hashCode());
        assertTrue(dto1.toString().contains("Teclado"));
    }

    @Test
    void testErrorDTOEqualsHashCodeToString() {
        ErrorDTO e1 = ErrorDTO.builder().code("A").message("B").build();
        ErrorDTO e2 = ErrorDTO.builder().code("A").message("B").build();
        ErrorDTO e3 = ErrorDTO.builder().code("X").message("Y").build();

        assertEquals(e1, e2);
        assertNotEquals(e1, e3);
        assertEquals(e1.hashCode(), e2.hashCode());
        assertNotEquals(e1.hashCode(), e3.hashCode());
        assertTrue(e1.toString().contains("B"));
    }

    @Test
    void testJsonApiResponseEqualsHashCodeToString() {
        ProductoDto producto = new ProductoDto(1L, "Camara", BigDecimal.TEN);
        JsonApiResponse<ProductoDto> r1 = new JsonApiResponse<>(producto, "info");
        JsonApiResponse<ProductoDto> r2 = new JsonApiResponse<>(producto, "info");
        JsonApiResponse<ProductoDto> r3 = new JsonApiResponse<>(producto, "otro");

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertTrue(r1.toString().contains("Camara"));
    }

}
