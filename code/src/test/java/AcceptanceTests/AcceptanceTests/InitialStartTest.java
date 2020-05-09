package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.SystemMocks.DeliverySystemMockAllNegative;
import AcceptanceTests.SystemMocks.DeliverySystemMockAllPositive;
import AcceptanceTests.SystemMocks.PaymentSystemMockAllNegative;
import AcceptanceTests.SystemMocks.PaymentSystemMockAllPositive;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InitialStartTest extends AcceptanceTests {
    private PaymentSystem paymentSystem;
    private SupplySystem deliverySystem;

    @Test
    public void initialStartTestSuccess(){
        paymentSystem = new PaymentSystemMockAllPositive();
        deliverySystem = new DeliverySystemMockAllPositive();
        boolean init = bridge.initialStart(admin.getUsername(),admin.getPassword(),paymentSystem,deliverySystem);
        assertTrue(init);
    }

    @Test
    public void initialStartTestFailConnectPayment(){
        paymentSystem = new PaymentSystemMockAllNegative();
        deliverySystem = new DeliverySystemMockAllPositive();

        boolean init = bridge.initialStart(admin.getUsername(),admin.getPassword(),paymentSystem,deliverySystem);
        assertFalse(init);
    }

    @Test
    public void initialStartTestFailConnectSupply(){
        paymentSystem = new PaymentSystemMockAllPositive();
        deliverySystem = new DeliverySystemMockAllNegative();

        boolean init = bridge.initialStart(admin.getUsername(),admin.getPassword(),paymentSystem,deliverySystem);
        assertFalse(init);
    }

}
