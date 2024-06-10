package test.java;

import main.java.utility.CreditCardTokenizer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreditCardTokenizerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void tokenize_validCreditCardNumber_ValidToken() {
        String creditCardNumber = "1234567891011213";
        String expectedTokenizedCreditCard = "ac9a3dc52df5643ac4a8fe9ab9a7b125965245e8d23dcf4dbeec635007dec410";

        assertEquals(expectedTokenizedCreditCard, CreditCardTokenizer.tokenize(creditCardNumber));
    }
}