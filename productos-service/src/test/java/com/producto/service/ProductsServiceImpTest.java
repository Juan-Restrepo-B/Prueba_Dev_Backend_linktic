package com.producto.service;

import com.producto.dto.*;
import com.producto.entity.*;
import com.producto.exception.BusinessException;
import com.producto.repository.*;
import com.producto.service.ProductsServiceImp;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductsServiceImpTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private ProductsServiceImp productsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearProducto_deberiaCrearProductoEInventario() {
        NewProductoDTO dto = new NewProductoDTO("Laptop", BigDecimal.valueOf(15000), 10L);
        Producto producto = new Producto("Laptop", BigDecimal.valueOf(15000));
        producto.setId(1L);

        when(productoRepository.save(any())).thenAnswer(inv -> {
            Producto p = inv.getArgument(0);
            p.setId(1L);
            return p;
        });

        ResponseEntity<String> response = productsService.crearProducto(dto);

        verify(productoRepository).save(any());
        verify(inventarioRepository).save(any());
        assertTrue(response.getBody().contains("Laptop"));
    }

    @Test
    void findById_deberiaRetornarProductoDto() {
        Producto producto = new Producto("Monitor", BigDecimal.valueOf(8000));
        producto.setId(2L);
        when(productoRepository.findById(2L)).thenReturn(Optional.of(producto));

        ResponseEntity<JsonApiResponse<ProductoDto>> response = productsService.findById(2L);

        assertEquals(2L, response.getBody().getData().getId());
        assertEquals("Monitor", response.getBody().getData().getNombre());
    }

    @Test
    void findById_deberiaLanzarExcepcionSiNoExiste() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> productsService.findById(99L));
        assertTrue(ex.getMessage().contains("Producto no encontrado"));
    }

    @Test
    void actualizarProducto_deberiaActualizarSoloCamposNoNulos() {
        Producto producto = new Producto("Teclado", BigDecimal.valueOf(1000));
        producto.setId(3L);
        Inventario inventario = new Inventario(3L, 50L);

        when(productoRepository.findById(3L)).thenReturn(Optional.of(producto));
        when(inventarioRepository.findById(3L)).thenReturn(Optional.of(inventario));

        NewProductoDTO dto = new NewProductoDTO(null, BigDecimal.valueOf(1200), null);

        ResponseEntity<String> response = productsService.actualizarProducto(3L, dto);

        verify(productoRepository).save(producto);
        verify(inventarioRepository).save(inventario);

        assertEquals(BigDecimal.valueOf(1200), producto.getPrecio());
        assertEquals("Teclado", producto.getNombre());
        assertTrue(response.getBody().contains("actualizado"));
    }

    @Test
    void actualizarProducto_deberiaLanzarExcepcionSiNoExiste() {
        when(productoRepository.findById(any())).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> productsService.actualizarProducto(99L, new NewProductoDTO()));
        assertTrue(ex.getMessage().contains("Producto no encontrado"));
    }

    @Test
    void eliminarProducto_deberiaEliminarProductoEInventario() {
        Producto producto = new Producto("Mouse", BigDecimal.valueOf(500));
        producto.setId(4L);
        Inventario inventario = new Inventario(4L, 5L);

        when(productoRepository.findById(4L)).thenReturn(Optional.of(producto));
        when(inventarioRepository.findById(4L)).thenReturn(Optional.of(inventario));

        ResponseEntity<String> response = productsService.eliminarProducto(4L);

        verify(productoRepository).delete(producto);
        verify(inventarioRepository).delete(inventario);

        assertTrue(response.getBody().contains("eliminado"));
    }

    @Test
    void eliminarProducto_deberiaLanzarExcepcionSiInventarioNoExiste() {
        when(inventarioRepository.findById(any())).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> productsService.eliminarProducto(88L));
        assertTrue(ex.getMessage().contains("Inventario no encontrado"));
    }

    @Test
    void listarProductos_deberiaRetornarProductosPaginados() {
        List<Producto> productos = Arrays.asList(
                new Producto("Item1", BigDecimal.valueOf(1000)),
                new Producto("Item2", BigDecimal.valueOf(2000))
        );
        productos.get(0).setId(1L);
        productos.get(1).setId(2L);

        Page<Producto> page = new PageImpl<>(productos);
        when(productoRepository.findAll(any(PageRequest.class))).thenReturn(page);

        ResponseEntity<JsonApiResponse<List<ProductoDto>>> response = productsService.listarProductos(0, 2);

        assertEquals(2, response.getBody().getData().size());
        assertEquals("Item1", response.getBody().getData().get(0).getNombre());
    }

    @Test
    void findById_deberiaLanzarBusinessExceptionConMensajeCorrecto() {
        Long idInexistente = 99L;
        when(productoRepository.findById(idInexistente)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> productsService.findById(idInexistente));

        assertEquals("Producto no encontrado con id: " + idInexistente, exception.getMessage());
        assertEquals("P-401", exception.getCode());
    }

    @Test
    void actualizarProducto_deberiaLanzarBusinessExceptionSiNoExisteInventario() {
        Long id = 10L;
        Producto producto = new Producto("Test", BigDecimal.TEN);
        producto.setId(id);

        when(productoRepository.findById(id)).thenReturn(Optional.of(producto));
        when(inventarioRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> productsService.actualizarProducto(id, new NewProductoDTO("Test", BigDecimal.ONE, 5L)));

        assertEquals("Inventario no encontrado con id: " + id, exception.getMessage());
        assertEquals("P-403", exception.getCode());
    }

    @Test
    void eliminarProducto_deberiaLanzarBusinessExceptionSiNoExisteInventario() {
        Long id = 88L;
        when(inventarioRepository.findById(id)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> productsService.eliminarProducto(id));

        assertEquals("Inventario no encontrado con id: " + id, exception.getMessage());
        assertEquals("P-403", exception.getCode());
    }
    @Test
    void actualizarProducto_noDeberiaActualizarCamposSiSonNull() {
        Producto producto = new Producto("Tablet", BigDecimal.valueOf(3000));
        producto.setId(5L);
        Inventario inventario = new Inventario(5L, 10L);

        when(productoRepository.findById(5L)).thenReturn(Optional.of(producto));
        when(inventarioRepository.findById(5L)).thenReturn(Optional.of(inventario));

        NewProductoDTO dto = new NewProductoDTO(null, null, null);
        ResponseEntity<String> response = productsService.actualizarProducto(5L, dto);

        verify(productoRepository).save(producto);
        verify(inventarioRepository).save(inventario);

        assertEquals(BigDecimal.valueOf(3000), producto.getPrecio());
        assertEquals("Tablet", producto.getNombre());
        assertTrue(response.getBody().contains("actualizado"));
    }
    @Test
    void actualizarProducto_noDeberiaActualizarInventarioSiCantidadEsNull() {
        Producto producto = new Producto("Camara", BigDecimal.valueOf(5000));
        producto.setId(6L);
        Inventario inventario = new Inventario(6L, 7L);

        when(productoRepository.findById(6L)).thenReturn(Optional.of(producto));
        when(inventarioRepository.findById(6L)).thenReturn(Optional.of(inventario));

        NewProductoDTO dto = new NewProductoDTO("Camara", BigDecimal.valueOf(5500), null);
        ResponseEntity<String> response = productsService.actualizarProducto(6L, dto);

        verify(productoRepository).save(producto);
        verify(inventarioRepository).save(inventario);
        assertEquals(7L, inventario.getCantidad());
    }

}
