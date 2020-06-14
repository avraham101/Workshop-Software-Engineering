import Systems.PaymentSystem.ExternalPayment;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ExternalPayamentTest {

    @Test
    public void handshakeTest() {
        ExternalPayment externalPayment = new ExternalPayment();
        assertTrue(externalPayment.connect());
    }

    @Test
    public void payTest() {

    }

    @Test
    public void cancleTest() {

    }

}
