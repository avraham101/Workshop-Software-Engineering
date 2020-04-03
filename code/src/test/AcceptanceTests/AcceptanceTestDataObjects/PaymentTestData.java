package AcceptanceTests.AcceptanceTestDataObjects;

import Data.Data;

import java.util.Date;

public class PaymentTestData {
    String creditCardNumber;
    String threeDigits;
    Date expiration;

    public PaymentTestData(String creditCardNumber, String threeDigits, Date expiration) {
        this.creditCardNumber = creditCardNumber;
        this.threeDigits = threeDigits;
        this.expiration = expiration;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getThreeDigits() {
        return threeDigits;
    }

    public Date getExpiration() {
        return expiration;
    }
}
