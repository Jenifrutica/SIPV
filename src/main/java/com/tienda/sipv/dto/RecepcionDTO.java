package com.tienda.sipv.dto;

import com.tienda.sipv.model.enums.Condicion;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Datos de entrada para registrar la recepcion de un lote de mercancia.
 */
public class RecepcionDTO {

    @NotBlank
    private String obraId;

    @Min(1)
    private int cantidad;

    @NotBlank
    private String loteCaja;

    @Schema(description = "Condicion fisica del lote recibido", example = "NUEVO")
    @NotNull
    private Condicion condicion;

    public RecepcionDTO() {
    }

    public String getObraId() {
        return obraId;
    }

    public void setObraId(String obraId) {
        this.obraId = obraId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getLoteCaja() {
        return loteCaja;
    }

    public void setLoteCaja(String loteCaja) {
        this.loteCaja = loteCaja;
    }

    public Condicion getCondicion() {
        return condicion;
    }

    public void setCondicion(Condicion condicion) {
        this.condicion = condicion;
    }
}
