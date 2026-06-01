package com.tienda.sipv.repository;

import com.tienda.sipv.model.Recibo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Acceso a datos de los recibos (coleccion "recibos").
 */
public interface ReciboRepository extends MongoRepository<Recibo, String> {
}
