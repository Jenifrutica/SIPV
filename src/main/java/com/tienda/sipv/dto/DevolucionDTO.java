package com.tienda.sipv.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Datos de entrada para registrar una devolucion.
 */
public class DevolucionDTO {

    @NotBlank
    private String idRecibo;

    @NotBlank
    private String sku;

    private String motivo;

    public DevolucionDTO() {
    }

    public String getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(String idRecibo) {
        this.idRecibo = idRecibo;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
