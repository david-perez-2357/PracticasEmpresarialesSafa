package core.apps;

import core.controllers.ViewCompaniesController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class ViewCompaniesApp extends Application {
    private Scene scene;
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewCompaniesApp.class.getResource("/pages/viewCompanies.fxml"));
        scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Empresas - Prácticas Empresariales SAFA");
        stage.setScene(scene);
        stage.show();

        // Add the stylesheet to the scene
        scene.getStylesheets().add(String.valueOf(ViewCompaniesApp.class.getResource("/css/bootstrap.css")));

        // Initialize the table with the companies
        ViewCompaniesController controller = fxmlLoader.getController();
        controller.start();
    }

    public static void main(String[] args) {
        launch();
    }
}