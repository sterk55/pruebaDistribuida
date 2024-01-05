package com.distribuida;

import com.distribuida.db.Libros;
import com.distribuida.servicios.ServicioLibro;
import com.google.gson.Gson;
import jakarta.enterprise.inject.se.SeContainer;
import spark.Request;
import spark.Response;

import java.util.List;

import static spark.Spark.halt;

public class Principal33 {

    static SeContainer container;

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

    public static Boolean actualizarLibro(Request req, Response res) {

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
        return  true;
    }


    public static Boolean eliminarLibro(Request req, Response res) {
        res.type("application/json");

        String idStr = req.params(":id");
        Integer id = Integer.valueOf(idStr); // Convertir el ID a Integer

        var servicio = container.select(ServicioLibro.class).get();

        // Llamar al método del servicio para eliminar la persona
        boolean eliminado = servicio.eliminar(id);

        if (eliminado) {
            return true;
        } else {
            res.status(404);
            return false;
        }
    }

    static boolean crearLibro(Request req, Response res) {
        var servicio = container.select(ServicioLibro.class).get();
        res.type("application/json");
        Gson gson = new Gson();
        var libroNuevo = gson.fromJson(req.body(), Libros.class);
        servicio.insert(libroNuevo);


        return true;
    }


//    public static void main(String[] args) {
//
//        container = SeContainerInitializer
//                .newInstance()
//                .initialize();
//
//
//        port(8080);
//
//
//
//        Gson gson = new Gson();
//        get("/books", Principal::listarLibros, gson::toJson);
//        get("/books/:id", Principal::buscarLibro, gson::toJson);
//        post("/books",Principal::crearLibro, gson::toJson);
//        delete("/books/:id", Principal::eliminarLibro, gson::toJson);
//        put("/books/:id", Principal::actualizarLibro, gson::toJson);
//
//
//    }
}
