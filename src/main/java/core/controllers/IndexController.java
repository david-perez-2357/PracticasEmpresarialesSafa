package core.controllers;

import core.apps.ManageCompanyApp;
import core.apps.ViewCompaniesApp;
import core.apps.ViewPeopleApp;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import static api.services.CompanyService.getAllCompanies;
import static api.services.PersonService.getAllPeople;
import static core.utils.ImageLoader.loadImage;

public class IndexController {
    @FXML
    private ImageView personImage;

    @FXML
    private ImageView companyImage;

    @FXML
    private ImageView asignationImage;

    @FXML
    public void initialize() {
        loadImage(personImage, "/media/Study-abroad-pana.png");
        loadImage(companyImage, "/media/City-skyline-pana.png");
        loadImage(asignationImage, "/media/Back-to-back-pana.png");
    }

    @FXML
    public void seeAllCompanies() throws SQLException {
        ViewCompaniesApp viewCompaniesApp = new ViewCompaniesApp(getAllCompanies());
        try {
            viewCompaniesApp.start(new Stage());
            if (personImage != null) {
                Stage stage = (Stage) personImage.getScene().getWindow();
                stage.close();
            }
        } catch (IOException | SQLException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void seeAllPeople() throws SQLException {
        ViewPeopleApp viewPeopleApp = new ViewPeopleApp(getAllPeople());

        try {
            viewPeopleApp.start(new Stage());
            Stage stage = (Stage) personImage.getScene().getWindow();
            stage.close();
        } catch (IOException | SQLException ioException) {
            ioException.printStackTrace();
        }
    }

    @FXML
    public void addCompany() {
        ManageCompanyApp manageCompanyApp = new ManageCompanyApp();

        try {
            manageCompanyApp.start(new Stage());
            Stage stage = (Stage) personImage.getScene().getWindow();
            stage.close();
        } catch (IOException | SQLException ioException) {
            ioException.printStackTrace();
        }
    }
}
