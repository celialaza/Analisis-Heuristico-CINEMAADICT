package org.example.hibernate.usuario;

import org.example.hibernate.utils.DataProvider;
import org.example.hibernate.utils.Repository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository implements Repository<Usuario> {

    @Override
    public Optional<Usuario> findById(int id) {
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.get(Usuario.class, id));
        }
    }

    @Override
    public List<Usuario> findAll() {
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            return session.createQuery("FROM Usuario", Usuario.class).list();
        }
    }

    @Override
    public Usuario save(Usuario entity) {
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
    public Usuario update(Usuario entity) {
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
    public void delete(Usuario entity) {
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

    public Optional<Usuario> obtenerPorNombre(String nombreUsuario) {
        try (Session session = DataProvider.getSessionFactory().openSession()) {
            Query<Usuario> query = session.createQuery("FROM Usuario WHERE nombre_usuario = :nombre", Usuario.class);
            query.setParameter("nombre", nombreUsuario);
            return Optional.ofNullable(query.uniqueResult());
        }
    }
}