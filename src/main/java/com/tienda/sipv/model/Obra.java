package com.tienda.sipv.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Obra: ficha conceptual de un titulo del catalogo.
 * No guarda cantidad de stock; las existencias fisicas son los Ejemplares.
 */
@Document(collection = "obras")
public class Obra {

    @Id
    private String id;
    private String titulo;
    private String mangaka;
    private String editorial;
    private String demografia;
    private String isbn;
    private double precioListaNuevo;

    public Obra() {
    }

    public Obra(String id, String titulo, String mangaka, String editorial,
                String demografia, String isbn, double precioListaNuevo) {
        this.id = id;
        this.titulo = titulo;
        this.mangaka = mangaka;
        this.editorial = editorial;
        this.demografia = demografia;
        this.isbn = isbn;
        this.precioListaNuevo = precioListaNuevo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMangaka() {
        return mangaka;
    }

    public void setMangaka(String mangaka) {
        this.mangaka = mangaka;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getDemografia() {
        return demografia;
    }

    public void setDemografia(String demografia) {
        this.demografia = demografia;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrecioListaNuevo() {
        return precioListaNuevo;
    }

    public void setPrecioListaNuevo(double precioListaNuevo) {
        this.precioListaNuevo = precioListaNuevo;
    }

    @Override
    public String toString() {
        return "Obra{id='" + id + "', titulo='" + titulo + "', mangaka='" + mangaka
                + "', editorial='" + editorial + "', demografia='" + demografia
                + "', isbn='" + isbn + "', precioListaNuevo=" + precioListaNuevo + "}";
    }
}
