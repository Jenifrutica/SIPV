package com.tienda.sipv.repository;

import com.tienda.sipv.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Acceso a datos de los usuarios (coleccion "usuarios").
 */
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    /** Busca un usuario por su email (se usa en el login). */
    Optional<Usuario> findByEmail(String email);
}
