package org.example.hibernate.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/*
* Clase que se encarga de conectar con la base de datos
*/
public class DataProvider {

    //Patrón Singleton.
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Carga la configuración desde hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {

            System.err.println("Error al inicializar la SessionFactory de Hibernate: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Método público para obtener la SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Método para cerrar la SessionFactory
    public static void shutdown() {
        getSessionFactory().close();
    }
}
