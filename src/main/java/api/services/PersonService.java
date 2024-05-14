package api.services;

import api.models.Person;
import api.models.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static api.ApiConstants.db;

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
                resultSet.getString("nombre")
            ));
        }

        return result;
    }

    /**
     * Get all people from the database
     * @return ResultSet
     * @throws SQLException Exception
     */
    public static List<Person> getAllPeople() throws SQLException {
        ResultSet people = db.executeQuery("""
                    SELECT p.*, r.id as role_id, r.nombre as role_name
                    FROM persona p
                    JOIN rol r ON p.rol_id = r.id
                """);

        return convertToPeopleObjects(people);
    }

    /**
     * Add a list of people to the database
     * @param people
     * @throws SQLException
     */
    public static void addPeople(List<Person> people) throws SQLException {
        db.beginTransaction();

        try {
            for (Person person : people) {
                addPerson(person);
            }

            db.commitTransaction();
        }catch (SQLException e) {
            db.rollbackTransaction();
            throw e;
        }
    }

    /**
     * Add a person to the database
     * @param person
     * @throws SQLException
     */
    public static void addPerson(Person person) throws SQLException {
        db.executePreparedStatement("""
            INSERT INTO persona (dni, nombre, apellidos, telefono, email, rol_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """, person.getDni(), person.getName(), person.getSurnames(), person.getTelephone(), person.getEmail(), person.getRole().getId());
    }

    /**
     * Delete a person in the database
     * @param person
     * @throws SQLException
     */
    public static Boolean deletePerson(Person person) throws SQLException {
        return db.executePreparedStatement("DELETE FROM persona WHERE id = ?", person.getId()) == 1;
    }

    /**
     * Get all roles from the database
     * @return ResultSet
     * @throws SQLException Exception
     */
    public static List<Role> getAllRoles() throws SQLException {
        ResultSet roles = db.executeQuery("SELECT * FROM rol");

        return convertToRolesObjects(roles);
    }
}
