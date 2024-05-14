package api.services;

import api.models.Company;
import api.models.Workday;
import api.models.Modality;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static api.ApiConstants.db;

/**
 * CompanyService
 */
public class CompanyService {
    /**
     * Convert a ResultSet into a list of Company objects
     * @param resultSet ResultSet
     * @return List<Company>
     * @throws SQLException Exception
     */
    public static List<Company> convertToCompaniesObjects(ResultSet resultSet) throws SQLException {
        List<Company> result = new ArrayList<>();

        // Loop through the ResultSet and create a new Company object
        while (resultSet.next()) {
            Workday workday = new Workday(resultSet.getInt("jornada_id"), resultSet.getString("jornada_nombre"));
            Modality modality = new Modality(resultSet.getInt("modalidad_id"), resultSet.getString("modalidad_nombre"));

            result.add(new Company(
                resultSet.getInt("codigo_empresa"),
                resultSet.getString("cif"),
                resultSet.getString("nombre"),
                resultSet.getString("direccion"),
                resultSet.getInt("codigo_postal"),
                resultSet.getString("localidad"),
                workday,
                modality,
                resultSet.getString("email")
            ));
        }

        return result;
    }

    /**
     * Get all companies
     * @return ResultSet
     * @throws SQLException Exception
     */
    public static List<Company> getAllCompanies() throws SQLException {
        ResultSet companies = db.executeQuery("""
                    SELECT e.*, j.id AS jornada_id, j.nombre AS jornada_nombre, m.id AS modalidad_id, m.nombre AS modalidad_nombre
                    FROM empresa e
                    JOIN jornada j ON e.jornada_id = j.id
                    JOIN modalidad m ON e.modalidad_id = m.id
                """);

        return convertToCompaniesObjects(companies);
    }

    public static Boolean deleteCompany(Company company) throws SQLException {
        return db.executeUpdate("DELETE FROM empresa WHERE codigo_empresa = " + company.getCompanyCode()) == 1;
    }
}
