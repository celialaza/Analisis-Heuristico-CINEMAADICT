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
        //SOLUCIÓN PROBLEMA #1
        //Validación de campos vacíos o espacios en blanco
        if (txtTitulo.getText().trim().isEmpty() || txtDirector.getText().trim().isEmpty() ||
                txtAnio.getText().trim().isEmpty() || txtGenero.getText().trim().isEmpty()) {
            mostrarAlerta("Error de Validación", "Por favor, rellena todos los campos. No se permiten solo espacios.");
            return;
        }

        try {
            int anio = Integer.parseInt(txtAnio.getText().trim());
            int anioActual = java.time.Year.now().getValue();

            //Validación del año
            if (anio < 1895 || anio > (anioActual + 1)) {
                mostrarAlerta("Error de Formato", "El año debe ser válido (entre 1895 y " + (anioActual + 1) + ").");
                return;
            }

            //Crear y guardar
            Pelicula pelicula = new Pelicula();
            pelicula.setTitulo(txtTitulo.getText().trim());
            pelicula.setDirector(txtDirector.getText().trim());
            pelicula.setGenero(txtGenero.getText().trim());
            pelicula.setDescripcion(txtDescripcion.getText().trim());
            pelicula.setAño(anio);

            pelicularep.save(pelicula);

            // SOLUCIÓN PROBLEMA #2
            mostrarInformacion("Operación Exitosa", "La película '" + pelicula.getTitulo() + "' ha sido añadida correctamente.");

            cerrarVentana();

        } catch (NumberFormatException e) {
            mostrarAlerta("Error de Formato", "El año debe ser un número entero válido.");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error Crítico", "No se pudo guardar la película en la base de datos.");
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

    private void mostrarInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}