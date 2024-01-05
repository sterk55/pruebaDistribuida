package com.distribuida.db;

import jakarta.persistence.*;

@Entity
@Table(name="libro")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Utilizar auto-incremento
    private Integer id;


    private String isbn;

    private String titulo;

    private String autor;

    private Integer precio;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}
