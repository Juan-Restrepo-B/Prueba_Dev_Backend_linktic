package com.inventario.controller;

import com.inventario.entity.Inventario;
import com.inventario.service.IInventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventarios")
@RequiredArgsConstructor
public class InventarioController {

    private final IInventarioService inventarioService;


    @GetMapping("/{productoId}")
    public Inventario consultar(@PathVariable Long productoId) {
        return inventarioService.consultarInventario(productoId);
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<String> actualizar(@PathVariable Long productoId,
                                             @RequestParam Long cantidad) {
        return inventarioService.actualizarInventario(productoId, cantidad);
    }
}
