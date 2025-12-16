package org.example.hibernate.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.hibernate.copia.CopiaRepository;
import org.example.hibernate.copia.Copia;
import org.example.hibernate.services.SessionServices;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Copia> tablaCopias;
    @FXML
    private TableColumn<Copia, String> colTitulo;
    @FXML
    private TableColumn<Copia, String> colGenero;
    @FXML
    private TableColumn<Copia, String> colEstado;
    @FXML
    private TableColumn<Copia, String> colSoporte;
    @FXML
    private Label lblUsuario;
    @FXML
    private Button btnAñadirPel;

    private final CopiaRepository copiaRepository = new CopiaRepository();
    private final SessionServices sessionService = new SessionServices();
    private final ObservableList<Copia> observableCopias = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configura las columnas
        colEstado.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEstado()));

        colSoporte.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSoporte()));


        colTitulo.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPelicula().getTitulo()));

        colGenero.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPelicula().getGenero()));

        tablaCopias.setItems(observableCopias);

        tablaCopias.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && tablaCopias.getSelectionModel().getSelectedItem() != null) {
                Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();
                abrirVentanaDetalle(seleccionada);
            }
        });


        // Carga datos del usuario logueado
        var usuario = sessionService.getActiveUser();
        if (usuario != null) {
            lblUsuario.setText("Colección de: " + usuario.getNombre_usuario());
            cargarCopias();
        }

        //Verifica que sea el adminsitrador para activar o desactivar el boton de añadirJuego a la BD
        if (!"mariagonzalez".equalsIgnoreCase(usuario.getNombre_usuario())) {
            btnAñadirPel.setDisable(true);
        } else {

            btnAñadirPel.setDisable(false);
        }
    }

    private void cargarCopias() {
        // Limpio la lista anterior
        observableCopias.clear();

        // Obtengo el ID del usuario de la sesión
        int idUsuario = sessionService.getActiveUser().getId();
        // Uso el Repository para traer solo sus copias
        List<Copia> copias = copiaRepository.obtenerCopiasPorUsuario(idUsuario);
        // Las meto en la tabla
        observableCopias.addAll(copias);
    }

    @FXML
    public void onLogout() throws IOException {
        sessionService.logout();
        cambiarVista("login.fxml", "Login");
    }

    // Método para navegar entre ventanas
    private void cambiarVista(String fxml, String titulo) throws IOException {
        Stage stage = (Stage) tablaCopias.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hibernate/" + fxml));
        Scene scene = new Scene(loader.load());
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onEliminarClick() {

        Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta("Error", "Selecciona una copia para eliminar.");
            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Borrado");
        alert.setHeaderText(null);
        alert.setContentText("¿Estás seguro de que quieres borrar la copia de: "
                + seleccionada.getPelicula().getTitulo() + "?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            copiaRepository.delete(seleccionada);

            cargarCopias();
        }
    }
    @FXML
    public void onAnadirClick() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hibernate/add-copia.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Añadir Copia");
            stage.setScene(new Scene(root));

            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);

            stage.showAndWait();

            cargarCopias();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void onModificarClick() {

        Copia seleccionada = tablaCopias.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Advertencia", "Selecciona una copia para modificar.");
            return;
        }

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hibernate/add-copia.fxml"));
            Parent root = loader.load();


            AddCopiaController controller = loader.getController();
            controller.setDatos(seleccionada);

            Stage stage = new Stage();
            stage.setTitle("Modificar Copia");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarCopias();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana de edición.");
        }
    }
    @FXML
    public void onNuevaPeliculaClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hibernate/add-pelicula.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Crear Nueva Película");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.showAndWait();


        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la ventana.");
        }
    }
    private void abrirVentanaDetalle(Copia copia) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/hibernate/detalle-copia.fxml"));
            Parent root = loader.load();

            DetalleCopiaController controller = loader.getController();
            controller.setDatos(copia);

            Stage stage = new Stage();
            stage.setTitle("Detalle de la Película");
            stage.setScene(new Scene(root));
            stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir el detalle.");
        }
    }

    // Método auxiliar reutilizable para alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}