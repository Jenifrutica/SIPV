package com.tienda.sipv.dto;

import com.tienda.sipv.model.LineaRecibo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Datos de salida de un recibo emitido.
 */
public class ReciboResponseDTO {

    private String idRecibo;
    private double subtotal;
    private double iva;
    private LocalDateTime fecha;
    private double total;
    private List<LineaRecibo> lineas;

    public ReciboResponseDTO() {
    }

    public ReciboResponseDTO(String idRecibo, double subtotal, double iva, LocalDateTime fecha,
                             double total, List<LineaRecibo> lineas) {
        this.idRecibo = idRecibo;
        this.subtotal = subtotal;
        this.iva = iva;
        this.fecha = fecha;
        this.total = total;
        this.lineas = lineas;
    }

    public String getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(String idRecibo) {
        this.idRecibo = idRecibo;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<LineaRecibo> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaRecibo> lineas) {
        this.lineas = lineas;
    }
}
