package core.apps;

import api.models.Company;
import core.controllers.ViewCompaniesController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewCompaniesApp extends Application {
    private Scene scene;
    private ViewCompaniesController controller;

    private List<Company> data;

    public ViewCompaniesApp(List<Company> data) {
        this.data = data;
    }

    public ViewCompaniesApp() {
        this.data = new ArrayList<>();
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewCompaniesApp.class.getResource("/pages/viewCompanies.fxml"));

        // Get the controller and initialize the table
        controller = new ViewCompaniesController(data);
        fxmlLoader.setController(controller);

        // Set the scene
        scene = new Scene(fxmlLoader.load(), 800, 550);
        stage.setTitle("Empresas - Prácticas Empresariales SAFA");
        stage.setScene(scene);
        stage.show();

        // Add the stylesheet to the scene
        scene.getStylesheets().add(String.valueOf(ViewCompaniesApp.class.getResource("/css/bootstrap.css")));

        // Close event
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