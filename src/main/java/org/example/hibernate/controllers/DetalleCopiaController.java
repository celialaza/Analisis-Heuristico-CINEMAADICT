package org.example.hibernate.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.example.hibernate.copia.Copia;

public class DetalleCopiaController {

    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblDirector;
    @FXML
    private Label lblAnio;
    @FXML
    private Label lblGenero;
    @FXML
    private TextArea txtDescripcion; // Usamos TextArea porque la descripción puede ser larga
    @FXML
    private Label lblEstado;
    @FXML
    private Label lblSoporte;
    @FXML
    private Label lblUsuario;

    public void setDatos(Copia copia) {

        lblTitulo.setText(copia.getPelicula().getTitulo());
        lblDirector.setText(copia.getPelicula().getDirector());
        lblAnio.setText(String.valueOf(copia.getPelicula().getAño()));
        lblGenero.setText(copia.getPelicula().getGenero());
        txtDescripcion.setText(copia.getPelicula().getDescripcion());


        lblEstado.setText(copia.getEstado());
        lblSoporte.setText(copia.getSoporte());


        lblUsuario.setText(copia.getUsuario().getNombre_usuario());
    }

    @FXML
    public void onCerrarClick() {

        Stage stage = (Stage) lblTitulo.getScene().getWindow();
        stage.close();
    }
}