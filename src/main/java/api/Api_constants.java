package api;

import api.database.Database;

import java.sql.SQLException;

public class Api_constants {
    public static final Database db;

    static {
        try {
            db = new Database();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
