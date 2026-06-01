package com.tienda.sipv.repository;

import com.tienda.sipv.model.Devolucion;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Acceso a datos de las devoluciones (coleccion "devoluciones").
 */
public interface DevolucionRepository extends MongoRepository<Devolucion, String> {
}
