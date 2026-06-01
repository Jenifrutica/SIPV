package com.tienda.sipv.service;

import com.tienda.sipv.dto.ObraDTO;
import com.tienda.sipv.dto.RecepcionDTO;

import java.util.List;

/**
 * Logica del modulo de Inventario: catalogo, recepcion, estados y disponibilidad.
 */
public interface IInventarioService {

    /** Registra una obra en el catalogo. Asigna el id generado al dto recibido. */
    void registrarObra(ObraDTO dto);

    /** Busca obras por titulo; si el titulo es vacio devuelve todo el catalogo. */
    List<ObraDTO> buscarPorTitulo(String titulo);

    /** Registra la recepcion de un lote y devuelve los SKUs de los ejemplares creados. */
    List<String> ingresarLote(RecepcionDTO dto);

    /** Cambia el estado de un ejemplar validando la transicion. */
    void cambiarEstadoEjemplar(String sku, String nuevoEstado);

    /** Devuelve cuantos ejemplares de una obra (por titulo) estan en exhibicion. */
    long consultarDisponibilidad(String titulo);

    /** Da de baja (elimina) un ejemplar defectuoso. Operacion solo de administrador. */
    void darDeBaja(String sku);
}
