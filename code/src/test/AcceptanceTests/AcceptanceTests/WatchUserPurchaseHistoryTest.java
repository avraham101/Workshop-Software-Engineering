package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.HashSet;
import java.util.List;

/**
 * use case 3.7 - watch purchase history
 */

public class WatchUserPurchaseHistoryTest extends AcceptanceTests {
    private UserTestData user0;

    @Before
    public void setUp(){
        user0 = superUser;
        addUserStoresAndProducts(user0);
        addCartToUser(user0.getCart());
        bridge.buyCart(validPayment,validDelivery);
        List<PurchaseTestData> purchase0 = user0.getCart().makePurchasesTestData();
        user0.getPurchases().addAll(purchase0);
    }

    @Test
    public void watchUserPurchaseHistoryTestSuccess(){
        HashSet<PurchaseTestData> actualPurchases = new HashSet<>(bridge.getCurrentUserPurchaseHistory());
        HashSet<PurchaseTestData> expectedPurchase = new HashSet<>(user0.getPurchases());

        assertEquals(expectedPurchase,actualPurchases);
    }
    @Test
    public void watchUserPurchaseHistoryTestFail(){
        bridge.logout();
        List<PurchaseTestData> actualPurchases = bridge.getCurrentUserPurchaseHistory();
        assertNull(actualPurchases);
    }
}
