package core.apps;

import core.controllers.ManageAssignationController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class ManageAssignationApp extends Application {
    private Scene scene;
    private ManageAssignationController controller;

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManageAssignationApp.class.getResource("/pages/manageAssignation.fxml"));

        // Get the controller and initialize the file
        controller = new ManageAssignationController();
        fxmlLoader.setController(controller);

        // Set the scene
        scene = new Scene(fxmlLoader.load(), 550, 450);
        stage.setTitle("Asignación - Prácticas Empresariales SAFA");
        stage.setScene(scene);
        stage.show();

        // Add the stylesheet to the scene
        scene.getStylesheets().add(String.valueOf(ManageAssignationApp.class.getResource("/css/bootstrap.css")));

        stage.setOnCloseRequest(e -> {
            IndexApp indexApp = new IndexApp();
            try {
                indexApp.start(new Stage());
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });

        // Set the window image
        stage.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/media/safa.jpg"))));
    }

    public static void main(String[] args) {
        launch();
    }
}

