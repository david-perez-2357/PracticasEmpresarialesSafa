package api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static api.ApiConstants.envReader;

/**
 * Database class
 */
public class Database {
    protected Connection connection;
    protected final String url;
    protected final String username = envReader.getValue("DB_USERNAME");
    protected final String dataBase = envReader.getValue("DB_DATABASE");
    protected final String password = envReader.getValue("DB_PASSWORD");
    protected final String language = envReader.getValue("DB_LANGUAGE");

    /**
     * Constructor
     */
    public Database() throws SQLException {
        this.url = "jdbc:" + language + "://localhost:3306/" + dataBase;

        this.connect();
    }

    /**
     * Connect to the database
     * @throws SQLException If there is an error connecting to the database
     */
    public void connect() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException e) {
            throw new SQLException("Error loading JDBC driver");
        }
    }

    /**
     * Disconnect from the database
     * @throws SQLException If there is an error disconnecting from the database
     */
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Execute a query that returns a ResultSet
     * @param query The SQL query to execute
     * @return ResultSet The ResultSet object
     * @throws SQLException If there is an error executing the query
     */
    public ResultSet executeQuery(String query) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeQuery();
        }catch (SQLException e) {
            throw new SQLException("Error executing query");
        }

    }

    /**
     * Execute an update, insert or delete query
     * @param query The SQL query to execute
     * @return rows affected by the query
     * @throws SQLException If there is an error executing the query
     */
    public int executeUpdate(String query) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            return statement.executeUpdate();
        }catch (SQLException e) {
            throw new SQLException("Error executing query");
        }
    }

    /**
     * Begin a transaction in the database
     * @throws SQLException If there is an error
     */
    public void beginTransaction() throws SQLException {
        try {
            connection.setAutoCommit(false); // Disable autocommit
        } catch (SQLException e) {
            throw new SQLException("Error starting transaction");
        }
    }

    /**
     * Commit the transaction in the database
     * @throws SQLException If there is an error committing the transaction
     */
    public void commitTransaction() throws SQLException {
        try {
            connection.commit(); // Commit the transaction
            connection.setAutoCommit(true); // Restore autocommit mode
        } catch (SQLException e) {
            throw new SQLException("Error committing transaction");
        }
    }

    /**
     * Rollback the transaction in the database
     * @throws SQLException If there is an error rolling back the transaction
     */
    public void rollbackTransaction() throws SQLException {
        try {
            connection.rollback(); // Rollback the transaction
            connection.setAutoCommit(true); // Restore autocommit mode
        } catch (SQLException e) {
            throw new SQLException("Error rolling back transaction");
        }
    }

    /**
     * Execute a prepared statement
     * @param query The SQL query to execute
     * @param values The values to bind to the query
     * @return The number of rows affected by the query
     * @throws SQLException If there is an error executing the prepared statement
     */
    public int executePreparedUpdate(String query, Object... values) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            // Bind the values to the query
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }

            // Execute the query
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error executing prepared statement");
        }
    }


    /**
     * Execute a prepared statement
     * @param query The SQL query to execute
     * @param values The values to bind to the query
     * @return The number of rows affected by the query
     * @throws SQLException If there is an error executing the prepared statement
     */
    public ResultSet executePreparedQuery(String query, Object... values) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(query);

            // Bind the values to the query
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }

            // Execute the query
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new SQLException("Error executing prepared statement");
        }
    }

}
