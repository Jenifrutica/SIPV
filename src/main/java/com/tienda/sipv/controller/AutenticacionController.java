package com.tienda.sipv.controller;

import com.tienda.sipv.dto.LoginRequestDTO;
import com.tienda.sipv.dto.TokenResponseDTO;
import com.tienda.sipv.exception.NoAutorizadoException;
import com.tienda.sipv.model.Administrador;
import com.tienda.sipv.model.Empleado;
import com.tienda.sipv.model.Usuario;
import com.tienda.sipv.model.enums.Rol;
import com.tienda.sipv.service.IUsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Modulo de Autenticacion: login y gestion de usuarios.
 *
 * Para las operaciones de administrador se lee la cabecera "X-Rol". En un sistema
 * real ese rol vendria dentro del token; aqui se simplifica con una cabecera.
 */
@RestController
@RequestMapping("/auth")
public class AutenticacionController {

    private final IUsuarioService usuarioService;

    public AutenticacionController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /** Inicia sesion y devuelve un token con el rol. */
    @PostMapping("/login")
    public TokenResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return usuarioService.autenticar(dto);
    }

    /** Crea un empleado. Solo lo puede hacer un administrador. */
    @PostMapping("/usuarios/empleado")
    public ResponseEntity<Usuario> crearEmpleado(@RequestBody Empleado empleado,
                                                 @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        validarAdministrador(rol);
        Usuario creado = usuarioService.crearUsuario(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /** Crea un administrador. Solo lo puede hacer un administrador. */
    @PostMapping("/usuarios/administrador")
    public ResponseEntity<Usuario> crearAdministrador(@RequestBody Administrador administrador,
                                                      @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        validarAdministrador(rol);
        Usuario creado = usuarioService.crearUsuario(administrador);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    /** Lista los usuarios del sistema. Solo lo puede ver un administrador. */
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios(@RequestHeader(value = "X-Rol", required = false) Rol rol) {
        validarAdministrador(rol);
        return usuarioService.listarUsuarios();
    }

    /** Verifica que quien llama tenga rol de administrador. */
    private void validarAdministrador(Rol rol) {
        if (rol != Rol.ADMINISTRADOR) {
            throw new NoAutorizadoException("Esta operacion requiere rol ADMINISTRADOR");
        }
    }
}
