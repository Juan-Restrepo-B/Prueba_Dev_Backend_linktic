package com.inventario.entity;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "inventarios")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "producto_id")
    private Long idProducto;
    @Column(name = "cantidad")
    private Long cantidad;

    public Inventario(Long idProducto, Long cantidad) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
    }
}