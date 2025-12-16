package org.example.hibernate.copia;

import org.example.hibernate.utils.DataProvider;
import org.example.hibernate.utils.Repository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Optional;

public class CopiaRepository implements Repository<Copia> {

    @Override
    public Optional<Copia> findById(int id) {
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Copia.class, id));
        }
    }

    @Override
    public List<Copia> findAll() {
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            return session.createQuery("FROM Copia", Copia.class).list();
        }
    }

    @Override
    public Copia save(Copia entity) {
        Transaction transaction = null;
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            // Uso merge para que sirva tanto para guardar nuevo como actualizar
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
    public Copia update(Copia entity) {
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
    public void delete(Copia entity) {
        Transaction transaction = null;
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.contains(entity) ? entity : session.merge(entity));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    public List<Copia> obtenerCopiasPorUsuario(int idUsuario) {
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            Query<Copia> query = session.createQuery(
                    "FROM Copia c JOIN FETCH c.pelicula JOIN FETCH c.usuario WHERE c.usuario.id = :id",
                    Copia.class
            );
            query.setParameter("id", idUsuario);
            return query.list();
        }
    }
    }
