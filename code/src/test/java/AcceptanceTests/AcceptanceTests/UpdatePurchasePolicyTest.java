package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PurchasePolicyTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void updatePurchasePolicyNotExistStore() {
        assertFalse(bridge.updatePolicy(superUser.getId(),purchasePolicyTestData,"store4Test"));
    }

    @Test
    public void updatePurchasePolicyWrongAmount() {
        purchasePolicyTestData.setMaxAmount(-1);
        assertFalse(bridge.updatePolicy(superUser.getId(),purchasePolicyTestData,"store1Test"));
    }

    @Test
    public void viewPolicy() {
        assertNotNull(bridge.viewPolicy("store1Test"));
    }

    @Test
    public void viewPolicyFail() {
        assertNull(bridge.viewPolicy("store11Test"));
    }

    @After
    public void tearDown(){
        removeProducts(products);
        removeStores(stores);
    }

}
