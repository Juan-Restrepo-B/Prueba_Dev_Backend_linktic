package com.producto.controller;

import com.producto.dto.JsonApiResponse;
import com.producto.dto.NewProductoDTO;
import com.producto.dto.ProductoDto;
import com.producto.service.IProductoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final IProductoService productoService;
    public ProductoController(IProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/crear")
    public ResponseEntity<String> crearProducto( @RequestBody @Valid NewProductoDTO newProductoDTO) {
        return productoService.crearProducto(newProductoDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JsonApiResponse<ResponseEntity<JsonApiResponse<ProductoDto>>>> getById(@PathVariable Long id) {
        ResponseEntity<JsonApiResponse<ProductoDto>> dto = productoService.findById(id);
        return ResponseEntity.ok(   new JsonApiResponse<>(dto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarProducto(@PathVariable Long id, @RequestBody @Valid NewProductoDTO newProductoDTO) {
        return productoService.actualizarProducto(id, newProductoDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        return productoService.eliminarProducto(id);
    }

    @GetMapping("/listar")
    public ResponseEntity<JsonApiResponse<List<ProductoDto>>> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return productoService.listarProductos(page, size);
    }
}