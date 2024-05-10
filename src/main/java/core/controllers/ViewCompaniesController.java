package core.controllers;

import api.models.Company;
import core.utils.TableViewManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

import static api.services.CompanyService.getAllCompanies;

public class ViewCompaniesController {
    @FXML
    private TableView companiesTable;

    @FXML
    private TextField searchInput;

    private TableViewManager<Company> tableViewManager;

    @FXML
    public void initialize() throws SQLException {
        tableViewManager = new TableViewManager<>(companiesTable);

        // Initialize the table with the companies
        addCompaniesToTable();
    }

    public void addCompaniesToTable() throws SQLException {
        List<Company> companies = getAllCompanies();

        tableViewManager.addColumn("Código", Company::getCompanyCode);
        tableViewManager.addColumn("CIF", Company::getCif);
        tableViewManager.addColumn("Nombre", Company::getName);
        tableViewManager.addColumn("Dirección", Company::getDirection);
        tableViewManager.addColumn("Código Postal", Company::getPostalCode);
        tableViewManager.addColumn("Localidad", Company::getLocation);
        tableViewManager.addColumn("Jornada", company -> company.getWorkday().getName());
        tableViewManager.addColumn("Modalidad", company -> company.getModality().getName());
        tableViewManager.addColumn("Email", Company::getEmail);

        tableViewManager.addAllData(companies);
        tableViewManager.refresh();
    }

    @FXML
    public void searchInputChanged() {
        // Filter the table by the search input
        String searchText = searchInput.getText();
        tableViewManager.filter(company -> company.getName().toLowerCase().contains(searchText.toLowerCase()));
    }
}
