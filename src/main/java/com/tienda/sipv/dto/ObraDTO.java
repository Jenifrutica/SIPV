package com.tienda.sipv.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Datos de entrada y salida de una Obra del catalogo.
 * La demografia es texto libre; no esta restringida a un enum.
 */
public class ObraDTO {

    private String id;

    @NotBlank
    private String titulo;

    private String mangaka;
    private String editorial;
    private String demografia;
    private String isbn;
    private double precioListaNuevo;

    public ObraDTO() {
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
}
