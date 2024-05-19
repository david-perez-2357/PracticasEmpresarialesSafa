package core.apps;

import api.models.Company;
import api.models.Person;
import core.controllers.IndexController;
import core.controllers.ManageCompanyController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class ManageCompanyApp extends Application {
    private final Company company;
    private final Person workManager;
    private final Person workTutor;

    public ManageCompanyApp() {
        this.company = null;
        this.workManager = null;
        this.workTutor = null;
    }

    public ManageCompanyApp(Company company, Person workManager, Person workTutor) {
        this.company = company;
        this.workManager = workManager;
        this.workTutor = workTutor;
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(ManageCompanyApp.class.getResource("/pages/manageCompany.fxml"));

        // Get the controller and initialize the file
        ManageCompanyController controller = new ManageCompanyController();
        controller.setCompany(company);
        controller.setCompanyWorkManager(workManager);
        controller.setCompanyWorkTutor(workTutor);

        if (company != null) {
            controller.setIsEdit(true);
        }

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
            if (company != null) {
                // If the company is not null, we are editing a company
                IndexController indexController = new IndexController();
                try {
                    indexController.seeAllCompanies();
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

        // Set the window image
        stage.getIcons().add(new javafx.scene.image.Image(Objects.requireNonNull(IndexApp.class.getResourceAsStream("/media/safa.jpg"))));
    }

    public static void main(String[] args) {
        launch();
    }
}
