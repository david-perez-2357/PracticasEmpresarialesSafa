package core.controllers;

import api.models.Company;
import core.utils.TableViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.sql.SQLException;
import java.util.List;

import static api.controllers.CompanyController.getCompanies;

public class ViewCompaniesController {
    @FXML
    private TableView companiesTable;

    public void start() throws SQLException {
        // Initialize the table with the companies
        addCompaniesToTable(companiesTable);
    }

    public void addCompaniesToTable(TableView companiesTable) throws SQLException {
        List<Company> companies = getCompanies();
        TableViewManager<Company> tableViewManager = new TableViewManager<>(companiesTable);

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
    }
}
