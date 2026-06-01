package com.tienda.sipv.model;

import com.tienda.sipv.model.enums.Rol;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Usuario del sistema. Clase abstracta: nunca se instancia directamente,
 * solo a traves de sus subclases {@link Administrador} y {@link Empleado}.
 * Todas las subclases se guardan en la coleccion "usuarios".
 *
 * El metodo {@link #tienePermiso(String)} es abstracto: cada subclase decide
 * sus permisos (polimorfismo).
 */
@Document(collection = "usuarios")
public abstract class Usuario {

    @Id
    private String id;
    private String nombre;
    private String email;
    private String password;
    private Rol rol;

    protected Usuario() {
    }

    protected Usuario(String id, String nombre, String email, String password, Rol rol) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.rol = rol;
    }

    /**
     * Indica si el usuario puede ejecutar la operacion indicada.
     * Cada subclase lo resuelve segun su rol.
     */
    public abstract boolean tienePermiso(String operacion);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{id='" + id + "', nombre='" + nombre + "', email='" + email + "', rol=" + rol + "}";
    }
}
