package api.services;

import api.models.Workday;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static api.ApiConstants.db;

public class WorkdayService {
    /**
     * Convert a ResultSet into a list of Workday objects
     * @param resultSet ResultSet
     * @return List<Workday>
     * @throws SQLException Exception
     */
    public static List<Workday> convertToWorkdaysObjects(ResultSet resultSet) throws SQLException {
        List<Workday> result = new ArrayList<>();

        while (resultSet.next()) {
            result.add(new Workday(
                resultSet.getInt("id"),
                resultSet.getString("nombre")
            ));
        }

        return result;
    }

    /**
     * Get all workdays
     * @return List<Workday>
     */
    public static List<Workday> getAllWorkdays() {
        List<Workday> result = new ArrayList<>();

        try {
            ResultSet resultSet = db.executeQuery("SELECT * FROM jornada");
            result = WorkdayService.convertToWorkdaysObjects(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }
}
