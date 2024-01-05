package com.distribuida.servicios;

import com.distribuida.db.Libros;

import java.util.List;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


@ApplicationScoped
public class ServicioLibroImpl implements ServicioLibro{

    @Inject
    EntityManager em;

    @Override
    public void insert(Libros l) {

        var tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(l);
            tx.commit();
        }
        catch(Exception ex) {
            tx.rollback();
        }
    }

    @Override
    public List<Libros> findAll() {
        return em.createQuery("select o from Libros o").getResultList();
    }

    @Override
    public Libros findById(Integer id) {
        return em.find(Libros.class, id);
    }

    @Override
    public boolean eliminar(Integer id) {
        var tx = em.getTransaction();
        try {
            tx.begin();

            Libros libro = findById(id);

            if (libro != null) {
                em.remove(libro);
                tx.commit();
                return true;
            } else {
                System.out.println("El Libro con ID " + id + " no fue encontrado.");
                tx.rollback();
                return false;
            }
        } catch (Exception ex) {
            tx.rollback();
            return false;
        }    }

    @Override
    public boolean actualizar(Libros libro) {
        var tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(libro);
            tx.commit();
            return true;
        } catch(Exception ex) {
            tx.rollback();
            return false;
        }
    }
}
