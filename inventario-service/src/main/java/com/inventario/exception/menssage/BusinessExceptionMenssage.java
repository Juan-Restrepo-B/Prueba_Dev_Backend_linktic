package com.inventario.exception.menssage;

public class BusinessExceptionMenssage {

    private BusinessExceptionMenssage() {
        throw new UnsupportedOperationException("Clase utilitaria, no instanciable");
    }
    public static final String ERROR_PRODUCTO_NO_ENCONTRADO = "Producto no encontrado con id: ";
    public static final String ERROR_STOCK_INSUFICIENTE = "Stock insuficiente para productoId: ";
    public static final String PRODUCTO_NO_ENCONTRADO = "P-401";
    public static final String STOCK_INSUFICIENTE = "P-404";
}
