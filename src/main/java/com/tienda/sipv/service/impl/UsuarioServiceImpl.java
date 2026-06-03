package com.tienda.sipv.service.impl;

import com.tienda.sipv.dto.LoginRequestDTO;
import com.tienda.sipv.dto.TokenResponseDTO;
import com.tienda.sipv.dto.UsuarioUpdateDTO;
import com.tienda.sipv.exception.CredencialesInvalidasException;
import com.tienda.sipv.exception.RecursoNoEncontradoException;
import com.tienda.sipv.model.Usuario;
import com.tienda.sipv.repository.UsuarioRepository;
import com.tienda.sipv.service.IUsuarioService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementacion del modulo de Autenticacion.
 */
@Service
public class UsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public TokenResponseDTO autenticar(LoginRequestDTO dto) {
        Optional<Usuario> encontrado = usuarioRepository.findByEmail(dto.getEmail());
        // Comparacion simple de contrasena. En un sistema real se guardaria cifrada.
        if (encontrado.isEmpty() || !encontrado.get().getPassword().equals(dto.getPassword())) {
            throw new CredencialesInvalidasException("Email o contrasena incorrectos");
        }
        Usuario usuario = encontrado.get();
        // Token simplificado (sin JWT) suficiente para el ejercicio.
        String token = "TOKEN-" + UUID.randomUUID();
        return new TokenResponseDTO(token, usuario.getEmail(), usuario.getRol());
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(UUID.randomUUID().toString());
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario obtenerUsuario(String id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el usuario " + id));
    }

    @Override
    public Usuario actualizarUsuario(String id, UsuarioUpdateDTO datos) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el usuario " + id));
        // PATCH: solo cambia los campos enviados (no nulos).
        if (datos.getNombre() != null) {
            usuario.setNombre(datos.getNombre());
        }
        if (datos.getEmail() != null) {
            usuario.setEmail(datos.getEmail());
        }
        if (datos.getPassword() != null) {
            usuario.setPassword(datos.getPassword());
        }
        if (datos.getRol() != null) {
            usuario.setRol(datos.getRol());
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(String id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el usuario " + id));
        usuarioRepository.delete(usuario);
    }
}
