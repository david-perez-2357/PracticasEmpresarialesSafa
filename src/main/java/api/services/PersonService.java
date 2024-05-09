package api.services;

import api.database.Database;
import api.models.Person;
import api.models.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static api.Api_constants.db;

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

    /**
     * Convert a ResultSet into a list of Role objects
     * @param resultSet ResultSet
     * @return List<Role>
     * @throws SQLException Exception
     */
    public static List<Role> convertToRolesObjects(ResultSet resultSet) throws SQLException {
        List<Role> result = new ArrayList<>();

        // Loop through the ResultSet and create a new Role object
        while (resultSet.next()) {
            result.add(new Role(
                resultSet.getInt("id"),
                resultSet.getString("name")
            ));
        }

        return result;
    }

    /**
     * Get all people from the database
     * @return ResultSet
     * @throws SQLException Exception
     */
    public static ResultSet getAllPeople() throws SQLException {
        return db.executeQuery("""
                    SELECT p.*, r.id as role_id, r.nombre as role_name
                    FROM persona p
                    JOIN rol r ON p.rol_id = r.id
                """);
    }

    /**
     * Get all roles from the database
     * @return ResultSet
     * @throws SQLException Exception
     */
    public static ResultSet getAllRoles() throws SQLException {
        return db.executeQuery("SELECT * FROM rol");
    }
}
