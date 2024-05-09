package api.controllers;

import api.models.Company;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

import static api.services.CompanyService.convertToCompaniesObjects;
import static api.services.CompanyService.getAllCompanies;

public class CompanyController {
    public static List<Company> getCompanies() throws SQLException {
        return convertToCompaniesObjects(getAllCompanies());
    }
}
