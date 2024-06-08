package main.java.dto;

public record CustomerDTO(String firstName, String lastName, String email, CreditCard creditCard,
                          PaymentProcessor paymentProcessor) {
}
