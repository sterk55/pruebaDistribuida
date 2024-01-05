package com.distribuida.servicios;

import com.distribuida.db.Libros;

import java.util.List;

public interface ServicioLibro {

    void insert(Libros l);
    List<Libros> findAll();
    Libros findById(Integer id);

    boolean eliminar (Integer id);

    boolean actualizar (Libros libro);
}
