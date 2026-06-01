package com.tienda.sipv.service;

import com.tienda.sipv.dto.DevolucionDTO;
import com.tienda.sipv.dto.ReciboResponseDTO;
import com.tienda.sipv.dto.VentaRequestDTO;
import com.tienda.sipv.model.Cliente;
import com.tienda.sipv.model.Recibo;

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

    // --- Gestion de clientes (el modulo de Ventas usa el repositorio de clientes) ---

    /** Registra un nuevo cliente. */
    Cliente registrarCliente(Cliente cliente);

    /** Lista todos los clientes. */
    List<Cliente> listarClientes();

    /** Obtiene un cliente por su id. */
    Cliente obtenerCliente(String id);

    /** Edita el nombre y el email de un cliente. */
    Cliente editarCliente(String id, Cliente datos);
}
