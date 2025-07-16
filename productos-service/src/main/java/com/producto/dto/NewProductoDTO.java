package com.producto.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
public class NewProductoDTO {
    private String nombre;
    private BigDecimal precio;
    private Long cantidad;

    public NewProductoDTO(String nombre, BigDecimal precio, Long cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }
}
