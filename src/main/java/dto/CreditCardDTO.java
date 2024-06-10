package main.java.dto;

public record CreditCardDTO(String creditCardNumber, byte expirationMonth, byte expirationYear, String cvv,
                            int customerId, int paymentProcessorId) {
}
