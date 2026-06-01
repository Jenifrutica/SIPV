package com.tienda.sipv.exception;

/**
 * Se lanza cuando una operacion de negocio no es valida, por ejemplo una
 * transicion de estado no permitida o vender un ejemplar que no esta disponible.
 * El manejador global la traduce a un HTTP 409 (conflicto).
 */
public class OperacionInvalidaException extends RuntimeException {
    public OperacionInvalidaException(String mensaje) {
        super(mensaje);
    }
}
