package com.tienda.sipv.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Datos de entrada para iniciar sesion.
 */
public class LoginRequestDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public LoginRequestDTO() {
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
}
