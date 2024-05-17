package core.controllers;

import api.models.Company;
import api.models.Person;
import core.apps.ManageCompanyApp;
import core.apps.ViewPeopleApp;
import core.utils.TableViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static api.services.CompanyService.deleteCompany;
import static api.services.PersonService.*;
import static core.utils.AlertMessage.*;

public class ViewCompaniesController {
    @FXML
    private TableView companiesTable;

    @FXML
    private TextField searchInput;

    @FXML
    private Button edit;

    @FXML
    private Button delete;

    @FXML
    private Button student;

    @FXML
    private Button people;

    @FXML
    private Button employee;

    private TableViewManager<Company> tableViewManager;

    private List<Company> data;

    public ViewCompaniesController(List<Company> data) {
        this.data = data;
    }

    @FXML
    public void initialize() throws SQLException {
        tableViewManager = new TableViewManager<>(companiesTable);

        // Initialize the table with the companies
        addCompaniesToTable();

        // Disable the buttons
        disableButtons();

        // Add a listener to the table to enable the buttons when a row is selected
        companiesTable.setOnMouseClicked(event -> {
            if (!companiesTable.getSelectionModel().isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                enableButtons();
            }
        });
    }

    public void addCompaniesToTable() throws SQLException {
        List<Company> companies = data;

        tableViewManager.addColumn("Código", Company::getCompanyCode);
        tableViewManager.addColumn("CIF", Company::getCif);
        tableViewManager.addColumn("Nombre", Company::getName);
        tableViewManager.addColumn("Dirección", Company::getAddress);
        tableViewManager.addColumn("Código Postal", Company::getPostalCode);
        tableViewManager.addColumn("Localidad", Company::getLocation);
        tableViewManager.addColumn("Jornada", company -> company.getWorkday().getName());
        tableViewManager.addColumn("Modalidad", company -> company.getModality().getName());
        tableViewManager.addColumn("Email", Company::getEmail);

        tableViewManager.addAllData(companies);
        tableViewManager.refresh();
    }

    public void disableButtons() {
        edit.setDisable(true);
        delete.setDisable(true);
        student.setDisable(true);
        people.setDisable(true);
        employee.setDisable(true);
    }

    public void enableButtons() {
        edit.setDisable(false);
        delete.setDisable(false);
        student.setDisable(false);
        people.setDisable(false);
        employee.setDisable(false);
    }

    @FXML
    public void searchInputChanged() {
        // Filter the table by the search input
        String searchText = searchInput.getText();
        tableViewManager.filter(company -> company.getName().toLowerCase().contains(searchText.toLowerCase()));
    }

    @FXML
    public void editCompanyButton() throws SQLException, IOException {
        // Get the selected company
        Company selectedCompany = (Company) companiesTable.getSelectionModel().getSelectedItem();
        Person workManager = getWorkManager(selectedCompany);
        Person workTutor = getWorkTutor(selectedCompany);

        // Open the manage company view
        ManageCompanyApp manageCompanyController = new ManageCompanyApp(selectedCompany, workManager, workTutor);
        manageCompanyController.start(new javafx.stage.Stage());

        // Close the current view
        companiesTable.getScene().getWindow().hide();
    }

    @FXML
    public void deleteCompanyButton() throws SQLException {
        // Get the selected company
        Company selectedCompany = (Company) companiesTable.getSelectionModel().getSelectedItem();

        // Show confirmation alert
        Optional<ButtonType> response = showConfirmation("¿Estás seguro de que quieres eliminar la empresa " + selectedCompany.getName() + "?");

        // If the user confirms the action
        if (response.isPresent() && response.get() != ButtonType.OK) {
            return;
        }

        // Remove the company from the database
         if (deleteCompany(selectedCompany)) {
             // Remove the company from the table
             tableViewManager.removeData(selectedCompany);

            // Disable the buttons
            disableButtons();

             // Show success message
             showInfo("La empresa " + selectedCompany.getName() + " ha sido eliminada correctamente");
         }else {
             // Show error message
             showError("Ha ocurrido un error al eliminar la empresa " + selectedCompany.getName());
         }
    }

    @FXML
    public void viewAllCompanyPeople() throws SQLException, IOException {
        // Get the selected company
        Company selectedCompany = (Company) companiesTable.getSelectionModel().getSelectedItem();

        // Get the people from the company
        List<Person> people = getPeopleByCompany(selectedCompany);

        // Open the view people view
        ViewPeopleApp viewPeopleApp = new ViewPeopleApp(people, "Personas de la empresa " + selectedCompany.getName());
        viewPeopleApp.start(new javafx.stage.Stage());

        // Close the current view
        companiesTable.getScene().getWindow().hide();
    }

    @FXML
    public void viewAllCompanyStudents() throws SQLException, IOException {
        // Get the selected company
        Company selectedCompany = (Company) companiesTable.getSelectionModel().getSelectedItem();

        // Get the students from the company
        List<Person> students = getStudentsByCompany(selectedCompany);

        // Open the view people view
        ViewPeopleApp viewPeopleApp = new ViewPeopleApp(students, "Alumnos de la empresa " + selectedCompany.getName());
        viewPeopleApp.start(new javafx.stage.Stage());

        // Close the current view
        companiesTable.getScene().getWindow().hide();
    }

    @FXML
    public void viewCompamyEmployees() throws SQLException, IOException {
        // Get the selected company
        Company selectedCompany = (Company) companiesTable.getSelectionModel().getSelectedItem();

        // Get the employees from the company
        List<Person> employees = List.of(getWorkManager(selectedCompany), getWorkTutor(selectedCompany));

        // Open the view people view
        ViewPeopleApp viewPeopleApp = new ViewPeopleApp(employees, "Empleados de la empresa " + selectedCompany.getName());
        viewPeopleApp.start(new javafx.stage.Stage());

        // Close the current view
        companiesTable.getScene().getWindow().hide();
    }
}
