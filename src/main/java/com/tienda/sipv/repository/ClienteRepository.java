package com.tienda.sipv.repository;

import com.tienda.sipv.model.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Acceso a datos de los clientes (coleccion "clientes").
 */
public interface ClienteRepository extends MongoRepository<Cliente, String> {

    /** Busca un cliente por su cedula. */
    Optional<Cliente> findByCedula(String cedula);
}
