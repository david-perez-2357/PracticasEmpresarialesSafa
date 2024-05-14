package core.controllers;

import api.models.Company;
import api.models.Person;
import api.models.Role;
import core.utils.AlertMessage;
import core.utils.DataFileManager;
import core.utils.TableViewManager;
import core.utils.XmlFileManager;
import javafx.application.Application;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static api.services.CompanyService.getAllCompanies;
import static api.services.PersonService.getAllPeople;
import static api.services.PersonService.getAllRoles;
import static api.services.PersonService.addPeople;

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
    public void exportToDat() {
        FilteredList<Person> filteredData = tableViewManager.getFilteredData();
        List<Person> data = new ArrayList<>(filteredData);
        String fileName = "personas";

        if (data.isEmpty()) {
            AlertMessage.showError("No hay datos para exportar");
            return;
        }

        if (rolesSelector.getValue() != null && !((Role) rolesSelector.getValue()).getName().equals("Todos")) {
            fileName += "-" + ((Role) rolesSelector.getValue()).getName().toLowerCase();
        }

        if (!searchInput.getText().isEmpty()) {
            fileName += "-" + searchInput.getText().toLowerCase();
        }

        String filePath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + fileName + ".dat";

        try {
            DataFileManager.exportPeople(data, filePath);
            System.out.println(data.size() + " personas exportadas correctamente en " + filePath);
            AlertMessage.showInfo(data.size() + " personas exportadas correctamente en " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void importFromDat() {
        FileChooser fileChooser = new FileChooser();
        Window stage = peopleTable.getScene().getWindow();

        fileChooser.setTitle("Importar Personas");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo DAT", "*.dat"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                List<Person> personasImportadas = DataFileManager.importPeople(file.getPath());
                System.out.println(personasImportadas.size() + " personas importadas correctamente");
                AlertMessage.showInfo(personasImportadas.size() + " personas importadas correctamente");

                for (Person persona : personasImportadas) {
                    System.out.println(persona);
                }

                addPeople(personasImportadas);
                tableViewManager.addAllData(personasImportadas);
            } catch (IOException | ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void exportToXml() {
        FilteredList<Person> filteredData = tableViewManager.getFilteredData();
        List<Person> data = new ArrayList<>(filteredData);
        String fileName = "personas";

        if (data.isEmpty()) {
            AlertMessage.showError("No hay datos para exportar");
            return;
        }

        if (rolesSelector.getValue() != null && !((Role) rolesSelector.getValue()).getName().equals("Todos")) {
            fileName += "-" + ((Role) rolesSelector.getValue()).getName().toLowerCase();
        }

        if (!searchInput.getText().isEmpty()) {
            fileName += "-" + searchInput.getText().toLowerCase();
        }

        String filePath = System.getProperty("user.home") + File.separator + "Downloads" + File.separator + fileName + ".xml";

        try {
            XmlFileManager.exportPeople(data, filePath);
            System.out.println(data.size() + " personas exportadas correctamente en " + filePath);
            AlertMessage.showInfo(data.size() + " personas exportadas correctamente en " + filePath);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void importFromXml() {
        FileChooser fileChooser = new FileChooser();
        Window stage = peopleTable.getScene().getWindow();

        fileChooser.setTitle("Importar Personas");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivo XML", "*.xml"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                List<Person> personasImportadas = XmlFileManager.importPeople(file.getPath());
                System.out.println(personasImportadas.size() + " personas importadas correctamente");
                AlertMessage.showInfo(personasImportadas.size() + " personas importadas correctamente");

                for (Person persona : personasImportadas) {
                    System.out.println(persona);
                }

                addPeople(personasImportadas);
                tableViewManager.addAllData(personasImportadas);
            } catch (SQLException | JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void deletePersonButton() {
        System.out.println("Delete button clicked");
    }
}
