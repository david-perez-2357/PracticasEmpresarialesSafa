package api.services;

import api.models.Modality;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static api.ApiConstants.db;
import static api.services.ModalityService.convertToModalitiesObjects;

public class ModalityService {
    /**
     * Convert a ResultSet into a list of Modality objects
     * @param resultSet
     * @return List<Modality>
     * @throws SQLException Exception
     */
    public static List<Modality> convertToModalitiesObjects(ResultSet resultSet) throws SQLException {
        List<Modality> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(new Modality(
                resultSet.getInt("id"),
                resultSet.getString("nombre")
            ));
        }

        return result;
    }

    /**
     * Get all modalities
     * @return List<Modality>
     */
    public static List<Modality> getAllModalities() {
        List<Modality> result = new ArrayList<>();

        try {
            ResultSet resultSet = db.executeQuery("SELECT * FROM modalidad");
            result = ModalityService.convertToModalitiesObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
