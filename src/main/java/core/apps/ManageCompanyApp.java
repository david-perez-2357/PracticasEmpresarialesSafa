package core.apps;

import core.controllers.ManageCompanyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ManageCompanyApp extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManageCompanyApp.class.getResource("/pages/manageCompany.fxml"));

        // Get the controller and initialize the table
        ManageCompanyController controller = new ManageCompanyController();
        fxmlLoader.setController(controller);

        // Set the scene
        Scene scene = new Scene(fxmlLoader.load(), 800, 750);
        stage.setTitle("Gestión de empresas - Prácticas Empresariales SAFA");
        stage.setScene(scene);
        stage.show();

        // Add the stylesheet to the scene
        scene.getStylesheets().add(String.valueOf(ManageCompanyApp.class.getResource("/css/bootstrap.css")));

        // Close event
        stage.setOnCloseRequest(e -> {
            IndexApp indexApp = new IndexApp();
            try {
                indexApp.start(new Stage());
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
