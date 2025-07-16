package com.producto.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@NoArgsConstructor
@Getter
@Setter
@Data
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
