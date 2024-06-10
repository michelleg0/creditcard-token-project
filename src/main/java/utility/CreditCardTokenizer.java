package main.java.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreditCardTokenizer {
    public static String tokenize(String creditCardNumber) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

            byte[] encodedHash = messageDigest.digest(creditCardNumber.getBytes());

            StringBuilder hexStr = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);

                if (hex.length() == 1) {
                    hexStr.append('0');
                }

                hexStr.append(hex);
            }

            return hexStr.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
