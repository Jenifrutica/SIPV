package com.tienda.sipv.controller;

import com.tienda.sipv.dto.DevolucionDTO;
import com.tienda.sipv.dto.ReciboResponseDTO;
import com.tienda.sipv.dto.VentaRequestDTO;
import com.tienda.sipv.exception.NoAutorizadoException;
import com.tienda.sipv.model.Cliente;
import com.tienda.sipv.model.Devolucion;
import com.tienda.sipv.model.Recibo;
import com.tienda.sipv.model.enums.EstadoRecibo;
import com.tienda.sipv.model.enums.Rol;
import com.tienda.sipv.service.IVentaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Tag(name = "2. Ventas", description = "Ventas, recibos, devoluciones y clientes")
public class VentaController {

    private final IVentaService ventaService;

    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }


    @PostMapping("/ventas")
    public ResponseEntity<ReciboResponseDTO> crearVenta(@Valid @RequestBody VentaRequestDTO dto) {
        ReciboResponseDTO recibo = ventaService.registrarVenta(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(recibo);
    }


    @PostMapping("/ventas/{id}/anular")
    public void anularVenta(@PathVariable String id,
                            @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        if (rol != Rol.ADMINISTRADOR) {
            throw new NoAutorizadoException("Anular una venta requiere rol ADMINISTRADOR");
        }
        ventaService.anularVenta(id);
    }


    @GetMapping("/recibos")
    public List<Recibo> listarRecibos() {
        return ventaService.listarRecibos();
    }


    @GetMapping("/recibos/{id}")
    public Recibo obtenerRecibo(@PathVariable String id) {
        return ventaService.obtenerRecibo(id);
    }


    @PatchMapping("/recibos/{id}")
    public Recibo actualizarRecibo(@PathVariable String id, @RequestParam EstadoRecibo estado) {
        return ventaService.actualizarEstadoRecibo(id, estado);
    }


    @DeleteMapping("/recibos/{id}")
    public void eliminarRecibo(@PathVariable String id) {
        ventaService.eliminarRecibo(id);
    }


    @PostMapping("/devoluciones")
    public ResponseEntity<Void> registrarDevolucion(@Valid @RequestBody DevolucionDTO dto) {
        ventaService.procesarDevolucion(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @GetMapping("/devoluciones")
    public List<Devolucion> listarDevoluciones() {
        return ventaService.listarDevoluciones();
    }


    @GetMapping("/devoluciones/{id}")
    public Devolucion obtenerDevolucion(@PathVariable String id) {
        return ventaService.obtenerDevolucion(id);
    }


    @PatchMapping("/devoluciones/{id}")
    public Devolucion actualizarDevolucion(@PathVariable String id, @RequestBody Devolucion datos) {
        return ventaService.actualizarDevolucion(id, datos);
    }


    @DeleteMapping("/devoluciones/{id}")
    public void eliminarDevolucion(@PathVariable String id) {
        ventaService.eliminarDevolucion(id);
    }


    @PostMapping("/clientes")
    public ResponseEntity<Cliente> registrarCliente(@RequestBody Cliente cliente) {
        Cliente creado = ventaService.registrarCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @GetMapping("/clientes")
    public List<Cliente> listarClientes() {
        return ventaService.listarClientes();
    }


    @GetMapping("/clientes/{id}")
    public Cliente obtenerCliente(@PathVariable String id) {
        return ventaService.obtenerCliente(id);
    }


    @PutMapping("/clientes/{id}")
    public Cliente editarCliente(@PathVariable String id, @RequestBody Cliente datos) {
        return ventaService.editarCliente(id, datos);
    }


    @PatchMapping("/clientes/{id}")
    public Cliente actualizarClienteParcial(@PathVariable String id, @RequestBody Cliente datos) {
        return ventaService.actualizarClienteParcial(id, datos);
    }


    @DeleteMapping("/clientes/{id}")
    public void eliminarCliente(@PathVariable String id) {
        ventaService.eliminarCliente(id);
    }
}
