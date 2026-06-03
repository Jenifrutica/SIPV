package com.tienda.sipv.dto;

import com.tienda.sipv.model.enums.Rol;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Datos para la actualizacion parcial (PATCH) de un usuario.
 * Todos los campos son opcionales: solo se aplican los que vengan informados.
 */
public class UsuarioUpdateDTO {

    private String nombre;
    private String email;
    private String password;

    @Schema(description = "Rol del usuario", example = "EMPLEADO")
    private Rol rol;

    public UsuarioUpdateDTO() {
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
}
