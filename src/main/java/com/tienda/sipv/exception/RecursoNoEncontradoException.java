package com.tienda.sipv.exception;

/**
 * Se lanza cuando no se encuentra un recurso (obra, ejemplar, recibo, etc.).
 * El manejador global la traduce a un HTTP 404.
 */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
