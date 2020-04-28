package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.FilterTestData.PurchasePolicyTestData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class UpdatePurchasePolicyTest extends AcceptanceTests {

    private PurchasePolicyTestData purchasePolicyTestData;

    @Before
    public void setUp() {
        addUserStoresAndProducts(superUser);
        purchasePolicyTestData = new PurchasePolicyTestData(10);
    }

    @Test
    public void updatePurchasePolicySuccess() {
        assertTrue(bridge.updatePolicy(superUser.getId(),purchasePolicyTestData,"store1Test"));
    }


}
