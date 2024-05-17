package core.controllers;

import api.models.Person;
import api.models.Role;
import core.utils.AlertMessage;
import core.utils.DataFileManager;
import core.utils.TableViewManager;
import core.utils.XmlFileManager;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static api.services.PersonService.*;
import static core.utils.AlertMessage.*;

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

    @FXML
    private Button edit;

    private TableViewManager<Person> tableViewManager;

    private List<Person> data;

    private String titleText = "Personas";

    public ViewPeopleController(List<Person> data, String title) {
        this.data = data;
        this.titleText = title;
    }

    @FXML
    public void initialize() throws SQLException {
        tableViewManager = new TableViewManager<>(peopleTable);

        // Initialize the fields
        addPeopleToTable();
        addDataToComboBox();

        // Disable the buttons
        disableButtons();

        // Set the title
        title.setText(titleText);

        // Add a listener to the table to enable the buttons when a row is selected
        peopleTable.setOnMouseClicked(event -> {
            if (!peopleTable.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                enableButtons();
            }
        });
    }

    private void addDataToComboBox() throws SQLException {
        List<Role> roles = getAllRoles();
        rolesSelector.getItems().add(new Role(0, "Todos"));
        rolesSelector.getItems().addAll(roles);
    }

    private void addPeopleToTable() throws SQLException {
        List<Person> people = data;

        tableViewManager.addColumn("DNI", Person::getDni);
        tableViewManager.addColumn("Nombre", Person::getFullName);
        tableViewManager.addColumn("Teléfono", Person::getTelephone);
        tableViewManager.addColumn("Email", Person::getEmail);
        tableViewManager.addColumn("Rol", person -> person.getRole().getName());

        tableViewManager.addAllData(people);
        tableViewManager.refresh();
    }

    private void disableButtons() {
        delete.setDisable(true);
        edit.setDisable(true);
    }

    private void enableButtons() {
        delete.setDisable(false);
        edit.setDisable(false);
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
            AlertMessage.showError("Ha ocurrido un error al exportar las personas");
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
                Integer numPersonasImportadas = addPeople(personasImportadas);
                System.out.println(numPersonasImportadas + " personas importadas correctamente");
                AlertMessage.showInfo(numPersonasImportadas + " personas importadas correctamente");

                tableViewManager.addAllData(personasImportadas);
            } catch (IOException | ClassNotFoundException | SQLException e) {
                AlertMessage.showError("Ha ocurrido un error al importar las personas");
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
            AlertMessage.showError("Ha ocurrido un error al exportar las personas");
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
                Integer numPersonasImportadas = addPeople(personasImportadas);
                System.out.println(numPersonasImportadas + " personas importadas correctamente");
                AlertMessage.showInfo(numPersonasImportadas + " personas importadas correctamente");

                tableViewManager.addAllData(personasImportadas);
            } catch (SQLException | JAXBException e) {
                AlertMessage.showError("Ha ocurrido un error al importar las personas");
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void deletePersonButton() throws SQLException {
        // Get the selected person
        Person selectedPerson = (Person) peopleTable.getSelectionModel().getSelectedItem();

        // Show confirmation alert
        Optional<ButtonType> response = showConfirmation("¿Estás seguro de que quieres eliminar la persona " + selectedPerson.getFullName() + "?");

        // If the user confirms the action
        if (response.isPresent() && response.get() != ButtonType.OK) {
            return;
        }

        // Remove the person from the database
        if (deletePerson(selectedPerson)) {
            // Remove the company from the table
            tableViewManager.removeData(selectedPerson);

            // Disable the buttons
            disableButtons();

            // Show success message
            showInfo("La persona " + selectedPerson.getFullName() + " ha sido eliminada correctamente");
        }else {
            // Show error message
            showError("Ha ocurrido un error al eliminar la persona " + selectedPerson.getFullName());
        }
    }

    @FXML
    public void editPersonButton() {
        System.out.println("Edit button clicked");
    }
}
