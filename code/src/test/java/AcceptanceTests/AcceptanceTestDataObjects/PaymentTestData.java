package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.Date;

public class PaymentTestData {
    String creditCardOwner;
    String creditCardNumber;
    String threeDigits;
    Date expiration;

    public PaymentTestData(String creditCardOwner,String creditCardNumber, String threeDigits, Date expiration) {
        this.creditCardOwner=creditCardOwner;
        this.creditCardNumber = creditCardNumber;
        this.threeDigits = threeDigits;
        this.expiration = expiration;
    }

    public String getCreditCardOwner() {
        return creditCardOwner;
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
