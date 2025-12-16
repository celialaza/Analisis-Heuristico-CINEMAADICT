package org.example.hibernate.services;


import org.example.hibernate.usuario.Usuario;
import org.example.hibernate.usuario.UsuarioRepository;

import java.util.Optional;

/**
 * Servicio encargado de la autenticación (Login).
 */
public class AuthService {
    private final UsuarioRepository usuarioRepository;


    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Valida las credenciales del usuario.
     * @param nombreUsuario El nombre ingresado.
     * @param password La contraseña ingresada.
     * @return Un Optional que contiene el Usuario si las credenciales son correctas.
     */
    public Optional<Usuario> validateUser(String nombreUsuario, String password) {

        Optional<Usuario> userOpt = usuarioRepository.obtenerPorNombre(nombreUsuario);

        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();

            //SOLUCION 5. Tener en cuenta mayúsculas/minúsculas (que el usuario sea exactamente el mismo)
            if(!user.getNombre_usuario().equals(nombreUsuario)) {
                return Optional.empty();
            }

            if (user.getContraseña().equals(password)) {
                return Optional.of(user);
            } else {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}

