package com.tienda.sipv.model;

import com.tienda.sipv.model.enums.Rol;

/**
 * Administrador del sistema. Tiene permisos extendidos: gestionar usuarios,
 * autorizar anulaciones de venta y dar de baja ejemplares.
 *
 * Los metodos de accion (gestionarUsuarios, autorizarAnulacion, darDeBajaEjemplar)
 * representan las capacidades del rol segun el modelo de dominio. La orquestacion
 * y la persistencia reales viven en la capa de servicio.
 */
public class Administrador extends Usuario {

    public Administrador() {
        super();
        setRol(Rol.ADMINISTRADOR);
    }

    public Administrador(String id, String nombre, String email, String password) {
        super(id, nombre, email, password, Rol.ADMINISTRADOR);
    }

    /** El administrador tiene permiso para todas las operaciones sensibles. */
    @Override
    public boolean tienePermiso(String operacion) {
        return true;
    }

    /** Capacidad de gestionar las cuentas de usuario del sistema. */
    public void gestionarUsuarios() {
        // La logica concreta la realiza UsuarioService.
    }

    /** Capacidad de autorizar la anulacion de un recibo de venta. */
    public void autorizarAnulacion(String idRecibo) {
        // La logica concreta la realiza VentaService.
    }

    /** Capacidad de dar de baja un ejemplar defectuoso. */
    public void darDeBajaEjemplar(String sku) {
        // La logica concreta la realiza InventarioService.
    }

    @Override
    public String toString() {
        return "Administrador{id='" + getId() + "', nombre='" + getNombre() + "', email='" + getEmail() + "'}";
    }
}
