package com.tienda.sipv.model;

/**
 * Linea de un recibo: el SKU vendido y el precio cobrado en ese momento.
 * Guarda el precio como una "foto" (snapshot) para que no cambie aunque luego
 * cambie el precio de la obra. Va embebida dentro del Recibo.
 */
public class LineaRecibo {

    private String sku;
    private double precioSnapShot;

    public LineaRecibo() {
    }

    public LineaRecibo(String sku, double precioSnapShot) {
        this.sku = sku;
        this.precioSnapShot = precioSnapShot;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public double getPrecioSnapShot() {
        return precioSnapShot;
    }

    public void setPrecioSnapShot(double precioSnapShot) {
        this.precioSnapShot = precioSnapShot;
    }

    @Override
    public String toString() {
        return "LineaRecibo{sku='" + sku + "', precioSnapShot=" + precioSnapShot + "}";
    }
}
