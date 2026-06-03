package com.tienda.sipv.controller;

import com.tienda.sipv.dto.DevolucionDTO;
import com.tienda.sipv.dto.ReciboResponseDTO;
import com.tienda.sipv.dto.VentaRequestDTO;
import com.tienda.sipv.exception.NoAutorizadoException;
import com.tienda.sipv.model.Cliente;
import com.tienda.sipv.model.Recibo;
import com.tienda.sipv.model.enums.Rol;
import com.tienda.sipv.service.IVentaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Modulo de Ventas: ventas, devoluciones, anulacion y clientes.
 * Para anular se lee la cabecera "X-Rol" (debe ser ADMINISTRADOR).
 */
@RestController
public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }

    /** Registra una venta y emite el recibo. */
    @PostMapping("/ventas")
    public ResponseEntity<ReciboResponseDTO> crearVenta(@Valid @RequestBody VentaRequestDTO dto) {
        ReciboResponseDTO recibo = ventaService.registrarVenta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(recibo);
    }

    /** Anula un recibo de venta. Solo administrador. */
    @PostMapping("/ventas/{id}/anular")
    public void anularVenta(@PathVariable String id,
                            @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        if (rol != Rol.ADMINISTRADOR) {
            throw new NoAutorizadoException("Anular una venta requiere rol ADMINISTRADOR");
        }
        ventaService.anularVenta(id);
    }

    /** Lista los recibos emitidos. */
    @GetMapping("/recibos")
    public List<Recibo> listarRecibos() {
        return ventaService.listarRecibos();
    }

    /** Obtiene el detalle de un recibo. */
    @GetMapping("/recibos/{id}")
    public Recibo obtenerRecibo(@PathVariable String id) {
        return ventaService.obtenerRecibo(id);
    }

    /** Registra una devolucion. */
    @PostMapping("/devoluciones")
    public ResponseEntity<Void> registrarDevolucion(@Valid @RequestBody DevolucionDTO dto) {
        ventaService.procesarDevolucion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /** Registra un cliente. */
    @PostMapping("/clientes")
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        Cliente creado = ventaService.registrarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /** Lista los clientes. */
    @GetMapping("/clientes")
    public List<Cliente> listarClientes() {
        return ventaService.listarClientes();
    }

    /** Obtiene un cliente por su id. */
    @GetMapping("/clientes/{id}")
    public Cliente obtenerCliente(@PathVariable String id) {
        return ventaService.obtenerCliente(id);
    }

    /** Edita el nombre y el email de un cliente. */
    @PutMapping("/clientes/{id}")
    public Cliente editarCliente(@PathVariable String id, @RequestBody Cliente datos) {
        return ventaService.editarCliente(id, datos);
    }
}
