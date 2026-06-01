package com.tienda.sipv.model;

import com.tienda.sipv.exception.OperacionInvalidaException;
import com.tienda.sipv.model.enums.Condicion;
import com.tienda.sipv.model.enums.EstadoEjemplar;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Ejemplar: copia fisica de una Obra, identificada por su SKU.
 * Su estado solo cambia con {@link #actualizarEstado(EstadoEjemplar)}, que valida
 * que la transicion sea permitida.
 *
 * El campo obraId materializa la asociacion "Obra 1 -> 0..* Ejemplar": en MongoDB
 * cada ejemplar guarda a que obra pertenece para poder contar la disponibilidad.
 */
@Document(collection = "ejemplares")
public class Ejemplar {

    @Id
    private String sku;
    private String obraId;
    private Condicion condicion;
    private String loteCaja;
    private EstadoEjemplar estadoActual;

    public Ejemplar() {
    }

    public Ejemplar(String sku, String obraId, Condicion condicion, String loteCaja, EstadoEjemplar estadoInicial) {
        this.sku = sku;
        this.obraId = obraId;
        this.condicion = condicion;
        this.loteCaja = loteCaja;
        this.estadoActual = estadoInicial;
    }

    /**
     * Cambia el estado del ejemplar validando la transicion.
     * Si la transicion no esta permitida, lanza una excepcion de negocio (HTTP 409).
     */
    public void actualizarEstado(EstadoEjemplar nuevoEstado) {
        if (!esTransicionValida(this.estadoActual, nuevoEstado)) {
            throw new OperacionInvalidaException(
                    "Transicion de estado no permitida: " + this.estadoActual + " -> " + nuevoEstado);
        }
        this.estadoActual = nuevoEstado;
    }

    /** Reglas de la maquina de estados del ejemplar. */
    private boolean esTransicionValida(EstadoEjemplar actual, EstadoEjemplar nuevo) {
        return switch (actual) {
            case BODEGA -> nuevo == EstadoEjemplar.EXHIBICION || nuevo == EstadoEjemplar.DEFECTUOSO;
            case EXHIBICION -> nuevo == EstadoEjemplar.RESERVADO || nuevo == EstadoEjemplar.VENDIDO
                    || nuevo == EstadoEjemplar.DEFECTUOSO;
            case RESERVADO -> nuevo == EstadoEjemplar.VENDIDO || nuevo == EstadoEjemplar.EXHIBICION
                    || nuevo == EstadoEjemplar.DEFECTUOSO;
            case VENDIDO -> nuevo == EstadoEjemplar.EXHIBICION || nuevo == EstadoEjemplar.DEFECTUOSO;
            case DEFECTUOSO -> nuevo == EstadoEjemplar.BODEGA;
        };
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getObraId() {
        return obraId;
    }

    public void setObraId(String obraId) {
        this.obraId = obraId;
    }

    public Condicion getCondicion() {
        return condicion;
    }

    public void setCondicion(Condicion condicion) {
        this.condicion = condicion;
    }

    public String getLoteCaja() {
        return loteCaja;
    }

    public void setLoteCaja(String loteCaja) {
        this.loteCaja = loteCaja;
    }

    public EstadoEjemplar getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(EstadoEjemplar estadoActual) {
        this.estadoActual = estadoActual;
    }

    @Override
    public String toString() {
        return "Ejemplar{sku='" + sku + "', obraId='" + obraId + "', condicion=" + condicion
                + ", loteCaja='" + loteCaja + "', estadoActual=" + estadoActual + "}";
    }
}
