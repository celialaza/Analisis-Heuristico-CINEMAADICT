package org.example.hibernate.services;


import org.example.hibernate.usuario.Usuario;

/**
 * Servicio para gestionar la sesión del usuario actual.
 * Funciona como un almacén global para saber quién está logueado.
 */
public class SessionServices {
    private static Usuario activeUser = null;

    /**
     * Establece el usuario actual tras un login exitoso.
     * @param user El usuario que ha iniciado sesión.
     */
    public void login(Usuario user) {
        activeUser = user;
    }

    /**
     * Verifica si hay alguien logueado.
     * @return true si hay usuario, false si no.
     */
    public boolean isLoggedIn(){
        return activeUser != null;
    }

    /**
     * Obtiene el usuario que está logueado actualmente.
     * @return El objeto Usuario.
     */
    public Usuario getActiveUser() {
        return activeUser;
    }

    /**
     * Cierra la sesión.
     */
    public void logout() {
        activeUser = null;
    }
}

