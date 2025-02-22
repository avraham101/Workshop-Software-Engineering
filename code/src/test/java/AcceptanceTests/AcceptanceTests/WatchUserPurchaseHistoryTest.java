package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * use case 3.7 - watch purchase history
 */

public class WatchUserPurchaseHistoryTest extends AcceptanceTests {
    private UserTestData user0;

    @Before
    public void setUp(){
        user0 = superUser;
        addUserStoresAndProducts(user0);
        addCartToUser(user0.getId(),user0.getCart());
        bridge.buyCart(user0.getId(),validPayment,validDelivery);
        List<PurchaseTestData> purchase0 = user0.getCart().makePurchasesTestData();
        user0.getPurchases().addAll(purchase0);
    }

    @Test
    public void watchUserPurchaseHistoryTestSuccess(){
        HashSet<PurchaseTestData> actualPurchases = new HashSet<>(bridge.getCurrentUserPurchaseHistory(user0.getId()));
        HashSet<PurchaseTestData> expectedPurchase = new HashSet<>(user0.getPurchases());

        assertEquals(expectedPurchase,actualPurchases);
    }
    @Test
    public void watchUserPurchaseHistoryTestFail(){
        bridge.logout(user0.getId());
        List<PurchaseTestData> actualPurchases = bridge.getCurrentUserPurchaseHistory(user0.getId());
        assertNull(actualPurchases);
    }

    @After
    public void tearDown(){

        removeUserStoresAndProducts(user0);
    }
}
