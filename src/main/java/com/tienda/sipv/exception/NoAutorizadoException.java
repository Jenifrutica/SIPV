package com.tienda.sipv.exception;

/**
 * Se lanza cuando un usuario intenta una operacion para la que no tiene permiso
 * (por ejemplo, un empleado que intenta anular una venta o dar de baja).
 * El manejador global la traduce a un HTTP 403 (prohibido).
 */
public class NoAutorizadoException extends RuntimeException {
    public NoAutorizadoException(String mensaje) {
        super(mensaje);
    }
}
