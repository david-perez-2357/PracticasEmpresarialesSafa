package api;

import api.database.Database;

import java.io.IOException;
import java.sql.SQLException;

public class Api_constants {
    public static final Database db;
    public static final EnvReader envReader;

    static {
        try {
            envReader = new EnvReader(String.valueOf(Api_constants.class.getResource("/.env")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            db = new Database();
        } catch (SQLException e) {
            throw new RuntimeException("Error loading database connection", e);
        }
    }
}
