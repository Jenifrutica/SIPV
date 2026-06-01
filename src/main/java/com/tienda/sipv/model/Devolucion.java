package com.tienda.sipv.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Devolucion de un articulo. Es un documento separado que referencia al recibo
 * (idRecibo) y al ejemplar devuelto (sku) sin borrar ni modificar la venta original.
 */
@Document(collection = "devoluciones")
public class Devolucion {

    @Id
    private String idDevolucion;
    private LocalDateTime fecha;
    private String motivo;
    private String idRecibo;
    private String sku;

    public Devolucion() {
    }

    public Devolucion(String idDevolucion, LocalDateTime fecha, String motivo, String idRecibo, String sku) {
        this.idDevolucion = idDevolucion;
        this.fecha = fecha;
        this.motivo = motivo;
        this.idRecibo = idRecibo;
        this.sku = sku;
    }

    public String getIdDevolucion() {
        return idDevolucion;
    }

    public void setIdDevolucion(String idDevolucion) {
        this.idDevolucion = idDevolucion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
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

    @Override
    public String toString() {
        return "Devolucion{idDevolucion='" + idDevolucion + "', fecha=" + fecha
                + ", motivo='" + motivo + "', idRecibo='" + idRecibo + "', sku='" + sku + "'}";
    }
}
