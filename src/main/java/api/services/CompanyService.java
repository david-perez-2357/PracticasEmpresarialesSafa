package api.services;

import api.models.Company;
import api.models.Person;
import api.models.Workday;
import api.models.Modality;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompanyService {
    public static List<Company> convertToCompaniesObjects(ResultSet resultSet) throws SQLException {
        List<Company> result = new ArrayList<>();

        // Loop through the ResultSet and create a new Company object
        while (resultSet.next()) {
            Workday workday = new Workday(resultSet.getInt("id_jornada"), resultSet.getString("nombre_jornada"));
            Modality modality = new Modality(resultSet.getInt("id_modalidad"), resultSet.getString("nombre_modalidad"));

            Person workManager = new Person();
            workManager.setId(resultSet.getInt("id_responsable"));
            workManager.setName(resultSet.getString("nombre_responsable"));

            Person workTutor = new Person();
            workTutor.setId(resultSet.getInt("id_tutor"));
            workTutor.setName(resultSet.getString("nombre_tutor"));

            result.add(new Company(
                resultSet.getInt("codigo_empresa"),
                resultSet.getString("cif"),
                resultSet.getString("nombre"),
                resultSet.getString("direccion"),
                resultSet.getInt("codigo_postal"),
                resultSet.getString("localidad"),
                workday,
                modality,
                resultSet.getString("email"),
                workManager,
                workTutor
            ));
        }

        return result;
    }
}
