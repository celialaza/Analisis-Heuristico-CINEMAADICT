package org.example.hibernate.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.hibernate.copia.CopiaRepository;
import org.example.hibernate.pelicula.PeliculaRepository;
import org.example.hibernate.copia.Copia;
import org.example.hibernate.pelicula.Pelicula;
import org.example.hibernate.services.SessionServices;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddCopiaController implements Initializable {
    @FXML
    private Label lblTitulo;
    @FXML
    private ComboBox<Pelicula> comboPeliculas;
    @FXML
    private TextField txtEstado;
    @FXML
    private TextField txtSoporte;

    private final PeliculaRepository peliculaRepository = new PeliculaRepository();
    private final CopiaRepository copiaRepository = new CopiaRepository();
    private final SessionServices sessionService = new SessionServices();

    // Variable para saber si estamos editando
    private Copia copiaEnEdicion = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarPeliculas();
    }

    private void cargarPeliculas() {
        List<Pelicula> peliculas = peliculaRepository.findAll();
        comboPeliculas.setItems(FXCollections.observableArrayList(peliculas));
    }

    /**
     * Prepara la ventana para MODO EDICIÓN.
     */
    public void setDatos(Copia copia) {
        this.copiaEnEdicion = copia;

        lblTitulo.setText("Editar Copia");
        // Rellenamos los campos con los datos actuales
        txtEstado.setText(copia.getEstado());
        txtSoporte.setText(copia.getSoporte());

        // Seleccionamos la película en el combo
        comboPeliculas.setValue(copia.getPelicula());

        // Deshabilitamos el combo, porque normalmente no se cambia la película de una copia,
        // solo su estado o soporte.
        comboPeliculas.setDisable(true);
    }

    @FXML
    public void onGuardarClick() {
        // Validaciones básicas
        if (comboPeliculas.getValue() == null || txtEstado.getText().isEmpty() || txtSoporte.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos","No debe quedar ningún campo sin rellenar");
            return;
        }

        if (copiaEnEdicion != null) {
            //MODO EDICIÓN
            // Actualizamos los campos del objeto existente
            copiaEnEdicion.setEstado(txtEstado.getText());
            copiaEnEdicion.setSoporte(txtSoporte.getText());

            // Llamamos al método update del Repository
            copiaRepository.update(copiaEnEdicion);

        } else {
            //MODO CREACIÓN
            Copia nuevaCopia = new Copia();
            nuevaCopia.setEstado(txtEstado.getText());
            nuevaCopia.setSoporte(txtSoporte.getText());
            nuevaCopia.setPelicula(comboPeliculas.getValue());
            nuevaCopia.setUsuario(sessionService.getActiveUser());

            copiaRepository.save(nuevaCopia);
        }

        cerrarVentana();
    }

    @FXML
    public void onCancelarClick() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtEstado.getScene().getWindow();
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