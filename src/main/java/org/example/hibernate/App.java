package org.example.hibernate;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.hibernate.utils.DataProvider;

import java.io.IOException;

public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        // Carga la vista de login al iniciar
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        scene = new Scene(fxmlLoader.load(), 400, 300);
        stage.setTitle("Gestor de Películas - Login");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        //Abre la sesión
        DataProvider.getSessionFactory();
    }

    @Override
    public void stop() throws Exception {
        // Cierra la conexión al cerrar la ventana
        DataProvider.shutdown();

    }

    public static void main(String[] args) {
        launch();
    }
}

