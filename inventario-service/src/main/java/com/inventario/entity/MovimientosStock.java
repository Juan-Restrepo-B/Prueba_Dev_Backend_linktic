package com.inventario.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "movimientos_stock")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Data
public class MovimientosStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "producto_id")
    private Long idProducto;
    @Column(name = "cantidad_anterior")
    private Long cantidadAnterior;
    @Column(name = "cantidad_nueva")
    private Long cantidadNueva;
    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "realizado_en")
    private LocalDateTime realizadoEn;

}