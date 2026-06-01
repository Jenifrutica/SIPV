package com.tienda.sipv.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Cliente de la tienda. Se asocia a las reservas y ventas.
 */
@Document(collection = "clientes")
public class Cliente {

    @Id
    private String id;
    private String cedula;
    private String nombre;
    private String email;

    public Cliente() {
    }

    public Cliente(String id, String cedula, String nombre, String email) {
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    @Override
    public String toString() {
        return "Cliente{id='" + id + "', cedula='" + cedula + "', nombre='" + nombre + "', email='" + email + "'}";
    }
}
