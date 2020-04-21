package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AdminPurchaseHistory extends AcceptanceTests {
    UserTestData userToCheck;
    @Before
    public void setUp(){
        userToCheck = users.get(1);
        addUserStoresAndProducts(userToCheck);

        bridge.addToCurrentUserCart(products.get(0),1);
        bridge.buyCart(validPayment,validDelivery);

        bridge.logout();
        bridge.login(admin.getUsername(),admin.getPassword());
    }

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     */
    @Test
    public void getUserPurchaseHistorySuccess(){
        List<PurchaseTestData> history = bridge.getUserPurchaseHistory(userToCheck.getUsername());
        assertNotNull(history);
        assertTrue(history.get(0).getProductsAndAmountInPurchase().containsKey(products.get(0)));
    }

    @Test
    public void getUserPurchaseHistoryFailInvalidUser(){
        List<PurchaseTestData> history = bridge.getUserPurchaseHistory("guest");
        assertNull(history);
    }

    /**
     * use case 6.4.2 - admin watch history purchases of some user
     */

    @Test
    public void getStorePurchaseHistorySuccess(){
        List<PurchaseTestData> history = bridge.getStorePurchasesHistory(stores.get(0).getStoreName());
        assertNotNull(history);
        assertTrue(history.get(0).getProductsAndAmountInPurchase().containsKey(products.get(0)));
    }

    @Test
    public void getStorePurchaseHistoryFailStoreNotExist(){
        List<PurchaseTestData> history = bridge.getStorePurchasesHistory("notExist");
        assertNull(history);
    }
}
