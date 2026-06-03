package com.tienda.sipv.controller;

import com.tienda.sipv.dto.ObraDTO;
import com.tienda.sipv.dto.RecepcionDTO;
import com.tienda.sipv.exception.NoAutorizadoException;
import com.tienda.sipv.model.Ejemplar;
import com.tienda.sipv.model.enums.EstadoEjemplar;
import com.tienda.sipv.model.enums.Rol;
import com.tienda.sipv.service.IInventarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
@Tag(name = "1. Inventario", description = "Catalogo de obras, ejemplares, recepcion y disponibilidad")
public class InventarioController {

    private final IInventarioService inventarioService;

    public InventarioController(IInventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }


    @PostMapping("/obras")
    public ResponseEntity<ObraDTO> registrarObra(@Valid @RequestBody ObraDTO dto) {
        inventarioService.registrarObra(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


    @GetMapping("/obras")
    public List<ObraDTO> buscarObras(@RequestParam(value = "titulo", required = false) String titulo) {
        return inventarioService.buscarPorTitulo(titulo);
    }


    @GetMapping("/obras/{id}")
    public ObraDTO obtenerObra(@PathVariable String id) {
        return inventarioService.obtenerObra(id);
    }


    @PatchMapping("/obras/{id}")
    public ObraDTO actualizarObra(@PathVariable String id, @RequestBody ObraDTO datos) {
        return inventarioService.actualizarObra(id, datos);
    }


    @DeleteMapping("/obras/{id}")
    public void eliminarObra(@PathVariable String id) {
        inventarioService.eliminarObra(id);
    }


    @GetMapping("/ejemplares")
    public List<Ejemplar> listarEjemplares() {
        return inventarioService.listarEjemplares();
    }


    @GetMapping("/ejemplares/{sku}")
    public Ejemplar obtenerEjemplar(@PathVariable String sku) {
        return inventarioService.obtenerEjemplar(sku);
    }


    @PostMapping("/ejemplares")
    public ResponseEntity<Ejemplar> crearEjemplar(@RequestBody Ejemplar ejemplar) {
        Ejemplar creado = inventarioService.crearEjemplar(ejemplar);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @PatchMapping("/ejemplares/{sku}")
    public Ejemplar actualizarEjemplar(@PathVariable String sku, @RequestBody Ejemplar datos) {
        return inventarioService.actualizarEjemplar(sku, datos);
    }


    @PostMapping("/recepciones")
    public ResponseEntity<List<String>> recibirMercancia(@Valid @RequestBody RecepcionDTO dto) {
        List<String> skus = inventarioService.ingresarLote(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(skus);
    }


    @PatchMapping("/ejemplares/{sku}/estado")
    public void cambiarEstado(@PathVariable String sku, @RequestParam EstadoEjemplar nuevoEstado) {
        inventarioService.cambiarEstadoEjemplar(sku, nuevoEstado.name());
    }


    @GetMapping("/obras/disponibilidad")
    public Map<String, Object> consultarDisponibilidad(@RequestParam String titulo) {
        long disponibles = inventarioService.consultarDisponibilidad(titulo);
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("titulo", titulo);
        respuesta.put("disponibles", disponibles);
        return respuesta;
    }


    @DeleteMapping("/ejemplares/{sku}")
    public void darDeBaja(@PathVariable String sku,
                          @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        if (rol != Rol.ADMINISTRADOR) {
            throw new NoAutorizadoException("Dar de baja requiere rol ADMINISTRADOR");
        }
        inventarioService.darDeBaja(sku);
    }
}
