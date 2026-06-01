package com.tienda.sipv.service;

import com.tienda.sipv.dto.LoginRequestDTO;
import com.tienda.sipv.dto.TokenResponseDTO;
import com.tienda.sipv.model.Usuario;

import java.util.List;

/**
 * Logica del modulo de Autenticacion: login y gestion de usuarios.
 */
public interface IUsuarioService {

    /** Valida las credenciales y devuelve un token con el rol del usuario. */
    TokenResponseDTO autenticar(LoginRequestDTO dto);

    /** Crea un usuario del sistema (administrador o empleado). */
    Usuario crearUsuario(Usuario usuario);

    /** Lista todos los usuarios del sistema. */
    List<Usuario> listarUsuarios();
}
