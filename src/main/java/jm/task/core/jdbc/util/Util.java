package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    private static final Logger logger = Logger.getLogger(Util.class.getCanonicalName());
    private static final String URL = "jdbc:postgresql://localhost:5432/burritos_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "17_Zogita";

    public static Connection getConnection() {

        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Не удалось установить соединение с БД: " + e);
            return null;
        }
    }
}
