package com.distribuida;

import com.distribuida.db.Libros;
import com.distribuida.servicios.ServicioLibro;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.List;

import static spark.Spark.halt;
import static spark.Spark.*;

public class Principal {

    static SeContainer container;
    static ObjectMapper mapper = new ObjectMapper();

    static List<Libros> listarLibros(Request req, Response res) {
        var servicio = container.select(ServicioLibro.class)
                .get();
        res.type("application/json");

        return servicio.findAll();
    }

    static Libros buscarLibro(Request req, Response res) {
        var servicio = container.select(ServicioLibro.class)
                .get();
        res.type("application/json");

        String _id = req.params(":id");

        var libro =  servicio.findById(Integer.valueOf(_id));

        if(libro==null) {
            // 404
            halt(404, "Libro no encontrado");
        }

        return libro;
    }

    public static Boolean actualizarLibro(Request req, Response res) throws IOException {
        var servicio=container.select(ServicioLibro.class).get();
        res.type("application/json");
        String _id=req.params(":id");
        String requestBody=req.body();

        Libros libro = mapper.readValue(requestBody, Libros.class);
        libro.setId(Integer.valueOf(_id));
        servicio.actualizar(libro);

        if(libro==null){
            halt(404, "Libro no encontrado");
        }

        return  true;
    }

    public static Boolean actualizarLibro2(Request req, Response res) throws IOException {

        var servicio=container.select(ServicioLibro.class).get();
        res.type("application/json");
        String _id=req.params(":id");
        String requestBody=req.body();

        Gson gson= new Gson();
        Libros libro=gson.fromJson(requestBody,Libros.class);
        libro.setId(Integer.valueOf(_id));
        servicio.actualizar(libro);

        if(libro==null){
            halt(404, "Libro no encontrado");
        }
        return true;
    }



    public static Boolean eliminarLibro(Request req, Response res) {
        res.type("application/json");

        String idStr = req.params(":id");
        Integer id = Integer.valueOf(idStr); // Convertir el ID a Integer

        var servicio = container.select(ServicioLibro.class).get();

        boolean eliminado = servicio.eliminar(id);

        if (eliminado) {
            return true;
        } else {
            res.status(404);
            return false;
        }
    }

    static boolean crearLibro(Request req, Response res) throws IOException {
        var servicio = container.select(ServicioLibro.class).get();
        res.type("application/json");

        Libros libro = mapper.readValue(req.body(), Libros.class);
        servicio.insert(libro);


        return true;
    }


    static boolean crealLibro2(Request req, Response res) throws IOException {
        var servicio = container.select(ServicioLibro.class).get();
        res.type("application/json");
        Gson gson = new Gson();
        var libroNuevo = gson.fromJson(req.body(), Libros.class);
        servicio.insert(libroNuevo);

        return true;


    }


    public static void main(String[] args) {

        container = SeContainerInitializer
                .newInstance()
                .initialize();

        port(8080);

        get("/books", Principal::listarLibros, mapper::writeValueAsString);
        get("/books/:id", Principal::buscarLibro, mapper::writeValueAsString);
        post("/books", Principal::crearLibro, mapper::writeValueAsString);
        delete("/books/:id", Principal::eliminarLibro, mapper::writeValueAsString);
        put("/books/:id", Principal::actualizarLibro, mapper::writeValueAsString);

    }
}
