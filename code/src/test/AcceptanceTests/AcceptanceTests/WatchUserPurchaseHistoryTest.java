package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.List;

public class WatchUserPurchaseHistoryTest extends AcceptanceTests {
    private UserTestData user0;

    @Before
    public void setUp(){
        user0 = superUser;
        addUserStoresAndProducts(user0);
        addCartToUser(user0.getCart());
       // PurchaseTestData purchase0 = bridge.buyCart(validPayment, validDelivery);
        //user0.getPurchases().add(purchase0);
    }

    @Test
    public void watchUserPurchaseHistoryTestSuccess(){
        List<PurchaseTestData> actualPurchases = bridge.getCurrentUserPurchaseHistory();
        List<PurchaseTestData> expectedPurchase = user0.getPurchases();

        assertEquals(expectedPurchase,actualPurchases);
    }
    @Test
    public void watchUserPurchaseHistoryTestFail(){
        bridge.logout();
        List<PurchaseTestData> actualPurchases = bridge.getCurrentUserPurchaseHistory();
        assertEquals(0,actualPurchases.size());
    }
}
