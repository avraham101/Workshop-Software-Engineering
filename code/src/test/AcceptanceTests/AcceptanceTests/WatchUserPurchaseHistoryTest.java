package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class WatchUserPurchaseHistoryTest extends AcceptanceTests {
    private UserTestData user0;

    @Before
    public void setUp(){
        super.setUp();
        user0 = superUser;
        String username = user0.getUsername();
        String password = user0.getPassword();
        bridge.register(username,password);
        bridge.login(username,password);
        addStores(stores);
        addProducts(products);
        PurchaseTestData purchase0 = bridge.buyCart(validPayment, validDelivery);
        user0.getPurchases().add(purchase0);
    }

    @Test
    public void watchUserPurchaseHistoryTestSuccess(){
        List<PurchaseTestData> actualPurchases = bridge.getUserPurchases(user0.getUsername());
        List<PurchaseTestData> expectedPurchase = user0.getPurchases();

        assertEquals(expectedPurchase,actualPurchases);
    }
    @Test
    public void watchUserPurchaseHistoryTestFail(){
        bridge.logout(user0.getUsername());
        List<PurchaseTestData> actualPurchases = bridge.getUserPurchases(user0.getUsername());
        assertNull(actualPurchases);
        bridge.login(user0.getUsername(),user0.getPassword());
    }

    @After
    public void tearDown(){
        deleteProducts(products);
        deleteStores(stores);
        deleteUser(user0.getUsername());
    }


}
