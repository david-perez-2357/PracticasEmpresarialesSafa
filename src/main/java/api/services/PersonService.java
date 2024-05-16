package api.services;

import api.models.Company;
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
     * Get the work manager from the database
     * @param company
     * @return
     * @throws SQLException
     */
    public static Person getWorkManager(Company company) throws SQLException {
        ResultSet workManager = db.executePreparedQuery("""
            SELECT p.*, r.id as role_id, r.nombre as role_name
            FROM persona p
            JOIN rol r ON p.rol_id = r.id
            JOIN empresa e ON p.id = e.id_responsable
            WHERE e.codigo_empresa = ?
        """, company.getCompanyCode());

        return convertToPeopleObjects(workManager).get(0);
    }

    /**
     * Get the work tutor from the database
     * @param company
     * @return
     * @throws SQLException
     */
    public static Person getWorkTutor(Company company) throws SQLException {
        ResultSet workTutor = db.executePreparedQuery("""
            SELECT p.*, r.id as role_id, r.nombre as role_name
            FROM persona p
            JOIN rol r ON p.rol_id = r.id
            JOIN empresa e ON p.id = e.id_tutor_laboral
            WHERE e.codigo_empresa = ?
        """, company.getCompanyCode());

        return convertToPeopleObjects(workTutor).get(0);
    }

    /**
     * Get people by role from the database
     * @param role
     * @return
     * @throws SQLException
     */
    public static List<Person> getPeopleByRole(Role role) throws SQLException {
        ResultSet people = db.executePreparedQuery("""
            SELECT p.*, r.id as role_id, r.nombre as role_name
            FROM persona p
            JOIN rol r ON p.rol_id = r.id
            WHERE r.id = ?
        """, role.getId());

        return convertToPeopleObjects(people);
    }

    /**
     * Check if a person exists in the database
     * @param person
     * @return
     * @throws SQLException
     */
    public static Boolean personExists(Person person) throws SQLException {
        // Check if the person exists by DNI, name and surnames
        ResultSet result = db.executePreparedQuery("""
            SELECT * FROM persona WHERE dni = ? AND nombre = ? AND apellidos = ?
        """, person.getDni(), person.getName(), person.getSurnames());

        return result.next();
    }

    /**
     * Add a list of people to the database
     * @param people
     * @throws SQLException
     */
    public static Integer addPeople(List<Person> people) throws SQLException {
        db.beginTransaction();
        Integer addedPeople = 0;

        try {
            for (Person person : people) {
                if (!personExists(person)) {
                    System.out.println("Adding person: " + person.getFullName());
                    addPerson(person);
                    addedPeople++;
                }
            }

            db.commitTransaction();
        }catch (SQLException e) {
            db.rollbackTransaction();
            throw e;
        }

        return addedPeople;
    }

    /**
     * Add a person to the database
     * @param person
     * @throws SQLException
     */
    public static void addPerson(Person person) throws SQLException {
        db.executePreparedUpdate("""
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
        return db.executePreparedUpdate("DELETE FROM persona WHERE id = ?", person.getId()) == 1;
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
