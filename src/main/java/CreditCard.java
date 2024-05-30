package main.java;

public class CreditCard {
    private String creditCardNumber;
    private byte expirationMonth;
    private byte expirationYear;
    private String cvv;

    public CreditCard(String creditCardNumber, byte expirationMonth, byte expirationYear, String cvv) {
        this.creditCardNumber = creditCardNumber;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
        this.cvv = cvv;
    }
}
