package com.inventario.service;

import com.inventario.client.ProductoClient;
import com.inventario.entity.Inventario;
import com.inventario.entity.MovimientosStock;
import com.inventario.repository.InventarioRepository;
import com.inventario.repository.MovimientosStockRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class InventarioServiceImp implements IInventarioService{

        private final MovimientosStockRepository movimientosStockRepository;
        private final InventarioRepository inventarioRepository;
        private final ProductoClient productoClient;

        @Override
        public Inventario consultarInventario(Long productoId) {
            String productoInfo = productoClient.obtenerProductoPorId(productoId);
            System.out.println("Producto: " + productoInfo);
            return inventarioRepository.findById(productoId)
                    .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        }

    @Override
    public ResponseEntity<String> actualizarInventario(Long productoId, Long cantidadComprada) {
        Inventario inventario = inventarioRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para productoId: " + productoId));

        Long cantidadAnterior = inventario.getCantidad();
        Long cantidadNueva = cantidadAnterior - cantidadComprada;

        if (cantidadNueva < 0) {
            throw new RuntimeException("Stock insuficiente para productoId: " + productoId);
        }

        inventario.setCantidad(cantidadNueva);
        inventarioRepository.save(inventario);

        MovimientosStock movimiento = new MovimientosStock();
        movimiento.setIdProducto(productoId);
        movimiento.setCantidadAnterior(cantidadAnterior);
        movimiento.setCantidadNueva(cantidadNueva);
        movimiento.setTipoMovimiento("SALIDA");
        movimiento.setDescripcion("Actualización por compra");
        movimiento.setRealizadoEn(LocalDateTime.now());

        movimientosStockRepository.save(movimiento);

        System.out.printf("Movimiento registrado: Producto %d, de %d a %d%n",
                productoId, cantidadAnterior, cantidadNueva);

        return ResponseEntity.ok("{\"mensaje\":\"Inventario actualizado correctamente\"}");
    }
}