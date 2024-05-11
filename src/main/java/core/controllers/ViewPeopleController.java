package core.controllers;

import api.models.Company;
import api.models.Person;
import api.models.Role;
import core.utils.TableViewManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.nio.Buffer;
import java.sql.SQLException;
import java.util.List;

import static api.services.CompanyService.getAllCompanies;
import static api.services.PersonService.getAllPeople;
import static api.services.PersonService.getAllRoles;

public class ViewPeopleController {
    @FXML
    private Label title;

    @FXML
    private TextField searchInput;

    @FXML
    private ComboBox rolesSelector;

    @FXML
    private TableView peopleTable;

    @FXML
    private Button delete;

    private TableViewManager<Person> tableViewManager;

    private List<Person> data;

    public ViewPeopleController(List<Person> data) {
        this.data = data;
    }

    @FXML
    public void initialize() throws SQLException {
        addPeopleToTable();
        addDataToComboBox();
    }

    private void addDataToComboBox() throws SQLException {
        List<Role> roles = getAllRoles();
        rolesSelector.getItems().add(new Role(0, "Todos"));
        rolesSelector.getItems().addAll(roles);
    }

    private void addPeopleToTable() throws SQLException {
        List<Person> people = getAllPeople();

        tableViewManager = new TableViewManager<>(peopleTable);
        tableViewManager.addColumn("DNI", Person::getDni);
        tableViewManager.addColumn("Nombre", Person::getFullName);
        tableViewManager.addColumn("Teléfono", Person::getTelephone);
        tableViewManager.addColumn("Email", Person::getEmail);
        tableViewManager.addColumn("Rol", person -> person.getRole().getName());

        tableViewManager.addAllData(people);
        tableViewManager.refresh();
    }

    @FXML
    public void filterChanged() {
        Role role = (Role) rolesSelector.getValue();
        String search = searchInput.getText();

        tableViewManager.filter(person -> {
            if (role != null && !role.getName().equals("Todos")) {
                return person.getRole().getId() == role.getId() && (search == null || person.getFullName().toLowerCase().contains(search.toLowerCase()));
            }

            return search == null || person.getFullName().toLowerCase().contains(search.toLowerCase());
        });
    }

    @FXML
    public void deletePersonButton() {
        System.out.println("Delete button clicked");
    }
}
