package com.tienda.sipv.repository;

import com.tienda.sipv.model.Obra;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Acceso a datos de las obras (coleccion "obras").
 */
public interface ObraRepository extends MongoRepository<Obra, String> {

    /** Busca obras cuyo titulo contiene el texto, sin distinguir mayusculas. */
    List<Obra> findByTituloContainingIgnoreCase(String titulo);

    /** Busca una obra por su ISBN (para evitar duplicados). */
    Optional<Obra> findByIsbn(String isbn);
}
