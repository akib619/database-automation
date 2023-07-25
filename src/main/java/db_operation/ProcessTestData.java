package db_operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import models.TestTable;

public class ProcessTestData {

    private Connection connection;

    public ProcessTestData() {
        this.connection = DatabaseConnection.getConnection();
    }

    public TestTable get(int id) {
        String sql = "SELECT * FROM test WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    TestTable test = new TestTable();
                    test.setId(resultSet.getInt("id"));
                    test.setName(resultSet.getString("name"));
                    return test;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    

    
    public List<TestTable> getTestsByPattern(String pattern) {
    List<TestTable> tests = new ArrayList<>();
    String sql = "SELECT * FROM test WHERE id LIKE ? LIMIT 10";
    try {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + pattern + "%");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            TestTable test = new TestTable();
            test.setId(resultSet.getInt("id"));
            test.setName(resultSet.getString("name"));
            test.setBrowser(resultSet.getString("browser"));
            tests.add(test);
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }

    public void copyTests(List<TestTable> tests, String project, String author) {
        String sql = "INSERT INTO test (name, browser, project, author) VALUES (?, ?, ?, ?)";
        try {
            for (TestTable test : tests) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, test.getName());
                statement.setString(2, test.getBrowser());
                statement.setString(3, project);
                statement.setString(4, author);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateTests(List<TestTable> tests) {
        String sql = "UPDATE test SET browser = ? WHERE id = ?";
        try {
            for (TestTable test : tests) {
                test.setBrowser("chrome");             
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, test.getBrowser());
                statement.setInt(2, test.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTests(List<TestTable> tests) {
        String sql = "DELETE FROM test WHERE id = ?";
        try {
            for (TestTable test : tests) {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, test.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
