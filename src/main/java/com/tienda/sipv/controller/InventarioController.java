package com.tienda.sipv.controller;

import com.tienda.sipv.dto.ObraDTO;
import com.tienda.sipv.dto.RecepcionDTO;
import com.tienda.sipv.exception.NoAutorizadoException;
import com.tienda.sipv.model.Ejemplar;
import com.tienda.sipv.model.enums.EstadoEjemplar;
import com.tienda.sipv.model.enums.Rol;
import com.tienda.sipv.service.IInventarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Modulo de Inventario: catalogo, recepcion, estados y disponibilidad.
 * Para dar de baja se lee la cabecera "X-Rol" (debe ser ADMINISTRADOR).
 */
@RestController
public class InventarioController {

    private final IInventarioService inventarioService;

    public InventarioController(IInventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    /** Registra una obra en el catalogo. */
    @PostMapping("/obras")
    public ResponseEntity<ObraDTO> registrarObra(@Valid @RequestBody ObraDTO dto) {
        inventarioService.registrarObra(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /** Consulta y busca obras; el parametro titulo es opcional. */
    @GetMapping("/obras")
    public List<ObraDTO> buscarObras(@RequestParam(value = "titulo", required = false) String titulo) {
        return inventarioService.buscarPorTitulo(titulo);
    }

    /** Obtiene una obra por su id. */
    @GetMapping("/obras/{id}")
    public ObraDTO obtenerObra(@PathVariable String id) {
        return inventarioService.obtenerObra(id);
    }

    /** Actualiza parcialmente una obra (solo los campos enviados). */
    @PatchMapping("/obras/{id}")
    public ObraDTO actualizarObra(@PathVariable String id, @RequestBody ObraDTO datos) {
        return inventarioService.actualizarObra(id, datos);
    }

    /** Elimina una obra del catalogo. */
    @DeleteMapping("/obras/{id}")
    public void eliminarObra(@PathVariable String id) {
        inventarioService.eliminarObra(id);
    }

    /** Lista todos los ejemplares del inventario. */
    @GetMapping("/ejemplares")
    public List<Ejemplar> listarEjemplares() {
        return inventarioService.listarEjemplares();
    }

    /** Obtiene un ejemplar por su SKU. */
    @GetMapping("/ejemplares/{sku}")
    public Ejemplar obtenerEjemplar(@PathVariable String sku) {
        return inventarioService.obtenerEjemplar(sku);
    }

    /** Registra la recepcion de un lote y devuelve los SKUs creados. */
    @PostMapping("/recepciones")
    public ResponseEntity<List<String>> recibirMercancia(@Valid @RequestBody RecepcionDTO dto) {
        List<String> skus = inventarioService.ingresarLote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(skus);
    }

    /** Cambia el estado de un ejemplar. */
    @PatchMapping("/ejemplares/{sku}/estado")
    public void cambiarEstado(@PathVariable String sku, @RequestParam EstadoEjemplar nuevoEstado) {
        inventarioService.cambiarEstadoEjemplar(sku, nuevoEstado.name());
    }

    /** Consulta publica de disponibilidad de una obra por su titulo. */
    @GetMapping("/obras/disponibilidad")
    public Map<String, Object> consultarDisponibilidad(@RequestParam String titulo) {
        long disponibles = inventarioService.consultarDisponibilidad(titulo);
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("titulo", titulo);
        respuesta.put("disponibles", disponibles);
        return respuesta;
    }

    /** Da de baja (elimina) un ejemplar defectuoso. Solo administrador. */
    @DeleteMapping("/ejemplares/{sku}")
    public void darDeBaja(@PathVariable String sku,
                          @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        if (rol != Rol.ADMINISTRADOR) {
            throw new NoAutorizadoException("Dar de baja requiere rol ADMINISTRADOR");
        }
        inventarioService.darDeBaja(sku);
    }
}
