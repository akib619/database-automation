package db_operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import models.TestTable;

public class CRUD {
    private Connection connection;

    public CRUD() {
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
                    test.setStatus_id(resultSet.getInt("status_id"));
                    test.setMethod_name(resultSet.getString("method_name"));
                    test.setProject_id(resultSet.getInt("project_id"));
                    test.setSession_id(resultSet.getInt("session_id"));
                    test.setEnv(resultSet.getString("env"));
                    return test;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    

    public void add(TestTable test) {
        String sql = "INSERT INTO test (name, status_id, method_name, project_id, session_id, start_time, end_time, env, browser) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, test.getName());
            statement.setInt(2, test.getStatus_id());
            statement.setString(3, test.getMethod_name());
            statement.setInt(4, test.getProject_id());
            statement.setInt(5, test.getSession_id());
            statement.setString(6, test.getStart_time());
            statement.setString(7, test.getEnd_time());
            statement.setString(8, test.getEnv());
            statement.setString(9, test.getBrowser());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update(TestTable test) {
        String sql = "UPDATE test SET name = ?, status_id = ?, method_name = ?, project_id = ?, session_id = ?, start_time = ?, end_time = ?, env = ?, browser = ? WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, test.getName());
            statement.setInt(2, test.getStatus_id());
            statement.setString(3, test.getMethod_name());
            statement.setInt(4, test.getProject_id());
            statement.setInt(5, test.getSession_id());
            statement.setString(6, test.getStart_time());
            statement.setString(7, test.getEnd_time());
            statement.setString(8, test.getEnv());
            statement.setString(9, test.getBrowser());
            statement.setInt(10, test.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public List<TestTable> getAll(int id) {
    List<TestTable> tests = new ArrayList<>();
    String sql = "SELECT id, name, method_name FROM test";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                TestTable test = new TestTable();
                test.setId(resultSet.getInt("id"));
                test.setName(resultSet.getString("name"));
                test.setMethod_name(resultSet.getString("method_name"));
                tests.add(test);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return tests;
}

}
