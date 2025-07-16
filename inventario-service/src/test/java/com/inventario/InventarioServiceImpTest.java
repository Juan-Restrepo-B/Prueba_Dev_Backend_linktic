package com.inventario;

import com.inventario.client.ProductoClient;
import com.inventario.entity.Inventario;
import com.inventario.entity.MovimientosStock;
import com.inventario.repository.InventarioRepository;
import com.inventario.repository.MovimientosStockRepository;
import com.inventario.service.InventarioServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventarioServiceImpTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private MovimientosStockRepository movimientosStockRepository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private InventarioServiceImp inventarioService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // -------------------------- consultarInventario --------------------------

    @Test
    void consultarInventario_deberiaRetornarInventario() {
        Long productoId = 1L;
        Inventario inventario = new Inventario(productoId, 50L);

        when(productoClient.obtenerProductoPorId(productoId)).thenReturn("Producto info mock");
        when(inventarioRepository.findById(productoId)).thenReturn(Optional.of(inventario));

        Inventario resultado = inventarioService.consultarInventario(productoId);

        assertNotNull(resultado);
        assertEquals(productoId, resultado.getIdProducto());
        assertEquals(50L, resultado.getCantidad());
    }

    @Test
    void consultarInventario_deberiaLanzarExcepcionSiNoExiste() {
        Long productoId = 2L;

        when(productoClient.obtenerProductoPorId(productoId)).thenReturn("Producto info mock");
        when(inventarioRepository.findById(productoId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                inventarioService.consultarInventario(productoId));

        assertEquals("Inventario no encontrado", ex.getMessage());
    }

    // -------------------------- actualizarInventario --------------------------

    @Test
    void actualizarInventario_deberiaActualizarCorrectamente() {
        Long productoId = 3L;
        Long cantidadInicial = 100L;
        Long cantidadComprada = 30L;
        Inventario inventario = new Inventario(productoId, cantidadInicial);

        when(inventarioRepository.findById(productoId)).thenReturn(Optional.of(inventario));

        ResponseEntity<String> response = inventarioService.actualizarInventario(productoId, cantidadComprada);

        verify(inventarioRepository).save(any(Inventario.class));
        verify(movimientosStockRepository).save(any(MovimientosStock.class));

        assertEquals("{\"mensaje\":\"Inventario actualizado correctamente\"}", response.getBody());
        assertEquals(70L, inventario.getCantidad());
    }

    @Test
    void actualizarInventario_deberiaLanzarExcepcionSiNoHayInventario() {
        Long productoId = 4L;

        when(inventarioRepository.findById(productoId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                inventarioService.actualizarInventario(productoId, 5L));

        assertTrue(ex.getMessage().contains("Inventario no encontrado"));
    }

    @Test
    void actualizarInventario_deberiaLanzarExcepcionSiNoHayStockSuficiente() {
        Long productoId = 5L;
        Inventario inventario = new Inventario(productoId, 10L);

        when(inventarioRepository.findById(productoId)).thenReturn(Optional.of(inventario));

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                inventarioService.actualizarInventario(productoId, 20L));

        assertEquals("Stock insuficiente para productoId: " + productoId, ex.getMessage());
    }
}
