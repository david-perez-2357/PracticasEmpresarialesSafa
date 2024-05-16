package core.controllers;

import api.models.*;
import core.utils.AlertMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Setter;

import java.sql.SQLException;
import java.util.List;

import static api.services.CompanyService.addCompany;
import static api.services.CompanyService.updateCompany;
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

    @Setter
    private Boolean isEdit = false;

    @Setter
    private Company company;

    @Setter
    private Person companyWorkManager;

    @Setter
    private Person companyWorkTutor;

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

        if (isEdit) {
            changeTextToEdit();
            name.setText(company.getName());
            cif.setText(company.getCif());
            email.setText(company.getEmail());
            workday.setValue(company.getWorkday());
            modality.setValue(company.getModality());
            address.setText(company.getAddress());
            postalCode.setText(String.valueOf(company.getPostalCode()));
            city.setText(company.getLocation());
            workManager.setValue(companyWorkManager);
            workTutor.setValue(companyWorkTutor);
        }
    }

    @FXML
    public void submitForm() throws SQLException {
        // Validate the form
        if (!validateForm()) {
            return;
        }

        // Get the values from the form
        String name = this.name.getText();
        String cif = this.cif.getText();
        String email = this.email.getText();
        Workday workday = this.workday.getValue();
        Modality modality = this.modality.getValue();
        String address = this.address.getText();
        int postalCode = Integer.parseInt(this.postalCode.getText());
        String city = this.city.getText();
        Person workManager = this.workManager.getValue();
        Person workTutor = this.workTutor.getValue();

        // Create the company object
        if (isEdit) {
            company.setName(name);
            company.setCif(cif);
            company.setEmail(email);
            company.setWorkday(workday);
            company.setModality(modality);
            company.setAddress(address);
            company.setPostalCode(postalCode);
            company.setLocation(city);
        } else {
            company = new Company(0, cif, name, address, postalCode, city, workday, modality, email);
        }

        companyWorkManager = workManager;
        companyWorkTutor = workTutor;

        if (isEdit && updateCompany(company, workManager, workTutor)) {
            // Update the company
            // updateCompany(company, workManager, workTutor);
            AlertMessage.showInfo("La empresa se ha actualizado correctamente");
        } else if (!isEdit && addCompany(company, workManager, workTutor)) {
            AlertMessage.showInfo("La empresa se ha añadido correctamente");
        }else {
            AlertMessage.showError("Ha ocurrido un error");
        }

        // Close the window
        submit.getScene().getWindow().hide();
    }

    public Boolean validateForm() {
        // Validate the form
        if (name.getText().isEmpty() || cif.getText().isEmpty() || email.getText().isEmpty() || workday.getValue() == null || modality.getValue() == null || address.getText().isEmpty() || postalCode.getText().isEmpty() || city.getText().isEmpty() || workManager.getValue() == null || workTutor.getValue() == null) {
            AlertMessage.showError("Por favor, rellena todos los campos");
            return false;
        }

        if (postalCode.getText().length() != 5) {
            AlertMessage.showError("El código postal debe tener 5 dígitos");
            return false;
        }

        if (!postalCode.getText().matches("[0-9]+")) {
            AlertMessage.showError("El código postal debe ser un número");
            return false;
        }

        if (!email.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            AlertMessage.showError("El email no es válido");
            return false;
        }

        if (cif.getText().length() != 9) {
            AlertMessage.showError("El CIF debe tener 9 caracteres");
            return false;
        }

        return true;
    }

    public void changeTextToEdit() {
        title.setText("Editar empresa");
        submit.setText("Guardar cambios");
    }
}
