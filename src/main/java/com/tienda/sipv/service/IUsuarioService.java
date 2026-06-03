package com.tienda.sipv.service;

import com.tienda.sipv.dto.LoginRequestDTO;
import com.tienda.sipv.dto.TokenResponseDTO;
import com.tienda.sipv.dto.UsuarioUpdateDTO;
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

    /** Obtiene un usuario por su id (404 si no existe). */
    Usuario obtenerUsuario(String id);

    /** Actualiza parcialmente un usuario (nombre, email, password, rol). */
    Usuario actualizarUsuario(String id, UsuarioUpdateDTO datos);

    /** Elimina un usuario (404 si no existe). */
    void eliminarUsuario(String id);
}
