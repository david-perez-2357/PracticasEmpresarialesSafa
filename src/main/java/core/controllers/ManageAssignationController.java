package core.controllers;

import api.models.Company;
import api.models.Person;
import api.models.Role;
import core.apps.IndexApp;
import core.utils.AlertMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static api.services.CompanyService.getAllCompanies;
import static api.services.PersonService.*;

public class ManageAssignationController {
    @FXML
    private ComboBox<Person> student;

    @FXML
    private ComboBox<Person> tutor;

    @FXML
    private ComboBox<Company> company;

    @FXML
    private Button submit;

    @FXML
    public void initialize() throws SQLException {
        setStudentsComboBox();
        setTutorsComboBox();
        setCompaniesComboBox();
    }

    @FXML
    public void submitForm() throws SQLException, IOException {
        Person student = this.student.getValue();
        Person tutor = this.tutor.getValue();
        Company company = this.company.getValue();

        if (!validateForm()) {
            AlertMessage.showError("Por favor, rellene todos los campos.");
            return;
        }

        Person workTutor = getWorkTutor(company);

        if(assignStudentToCompany(student, tutor, company)) {
            AlertMessage.showInfo("El alumno " + student.getFullName() + " queda asignado a la empresa " + company.getName() +
                    " supervisado por el tutor docente " + tutor.getFullName() + " y por el tutor de laboral " + workTutor.getFullName() + ".");

            // Open Index
            IndexApp indexApp = new IndexApp();
            indexApp.start(new Stage());

            // Close the window
            submit.getScene().getWindow().hide();
        } else {
            AlertMessage.showError("No se ha podido realizar la asignación, compruebe que el alumno no esté ya asignado a alguna empresa.");
        }
    }

    public Boolean validateForm() {
        return student.getValue() != null && tutor.getValue() != null && company.getValue() != null;
    }

    public void setStudentsComboBox() throws SQLException {
        List<Person> students = getNonAssignedStudents();
        student.getItems().addAll(students);
    }

    public void setTutorsComboBox() throws SQLException {
        List<Person> tutors = getPeopleByRole(new Role(2, "tutor escolar"));
        tutor.getItems().addAll(tutors);
    }

    public void setCompaniesComboBox() throws SQLException {
        List<Company> companies = getAllCompanies();
        company.getItems().addAll(companies);
    }
}
