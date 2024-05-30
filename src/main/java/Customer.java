package main.java;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private CreditCard creditCard;
    private PaymentProcessor paymentProcessor;

    public Customer(String firstName, String lastName, String email, CreditCard creditCard, PaymentProcessor paymentProcessor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creditCard = creditCard;
        this.paymentProcessor = paymentProcessor;
    }
}
