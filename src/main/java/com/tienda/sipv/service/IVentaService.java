package com.tienda.sipv.service;

import com.tienda.sipv.dto.DevolucionDTO;
import com.tienda.sipv.dto.ReciboResponseDTO;
import com.tienda.sipv.dto.VentaRequestDTO;
import com.tienda.sipv.model.Cliente;
import com.tienda.sipv.model.Devolucion;
import com.tienda.sipv.model.Recibo;
import com.tienda.sipv.model.enums.EstadoRecibo;

import java.util.List;

/**
 * Logica del modulo de Ventas: ventas, devoluciones, anulacion y clientes.
 */
public interface IVentaService {

    /** Registra una venta y emite el recibo. */
    ReciboResponseDTO registrarVenta(VentaRequestDTO dto);

    /** Anula un recibo y reintegra los ejemplares a exhibicion. Solo administrador. */
    void anularVenta(String idRecibo);

    /** Registra una devolucion y reintegra el ejemplar al inventario. */
    void procesarDevolucion(DevolucionDTO dto);

    /** Lista los recibos emitidos. */
    List<Recibo> listarRecibos();

    /** Obtiene el detalle de un recibo. */
    Recibo obtenerRecibo(String idRecibo);

    /** Cambia el estado de un recibo (404 si no existe). */
    Recibo actualizarEstadoRecibo(String idRecibo, EstadoRecibo estado);

    /** Elimina un recibo (404 si no existe). */
    void eliminarRecibo(String idRecibo);

    /** Lista todas las devoluciones. */
    List<Devolucion> listarDevoluciones();

    /** Obtiene una devolucion por su id (404 si no existe). */
    Devolucion obtenerDevolucion(String id);

    /** Actualiza parcialmente una devolucion (solo el motivo). */
    Devolucion actualizarDevolucion(String id, Devolucion datos);

    /** Elimina una devolucion (404 si no existe). */
    void eliminarDevolucion(String id);

    // --- Gestion de clientes (el modulo de Ventas usa el repositorio de clientes) ---

    /** Registra un nuevo cliente. */
    Cliente registrarCliente(Cliente cliente);

    /** Lista todos los clientes. */
    List<Cliente> listarClientes();

    /** Obtiene un cliente por su id. */
    Cliente obtenerCliente(String id);

    /** Edita el nombre y el email de un cliente. */
    Cliente editarCliente(String id, Cliente datos);

    /** Actualiza parcialmente un cliente; solo cambia los campos enviados (404 si no existe). */
    Cliente actualizarClienteParcial(String id, Cliente datos);

    /** Elimina un cliente (404 si no existe). */
    void eliminarCliente(String id);
}
