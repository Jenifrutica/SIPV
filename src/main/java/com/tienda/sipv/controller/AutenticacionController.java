package com.tienda.sipv.controller;

import com.tienda.sipv.dto.LoginRequestDTO;
import com.tienda.sipv.dto.TokenResponseDTO;
import com.tienda.sipv.dto.UsuarioUpdateDTO;
import com.tienda.sipv.exception.NoAutorizadoException;
import com.tienda.sipv.model.Administrador;
import com.tienda.sipv.model.Empleado;
import com.tienda.sipv.model.Usuario;
import com.tienda.sipv.model.enums.Rol;
import com.tienda.sipv.service.IUsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/auth")
@Tag(name = "3. Autenticacion", description = "Login y gestion de usuarios")
public class AutenticacionController {

    private final IUsuarioService usuarioService;

    public AutenticacionController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @PostMapping("/login")
    public TokenResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return usuarioService.autenticar(dto);
    }


    @PostMapping("/usuarios/empleado")
    public ResponseEntity<Usuario> crearEmpleado(@RequestBody Empleado empleado,
                                                 @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        validarAdministrador(rol);
        Usuario creado = usuarioService.crearUsuario(empleado);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @PostMapping("/usuarios/administrador")
    public ResponseEntity<Usuario> crearAdministrador(@RequestBody Administrador administrador,
                                                      @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        validarAdministrador(rol);
        Usuario creado = usuarioService.crearUsuario(administrador);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios(@RequestHeader(value = "X-Rol", required = false) Rol rol) {
        validarAdministrador(rol);
        return usuarioService.listarUsuarios();
    }


    @GetMapping("/usuarios/{id}")
    public Usuario obtenerUsuario(@PathVariable String id,
                                  @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        validarAdministrador(rol);
        return usuarioService.obtenerUsuario(id);
    }


    @PatchMapping("/usuarios/{id}")
    public Usuario actualizarUsuario(@PathVariable String id, @RequestBody UsuarioUpdateDTO datos,
                                     @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        validarAdministrador(rol);
        return usuarioService.actualizarUsuario(id, datos);
    }


    @DeleteMapping("/usuarios/{id}")
    public void eliminarUsuario(@PathVariable String id,
                                @RequestHeader(value = "X-Rol", required = false) Rol rol) {
        validarAdministrador(rol);
        usuarioService.eliminarUsuario(id);
    }


    private void validarAdministrador(Rol rol) {
        if (rol != Rol.ADMINISTRADOR) {
            throw new NoAutorizadoException("Esta operacion requiere rol ADMINISTRADOR");
        }
    }
}
