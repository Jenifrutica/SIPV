package com.tienda.sipv.service;

import com.tienda.sipv.dto.ObraDTO;
import com.tienda.sipv.dto.RecepcionDTO;
import com.tienda.sipv.model.Ejemplar;

import java.util.List;

/**
 * Logica del modulo de Inventario: catalogo, recepcion, estados y disponibilidad.
 */
public interface IInventarioService {

    /** Registra una obra en el catalogo. Asigna el id generado al dto recibido. */
    void registrarObra(ObraDTO dto);

    /** Busca obras por titulo; si el titulo es vacio devuelve todo el catalogo. */
    List<ObraDTO> buscarPorTitulo(String titulo);

    /** Obtiene una obra por su id (404 si no existe). */
    ObraDTO obtenerObra(String id);

    /** Actualiza parcialmente una obra; solo cambia los campos enviados (404 si no existe). */
    ObraDTO actualizarObra(String id, ObraDTO datos);

    /** Elimina una obra del catalogo (404 si no existe, 409 si tiene ejemplares). */
    void eliminarObra(String id);

    /** Lista todos los ejemplares del inventario. */
    List<Ejemplar> listarEjemplares();

    /** Obtiene un ejemplar por su SKU (404 si no existe). */
    Ejemplar obtenerEjemplar(String sku);

    /** Crea un ejemplar suelto (valida que la obra exista; estado inicial BODEGA). */
    Ejemplar crearEjemplar(Ejemplar ejemplar);

    /** Actualiza parcialmente un ejemplar (condicion y loteCaja; el estado tiene su propio endpoint). */
    Ejemplar actualizarEjemplar(String sku, Ejemplar datos);

    /** Registra la recepcion de un lote y devuelve los SKUs de los ejemplares creados. */
    List<String> ingresarLote(RecepcionDTO dto);

    /** Cambia el estado de un ejemplar validando la transicion. */
    void cambiarEstadoEjemplar(String sku, String nuevoEstado);

    /** Devuelve cuantos ejemplares de una obra (por titulo) estan en exhibicion. */
    long consultarDisponibilidad(String titulo);

    /** Da de baja (elimina) un ejemplar defectuoso. Operacion solo de administrador. */
    void darDeBaja(String sku);
}
