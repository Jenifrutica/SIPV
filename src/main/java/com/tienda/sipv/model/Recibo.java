package com.tienda.sipv.model;

import com.tienda.sipv.model.enums.EstadoRecibo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Recibo de una venta. Guarda la fecha, los totales y su estado.
 * Las lineas (con el precio cobrado por SKU) van embebidas.
 * Anular un recibo cambia su estado a ANULADO; no se borra ni se modifican sus lineas.
 */
@Document(collection = "recibos")
public class Recibo {

    @Id
    private String idRecibo;
    private LocalDateTime fecha;
    private double subtotal;
    private double iva;
    private double total;
    private EstadoRecibo estado;
    private List<LineaRecibo> lineas = new ArrayList<>();

    public Recibo() {
    }

    public Recibo(String idRecibo, LocalDateTime fecha, double subtotal, double iva, double total) {
        this.idRecibo = idRecibo;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva = iva;
        this.total = total;
    }

    public String getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(String idRecibo) {
        this.idRecibo = idRecibo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EstadoRecibo getEstado() {
        return estado;
    }

    public void setEstado(EstadoRecibo estado) {
        this.estado = estado;
    }

    public List<LineaRecibo> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaRecibo> lineas) {
        this.lineas = lineas;
    }

    @Override
    public String toString() {
        return "Recibo{idRecibo='" + idRecibo + "', fecha=" + fecha + ", subtotal=" + subtotal
                + ", iva=" + iva + ", total=" + total + ", estado=" + estado + ", lineas=" + lineas + "}";
    }
}
