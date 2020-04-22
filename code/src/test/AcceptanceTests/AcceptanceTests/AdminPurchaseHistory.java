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

        bridge.addToUserCart(userToCheck.getId(),products.get(0),1);
        bridge.buyCart(userToCheck.getId(),validPayment,validDelivery);
        //TODO: remove line ?
        bridge.logout(userToCheck.getId());
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
    }

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     */
    @Test
    public void getUserPurchaseHistorySuccess(){
        List<PurchaseTestData> history = bridge.getUserPurchaseHistory(admin.getId(),userToCheck.getUsername());
        assertNotNull(history);
        assertTrue(history.get(0).getProductsAndAmountInPurchase().containsKey(products.get(0)));
    }

    @Test
    public void getUserPurchaseHistoryFailInvalidUser(){
        List<PurchaseTestData> history = bridge.getUserPurchaseHistory(admin.getId(),
                                                                "guest");
        assertNull(history);
    }

    /**
     * use case 6.4.2 - admin watch history purchases of some user
     */

    @Test
    public void getStorePurchaseHistorySuccess(){
        List<PurchaseTestData> history = bridge.getStorePurchasesHistory(admin.getId(),
                stores.get(0).getStoreName());
        assertNotNull(history);
        assertTrue(history.get(0).getProductsAndAmountInPurchase().containsKey(products.get(0)));
    }

    @Test
    public void getStorePurchaseHistoryFailStoreNotExist(){
        List<PurchaseTestData> history = bridge.getStorePurchasesHistory(admin.getId(),
                "notExist");
        assertNull(history);
    }
}
