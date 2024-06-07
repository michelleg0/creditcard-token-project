package main.java;

import java.sql.Connection;
import java.sql.SQLException;
import main.java.utility.DatabaseConnection;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Connected to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
