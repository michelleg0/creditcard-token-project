package main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.java.dto.PaymentProcessorDTO;
import main.java.entity.PaymentProcessor;
import main.java.utility.DatabaseConnection;
import main.java.utility.DbConstants;

public class PaymetProcessorDAO {
    public int insertPaymentProcessor(PaymentProcessorDTO paymentProcessor) {
        String sql = "INSERT INTO payment_processor (name) VALUES (?)";

        int newId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paymentProcessor.name());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    newId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Inserting payment processor failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newId;
    }

    public List<PaymentProcessor> getAllPaymentProcessors() {
        String sql = "SELECT id, name FROM payment_processor";

        List<PaymentProcessor> paymentProcessors = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt(DbConstants.ID);
                String name = rs.getString(DbConstants.NAME);

                paymentProcessors.add(new PaymentProcessor(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentProcessors;
    }

    public PaymentProcessor getPaymentProcessor(int id) {
        String sql = "SELECT id, name FROM payment_processor WHERE id = ?";

        PaymentProcessor paymentProcessor = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int paymentProcessorId = rs.getInt(DbConstants.ID);
                    String name = rs.getString(DbConstants.NAME);

                    paymentProcessor = new PaymentProcessor(paymentProcessorId, name);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paymentProcessor;
    }
}
