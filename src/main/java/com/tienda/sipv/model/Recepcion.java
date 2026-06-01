package com.tienda.sipv.model;

import com.tienda.sipv.model.enums.Condicion;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Recepcion: lote o caja de mercancia que ingresa a la tienda.
 * Al registrarse genera N ejemplares. El campo loteCaja permite rastrear la
 * procedencia de un ejemplar defectuoso (trazabilidad, RNF-005): desde el
 * loteCaja del ejemplar se llega a su Recepcion.
 */
@Document(collection = "recepciones")
public class Recepcion {

    @Id
    private String id;
    private String obraId;
    private int cantidad;
    private String loteCaja;
    private Condicion condicion;

    public Recepcion() {
    }

    public Recepcion(String id, String obraId, int cantidad, String loteCaja, Condicion condicion) {
        this.id = id;
        this.obraId = obraId;
        this.cantidad = cantidad;
        this.loteCaja = loteCaja;
        this.condicion = condicion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Recepcion{id='" + id + "', obraId='" + obraId + "', cantidad=" + cantidad
                + ", loteCaja='" + loteCaja + "', condicion=" + condicion + "}";
    }
}
