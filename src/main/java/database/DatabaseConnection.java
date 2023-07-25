package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import utils.JsonDataReader;
import utils.TestData;
import utils.TestDataDB;

public class DatabaseConnection {
    private static TestDataDB testDataDB = JsonDataReader.readJson("src/main/java/resources/testData.json", TestData.class).getDataDB();
    private static final String URL = testDataDB.getUrl();
    private static final String USER = testDataDB.getUser();
    private static final String PASSWORD = testDataDB.getPassword();
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}

