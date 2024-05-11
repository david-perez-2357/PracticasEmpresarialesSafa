package core.apps;

import api.models.Company;
import api.models.Person;
import core.controllers.ViewPeopleController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ViewPeopleApp extends Application {
    private Scene scene;
    private ViewPeopleController controller;

    private List<Person> data;

    public ViewPeopleApp(List<Person> data) {
        this.data = data;
    }

    public ViewPeopleApp() {
        this.data = new ArrayList<>();
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewPeopleController.class.getResource("/pages/viewPeople.fxml"));

        // Get the controller and initialize the table
        controller = new ViewPeopleController(data);
        fxmlLoader.setController(controller);

        // Set the scene
        scene = new Scene(fxmlLoader.load(), 800, 500);
        stage.setTitle("Personas - Prácticas Empresariales SAFA");
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
    }

    public static void main(String[] args) {
        launch();
    }
}
