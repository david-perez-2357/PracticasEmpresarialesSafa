package core.apps;

import core.controllers.IndexController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static javafx.application.Application.launch;

public class IndexApp extends Application {
    private Scene scene;
    private IndexController controller;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(IndexApp.class.getResource("/pages/index.fxml"));
        scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Prácticas Empresariales SAFA");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // Add the stylesheet to the scene
        scene.getStylesheets().add(String.valueOf(IndexApp.class.getResource("/css/bootstrap.css")));

        // Get the controller and initialize the table
        controller = fxmlLoader.getController();
    }

    public static void main(String[] args) {
        launch();
    }
}
