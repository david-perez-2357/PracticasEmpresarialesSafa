package core.controllers;

import api.models.Company;
import core.utils.TableViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static api.services.CompanyService.deleteCompany;
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
    private Button tutor;

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
        tutor.setDisable(true);
        employee.setDisable(true);
    }

    public void enableButtons() {
        edit.setDisable(false);
        delete.setDisable(false);
        student.setDisable(false);
        tutor.setDisable(false);
        employee.setDisable(false);
    }

    @FXML
    public void searchInputChanged() {
        // Filter the table by the search input
        String searchText = searchInput.getText();
        tableViewManager.filter(company -> company.getName().toLowerCase().contains(searchText.toLowerCase()));
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
}
