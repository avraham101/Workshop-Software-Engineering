package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.SystemMocks.PaymenSystemMock;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;
import org.junit.Test;

import static org.junit.Assert.*;

public class InitialStartTest extends AcceptanceTests {
    private PaymentSystem paymentSystem;
    private SupplySystem deliverySystem;
    @Test
    public void initialStartTestSuccess(){
        boolean init = bridge.initialStart(admin.getUsername(),admin.getPassword());
        assertTrue(init);
    }

    @Test
    public void initialStartTestFailConnectPayment(){
        paymentSystem = new PaymenSystemMock();

    }

}
