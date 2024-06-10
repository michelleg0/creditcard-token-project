package main.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import main.java.dto.CreditCardDTO;
import main.java.utility.CreditCardTokenizer;
import main.java.utility.DatabaseConnection;

public class CreditCardDAO {
    public void insertCreditCard(CreditCardDTO creditCard) {
        String sql = "INSERT INTO credit_card (credit_card_token, last_four_cc_digits, expiration_month, expiration_year, customer_id, payment_processor_id) VALUES (?, ?, ?, ?, ?, ?)";

        String token = CreditCardTokenizer.tokenize(creditCard.creditCardNumber());
        String lastFourDigits = creditCard.creditCardNumber().substring(creditCard.creditCardNumber().length() - 4);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            stmt.setString(2, lastFourDigits);
            stmt.setByte(3, creditCard.expirationMonth());
            stmt.setByte(4, creditCard.expirationYear());
            stmt.setInt(5, creditCard.customerId());
            stmt.setInt(6, creditCard.paymentProcessorId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
