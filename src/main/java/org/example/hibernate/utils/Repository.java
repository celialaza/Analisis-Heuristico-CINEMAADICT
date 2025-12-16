package org.example.hibernate.utils;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica que define las operaciones CRUD estándar.
 * @param <T> El tipo de entidad (Usuario, Pelicula, Copia).
 */
public interface Repository<T> {

    Optional<T> findById(int id);

    List<T> findAll();

    T  save(T entity);

    T update(T entity);

    void delete(T entity);
}
