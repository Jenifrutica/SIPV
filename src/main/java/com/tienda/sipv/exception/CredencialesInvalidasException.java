package com.tienda.sipv.exception;

/**
 * Se lanza cuando el login recibe credenciales que no son validas.
 * El manejador global la traduce a un HTTP 401 (no autenticado).
 */
public class CredencialesInvalidasException extends RuntimeException {
    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}
