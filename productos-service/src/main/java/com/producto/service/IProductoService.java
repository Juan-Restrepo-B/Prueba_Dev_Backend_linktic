package com.producto.service;

import com.producto.dto.JsonApiResponse;
import com.producto.dto.NewProductoDTO;
import com.producto.dto.ProductoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface IProductoService {
    ResponseEntity<String> crearProducto(NewProductoDTO newProducto);
    ResponseEntity<JsonApiResponse<ProductoDto>> findById(Long id);
    ResponseEntity<String> actualizarProducto(@PathVariable Long id, NewProductoDTO newProductoDTO);
    ResponseEntity<String> eliminarProducto(@PathVariable Long id);
    ResponseEntity<JsonApiResponse<List<ProductoDto>>> listarProductos(int page, int size);
}
