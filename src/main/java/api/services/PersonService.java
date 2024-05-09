package api.services;

import api.database.Database;
import api.models.Person;
import api.models.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * PersonService
 */
public class PersonService {
    /**
     * Convert a ResultSet into a list of Person objects
     * @param resultSet ResultSet
     * @return List<Person>
     * @throws SQLException Exception
     */
    public static List<Person> convertToPeopleObjects(ResultSet resultSet) throws SQLException {
        List<Person> result = new ArrayList<>();

        // Loop through the ResultSet and create a new Person object
        while (resultSet.next()) {
            Role role = new Role(
                resultSet.getInt("role_id"),
                resultSet.getString("role_name")
            );

            result.add(new Person(
                resultSet.getInt("id"),
                resultSet.getString("dni"),
                resultSet.getString("nombre"),
                resultSet.getString("apellidos"),
                resultSet.getInt("telefono"),
                resultSet.getString("email"),
                role
            ));
        }

        return result;
    }
}
