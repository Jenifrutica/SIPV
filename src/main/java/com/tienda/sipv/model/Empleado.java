package com.tienda.sipv.model;

import com.tienda.sipv.model.enums.Rol;

/**
 * Empleado de la tienda. Realiza la operacion diaria: catalogo, recepcion,
 * cambios de estado de los ejemplares, ventas y devoluciones.
 * No tiene permiso para las operaciones sensibles (anular, dar de baja).
 *
 * Los metodos de accion representan las capacidades del rol segun el modelo de
 * dominio; la orquestacion y la persistencia reales viven en la capa de servicio.
 */
public class Empleado extends Usuario {

    private String codigoEmpleado;

    public Empleado() {
        super();
        setRol(Rol.EMPLEADO);
    }

    public Empleado(String id, String nombre, String email, String password, String codigoEmpleado) {
        super(id, nombre, email, password, Rol.EMPLEADO);
        this.codigoEmpleado = codigoEmpleado;
    }

    /** El empleado no tiene permiso para las operaciones sensibles. */
    @Override
    public boolean tienePermiso(String operacion) {
        return false;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    /** Capacidad de registrar una obra en el catalogo. */
    public void registrarObra() {
        // La logica concreta la realiza InventarioService.
    }

    /** Capacidad de registrar la recepcion de un lote. */
    public void registrarRecepcion() {
        // La logica concreta la realiza InventarioService.
    }

    /** Capacidad de cambiar el estado de un ejemplar. */
    public void cambiarEstadoEjemplar() {
        // La logica concreta la realiza InventarioService.
    }

    /** Capacidad de registrar una venta. */
    public void registrarVenta() {
        // La logica concreta la realiza VentaService.
    }

    /** Capacidad de registrar una devolucion. */
    public void registrarDevolucion() {
        // La logica concreta la realiza VentaService.
    }

    @Override
    public String toString() {
        return "Empleado{id='" + getId() + "', nombre='" + getNombre() + "', codigoEmpleado='" + codigoEmpleado + "'}";
    }
}
