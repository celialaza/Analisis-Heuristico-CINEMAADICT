package org.example.hibernate.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hibernate.usuario.UsuarioRepository;
import org.example.hibernate.usuario.Usuario;
import org.example.hibernate.services.AuthService;
import org.example.hibernate.services.SessionServices;

import java.io.IOException;
import java.util.Optional;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtPassword;

    private final AuthService authService;
    private final SessionServices sessionService;

    public LoginController() {
        this.authService = new AuthService(new UsuarioRepository());
        this.sessionService = new SessionServices();
    }

    @FXML
    public void onLoginButtonClick() {
        String usuario = txtUsuario.getText();
        String password = txtPassword.getText();

        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Error", "Por favor, rellena todos los campos.");
            return;
        }

        Optional<Usuario> userResult = authService.validateUser(usuario, password);

        if (userResult.isPresent()) {

            Usuario usuarioLogueado = userResult.get();


            sessionService.login(usuarioLogueado);
            mostrarInformacion("Éxito", "Bienvenido " + usuarioLogueado.getNombre_usuario());

            irAPantallaPrincipal();


        } else {
            mostrarAlerta("Login Fallido", "Usuario o contraseña incorrectos.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    private void irAPantallaPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hibernate/main.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) txtUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestor de Películas - Mis Copias");
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la pantalla principal.");
        }
    }
}