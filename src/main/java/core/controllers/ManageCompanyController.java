package core.controllers;

import api.models.Modality;
import api.models.Person;
import api.models.Role;
import api.models.Workday;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

import static api.services.ModalityService.getAllModalities;
import static api.services.PersonService.getAllRoles;
import static api.services.PersonService.getPeopleByRole;
import static api.services.WorkdayService.getAllWorkdays;

public class ManageCompanyController {
    @FXML
    private Label title;

    @FXML
    private Button submit;

    @FXML
    private TextField name;

    @FXML
    private TextField cif;

    @FXML
    private TextField email;

    @FXML
    private ComboBox<Workday> workday;

    @FXML
    private ComboBox<Modality> modality;

    @FXML
    private TextField address;

    @FXML
    private TextField postalCode;

    @FXML
    private TextField city;

    @FXML
    private ComboBox<Person> workManager;

    @FXML
    private ComboBox<Person> workTutor;

    @FXML
    private void initialize() throws SQLException {
        List<Role> allRoles = getAllRoles();
        List<Workday> allWorkdays = getAllWorkdays();
        List<Modality> allModalities = getAllModalities();

        // Add all workdays and modalities to the ComboBoxes
        workday.getItems().addAll(allWorkdays);
        modality.getItems().addAll(allModalities);

        // Get the work manager and work tutor roles
        Role workManagerRole = allRoles.stream().filter(role -> role.getName().equals("responsable laboral")).findFirst().orElse(null);
        Role workTutorRole = allRoles.stream().filter(role -> role.getName().equals("tutor laboral")).findFirst().orElse(null);

        // Get all people with the work manager and work tutor roles
        assert workManagerRole != null;
        List<Person> managers = getPeopleByRole(workManagerRole);
        workManager.getItems().addAll(managers);

        assert workTutorRole != null;
        List<Person> tutors = getPeopleByRole(workTutorRole);
        workTutor.getItems().addAll(tutors);
    }

    public void changeTextToEdit() {
        title.setText("Editar empresa");
        submit.setText("Guardar cambios");
    }
}
