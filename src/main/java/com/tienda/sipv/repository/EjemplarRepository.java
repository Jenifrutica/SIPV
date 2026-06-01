package com.tienda.sipv.repository;

import com.tienda.sipv.model.Ejemplar;
import com.tienda.sipv.model.enums.EstadoEjemplar;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Acceso a datos de los ejemplares (coleccion "ejemplares").
 */
public interface EjemplarRepository extends MongoRepository<Ejemplar, String> {

    /**
     * Cuenta los ejemplares de una obra que estan en un estado dado.
     * Se usa para la disponibilidad (conteo dinamico, sin aritmetica de stock).
     */
    long countByObraIdAndEstadoActual(String obraId, EstadoEjemplar estadoActual);
}
