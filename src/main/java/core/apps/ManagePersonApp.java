package core.apps;

import api.models.Person;
import core.controllers.IndexController;
import core.controllers.ManageCompanyController;
import core.controllers.ManagePersonController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class ManagePersonApp extends Application {
    private final Person person;

    public ManagePersonApp() {
        this.person = null;
    }

    public ManagePersonApp(Person person) {
        this.person = person;
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManagePersonApp.class.getResource("/pages/managePerson.fxml"));

        // Get the controller and initialize the file
        ManagePersonController controller = new ManagePersonController();
        controller.setPerson(person);

        if (person != null) {
            controller.setIsEdit(true);
        }

        fxmlLoader.setController(controller);

        // Set the scene
        Scene scene = new Scene(fxmlLoader.load(), 800, 470);
        stage.setTitle("Gestión de personas - Prácticas Empresariales SAFA");
        stage.setScene(scene);
        stage.show();

        // Add the stylesheet to the scene
        scene.getStylesheets().add(String.valueOf(ManagePersonApp.class.getResource("/css/bootstrap.css")));

        // Close event
        stage.setOnCloseRequest(e -> {
            if (person != null) {
                // If the company is not null, we are editing a company
                IndexController indexController = new IndexController();
                try {
                    indexController.seeAllPeople();
                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                }
            }else {
                // If the company is null, we are creating a new company
                IndexApp indexApp = new IndexApp();
                try {
                    indexApp.start(new Stage());
                } catch (IOException | SQLException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
