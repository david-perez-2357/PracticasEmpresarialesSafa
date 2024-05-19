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
import java.util.Objects;

import static api.services.CompanyService.getAllCompanies;


public class ViewPeopleApp extends Application {
    private Scene scene;
    private ViewPeopleController controller;

    private List<Person> data;

    private String  title = "Personas";

    public ViewPeopleApp(List<Person> data, String title) {
        this.data = data;
        this.title = title;
    }

    public ViewPeopleApp() {
        this.data = new ArrayList<>();
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewPeopleController.class.getResource("/pages/viewPeople.fxml"));

        // Get the controller and initialize the table
        controller = new ViewPeopleController(data, title);
        fxmlLoader.setController(controller);

        // Set the scene
        scene = new Scene(fxmlLoader.load(), 800, 550);
        stage.setTitle("Personas - Prácticas Empresariales SAFA");
        stage.setScene(scene);
        stage.show();

        // Add the stylesheet to the scene
        scene.getStylesheets().add(String.valueOf(ViewCompaniesApp.class.getResource("/css/bootstrap.css")));

        // Close event
        stage.setOnCloseRequest(e -> {
            if (title.equals("Personas")) {
                // If the title is "Personas", we are viewing people
                IndexApp indexApp = new IndexApp();
                try {
                    indexApp.start(new Stage());
                } catch (IOException | SQLException ioException) {
                    ioException.printStackTrace();
                }
            }else {
                // If the title is not "Personas", we are viewing people from a company
                try {
                    List<Company> companies = getAllCompanies();
                    ViewCompaniesApp viewCompaniesApp = new ViewCompaniesApp(companies);
                    viewCompaniesApp.start(new Stage());
                } catch (IOException | SQLException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        // Set the window image
        stage.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/media/safa.jpg"))));
    }

    public static void main(String[] args) {
        launch();
    }
}
