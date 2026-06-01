package com.tienda.sipv.repository;

import com.tienda.sipv.model.Recepcion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Acceso a datos de las recepciones (coleccion "recepciones").
 */
public interface RecepcionRepository extends MongoRepository<Recepcion, String> {

    /** Busca la recepcion de un lote o caja (para rastrear la procedencia). */
    Optional<Recepcion> findByLoteCaja(String loteCaja);
}
