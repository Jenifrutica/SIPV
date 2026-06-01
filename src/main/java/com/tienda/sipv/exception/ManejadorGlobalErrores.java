package com.tienda.sipv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manejador global de errores de la API.
 * Traduce las excepciones de negocio al codigo HTTP correcto y devuelve un
 * mensaje descriptivo en espanol (RNF-008). No expone trazas internas.
 */
@RestControllerAdvice
public class ManejadorGlobalErrores {

    /** Construye el cuerpo del error con el codigo y el mensaje. */
    private ResponseEntity<Map<String, Object>> construir(HttpStatus estado, String mensaje) {
        Map<String, Object> cuerpo = new LinkedHashMap<>();
        cuerpo.put("codigo", estado.value());
        cuerpo.put("mensaje", mensaje);
        return ResponseEntity.status(estado).body(cuerpo);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> noEncontrado(RecursoNoEncontradoException ex) {
        return construir(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(OperacionInvalidaException.class)
    public ResponseEntity<Map<String, Object>> operacionInvalida(OperacionInvalidaException ex) {
        return construir(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(NoAutorizadoException.class)
    public ResponseEntity<Map<String, Object>> noAutorizado(NoAutorizadoException ex) {
        return construir(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, Object>> credencialesInvalidas(CredencialesInvalidasException ex) {
        return construir(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    /** Errores de validacion de los datos de entrada (@Valid) -> 400. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validacion(MethodArgumentNotValidException ex) {
        String mensaje = "Datos invalidos";
        if (ex.getBindingResult().getFieldError() != null) {
            mensaje = ex.getBindingResult().getFieldError().getField() + ": "
                    + ex.getBindingResult().getFieldError().getDefaultMessage();
        }
        return construir(HttpStatus.BAD_REQUEST, mensaje);
    }
}
