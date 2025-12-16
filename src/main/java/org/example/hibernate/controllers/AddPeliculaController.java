package org.example.hibernate.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hibernate.pelicula.PeliculaRepository;
import org.example.hibernate.pelicula.Pelicula;

public class AddPeliculaController {

    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtDirector;
    @FXML
    private TextField txtGenero;
    @FXML
    private TextField txtAnio;
    @FXML
    private TextArea txtDescripcion;

    private final PeliculaRepository pelicularep = new PeliculaRepository();

    @FXML
    public void onGuardarClick() {

        if (txtTitulo.getText().isEmpty() || txtDirector.getText().isEmpty() ||
                txtAnio.getText().isEmpty() || txtGenero.getText().isEmpty()) {
            mostrarAlerta("Error", "Por favor, rellena todos los campos obligatorios.");
            return;
        }

        try {
            // Creo el objeto Película
            Pelicula pelicula = new Pelicula();
            pelicula.setTitulo(txtTitulo.getText());
            pelicula.setDirector(txtDirector.getText());
            pelicula.setGenero(txtGenero.getText());
            pelicula.setDescripcion(txtDescripcion.getText());


            int anio = Integer.parseInt(txtAnio.getText());
            pelicula.setAño(anio);

            //Guardo en la base de datos general
            pelicularep.save(pelicula);

            cerrarVentana();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "El año debe ser un número válido.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo guardar la película.");
        }
    }

    @FXML
    public void onCancelarClick() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtTitulo.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}