package main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.java.dto.CustomerDTO;
import main.java.entity.Customer;
import main.java.utility.DatabaseConnection;
import main.java.utility.DbConstants;

public class CustomerDAO {
    public int insertCustomer(CustomerDTO customer) throws SQLException {
        String sql = "INSERT INTO customer (first_name, last_name, email) VALUES (?, ?, ?)";

        int newId;

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, customer.firstName());
        stmt.setString(2, customer.lastName());
        stmt.setString(3, customer.email());

        stmt.executeUpdate();

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                newId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Inserting customer failed, no ID obtained.");
            }
        }

        return newId;
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT id, first_name, last_name, email FROM customer";

        List<Customer> customers = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt(DbConstants.ID);
                String firstName = rs.getString(DbConstants.FIRST_NAME);
                String lastName = rs.getString(DbConstants.LAST_NAME);
                String email = rs.getString(DbConstants.EMAIL);

                customers.add(new Customer(id, firstName, lastName, email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public Customer getCustomer(int id) {
        String sql = "SELECT id, first_name, last_name, email FROM customer WHERE id = ?";

        Customer customer = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int customerId = rs.getInt(DbConstants.ID);
                    String firstName = rs.getString(DbConstants.FIRST_NAME);
                    String lastName = rs.getString(DbConstants.LAST_NAME);
                    String email = rs.getString(DbConstants.EMAIL);

                    customer = new Customer(customerId, firstName, lastName, email);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }
}
