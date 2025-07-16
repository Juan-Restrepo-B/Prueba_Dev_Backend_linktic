package com.producto.service;

import com.producto.dto.JsonApiResponse;
import com.producto.dto.NewProductoDTO;
import com.producto.dto.ProductoDto;
import com.producto.entity.Inventario;
import com.producto.entity.Producto;
import com.producto.exception.BusinessException;
import com.producto.exception.menssage.BusinessExceptionMenssage;
import com.producto.repository.InventarioRepository;
import com.producto.repository.ProductoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductsServiceImp implements IProductoService {
    private final ProductoRepository productoRepository;
    private final InventarioRepository inventarioRepository;

    @Override
    public ResponseEntity<String> crearProducto(NewProductoDTO newProducto) {

        Long idProducto = this.saveProducto(newProducto);

        this.saveInventario(newProducto, idProducto);

        return ResponseEntity.ok("{ \"mensaje\": \"Se ha registrado el producto: " + newProducto.getNombre()  +
                " exitosamente.\" }");
    }

    Long saveProducto(NewProductoDTO newProducto) {
        Producto producto = new Producto(
                newProducto.getNombre(),
                newProducto.getPrecio()
        );

        productoRepository.save(producto);

        return producto.getId();
    }

    void saveInventario(NewProductoDTO newProducto, Long idProducto) {
        Inventario inventario = new Inventario(
                idProducto,
                newProducto.getCantidad()
        );

        inventarioRepository.save(inventario);
    }

    public ResponseEntity<JsonApiResponse<ProductoDto>> findById(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        BusinessExceptionMenssage.ERROR_PRODUCTO_NO_ENCONTRADO + id, "P-400"
                ));

        ProductoDto productoDto = new ProductoDto(
                producto.getId(),
                producto.getNombre(),
                producto.getPrecio()
        );

        return ResponseEntity.ok(new JsonApiResponse<>(productoDto));
    }
    @Override
    public ResponseEntity<String> actualizarProducto(Long id, NewProductoDTO newProductoDTO) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        BusinessExceptionMenssage.ERROR_PRODUCTO_NO_ENCONTRADO + id, "P-401"
                ));

        if (newProductoDTO.getNombre() != null) {
            producto.setNombre(newProductoDTO.getNombre());
        }

        if (newProductoDTO.getPrecio() != null) {
            producto.setPrecio(newProductoDTO.getPrecio());
        }

        productoRepository.save(producto);

        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        BusinessExceptionMenssage.ERROR_INVENTARIO_NO_ENCONTRADO + id, "P-402"
                ));

        if (newProductoDTO.getCantidad() != null) {
            inventario.setCantidad(newProductoDTO.getCantidad());
        }

        inventarioRepository.save(inventario);

        return ResponseEntity.ok("{ \"mensaje\": \"Producto actualizado exitosamente.\" }");
    }

    @Override
    public ResponseEntity<String> eliminarProducto(Long id) {

        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        BusinessExceptionMenssage.ERROR_INVENTARIO_NO_ENCONTRADO + id, "P-402"
                ));

        inventarioRepository.delete(inventario);

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        BusinessExceptionMenssage.ERROR_PRODUCTO_NO_ENCONTRADO + id, "P-401"
                ));

        productoRepository.delete(producto);

        return ResponseEntity.ok("{ \"mensaje\": \"Producto eliminado exitosamente.\" }");
    }

    @Override
    public ResponseEntity<JsonApiResponse<List<ProductoDto>>> listarProductos(int page, int size) {
        Page<Producto> productoPage = productoRepository.findAll(PageRequest.of(page, size));

        List<ProductoDto> productDos = productoPage.getContent().stream()
                .map(producto -> new ProductoDto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getPrecio()))
                .collect(Collectors.toList());

        Map<String, Object> meta = new HashMap<>();
        meta.put("currentPage", productoPage.getNumber());
        meta.put("totalPages", productoPage.getTotalPages());
        meta.put("totalItems", productoPage.getTotalElements());
        meta.put("pageSize", productoPage.getSize());

        JsonApiResponse<List<ProductoDto>> response = new JsonApiResponse<>(productDos, meta);
        return ResponseEntity.ok(response);
    }


}
