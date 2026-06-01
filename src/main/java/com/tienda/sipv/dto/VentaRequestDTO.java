package com.tienda.sipv.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * Datos de entrada para registrar una venta: el cliente y los SKUs vendidos.
 */
public class VentaRequestDTO {

    @NotBlank
    private String cedulaCliente;

    @NotEmpty
    private List<String> skusProductos;

    public VentaRequestDTO() {
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public List<String> getSkusProductos() {
        return skusProductos;
    }

    public void setSkusProductos(List<String> skusProductos) {
        this.skusProductos = skusProductos;
    }
}
