package com.inventario.service;

import com.inventario.entity.Inventario;
import org.springframework.http.ResponseEntity;

public interface IInventarioService {
    Inventario consultarInventario(Long productoId);
    ResponseEntity<String> actualizarInventario(Long productoId, Long cantidadComprada);
}
