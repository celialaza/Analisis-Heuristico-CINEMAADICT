package org.example.hibernate.pelicula;

import org.example.hibernate.utils.DataProvider;
import org.example.hibernate.utils.Repository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;

public class PeliculaRepository implements Repository<Pelicula> {

    @Override
    public Optional<Pelicula> findById(int id) {
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Pelicula.class, id));
        }
    }

    @Override
    public List<Pelicula> findAll() {
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            return session.createQuery("FROM Pelicula", Pelicula.class).list();
        }
    }

    @Override
    public Pelicula save(Pelicula entity) {
        Transaction transaction = null;
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Pelicula update(Pelicula entity) {
        Transaction transaction = null;
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Pelicula entity) {
        Transaction transaction = null;
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}