package com.producto.exception.menssage;

public class BusinessExceptionMenssage {

    private BusinessExceptionMenssage() {
        throw new UnsupportedOperationException("Clase utilitaria, no instanciable");
    }
    public static final String ERROR_PRODUCTO_NO_ENCONTRADO = "Producto no encontrado con id: ";
    public static final String ERROR_INVENTARIO_NO_ENCONTRADO = "Inventario no encontrado con id: ";
    public static final String PRODUCTO_NO_ENCONTRADO = "P-401";
    public static final String INVENTARIO_NO_ENCONTRADO = "P-403";
}
