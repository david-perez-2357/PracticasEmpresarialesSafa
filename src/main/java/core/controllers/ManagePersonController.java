package core.controllers;

import api.models.Person;
import api.models.Role;
import core.apps.IndexApp;
import core.utils.AlertMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static api.services.PersonService.*;

public class ManagePersonController {
    @FXML
    private Label title;

    @FXML
    private TextField name;

    @FXML
    private TextField surnames;

    @FXML
    private ComboBox<Role> role;

    @FXML
    private TextField dni;

    @FXML
    private TextField email;

    @FXML
    private TextField phone;

    @FXML
    private Button submit;

    @Setter
    private Person person;

    @Setter
    private Boolean isEdit = false;

    @FXML
    public void initialize() throws SQLException {
        setRoleComboBox();

        if (isEdit) {
            changeTextToEdit();
            name.setText(person.getName());
            surnames.setText(person.getSurnames());
            role.setValue(person.getRole());
            dni.setText(person.getDni());
            email.setText(person.getEmail());
            phone.setText(String.valueOf(person.getTelephone()));
        }
    }

    @FXML
    public void submitForm() throws SQLException, IOException {
        if (!validateForm()) {
            return;
        }

        String name = this.name.getText();
        String surnames = this.surnames.getText();
        Role role = this.role.getValue();
        String dni = this.dni.getText();
        String email = this.email.getText();
        int phone = Integer.parseInt(this.phone.getText());

        if (isEdit) {
            person.setName(name);
            person.setSurnames(surnames);
            person.setRole(role);
            person.setDni(dni);
            person.setEmail(email);
            person.setTelephone(phone);

            try {
                updatePerson(person);
                AlertMessage.showInfo("Persona actualizada correctamente");
            } catch (SQLException e) {
                AlertMessage.showError("Ha ocurrido un error al crear la persona");
            }

            // Open the people window
            IndexController indexController = new IndexController();
            indexController.seeAllPeople();

            // Close the window
            submit.getScene().getWindow().hide();

        } else {
            Person newPerson = new Person(0, dni, name, surnames, phone, email, role);

            try {
                addPerson(newPerson);
                AlertMessage.showInfo("Persona creada correctamente");
            } catch (SQLException e) {
                AlertMessage.showError("Ha ocurrido un error al crear la persona");
            }

            // Open the index window
            try {
                IndexApp indexApp = new IndexApp();
                indexApp.start(new Stage());
            } catch (IOException | SQLException ioException) {
                ioException.printStackTrace();
            }

            // Close the window
            submit.getScene().getWindow().hide();
        }
    }

    public Boolean validateForm() {
        if (name.getText().isEmpty() || surnames.getText().isEmpty() || role.getValue() == null || dni.getText().isEmpty() || email.getText().isEmpty() || phone.getText().isEmpty()) {
            AlertMessage.showError("Por favor, rellena todos los campos");
            return false;
        }

        if (dni.getText().length() != 9) {
            AlertMessage.showError("El DNI debe tener 9 caracteres");
            return false;
        }

        if (!dni.getText().matches("[0-9]{8}[A-Z]")) {
            AlertMessage.showError("El DNI no es válido");
            return false;
        }

        if (!email.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            AlertMessage.showError("El email no es válido");
            return false;
        }

        if (!phone.getText().matches("[0-9]+")) {
            AlertMessage.showError("El teléfono debe ser un número");
            return false;
        }

        return true;
    }

    public void changeTextToEdit() {
        title.setText("Editar persona");
        submit.setText("Guardar cambios");
    }

    public void setRoleComboBox() throws SQLException {
        List<Role> roles = getAllRoles();
        role.getItems().addAll(roles);
    }
}
