package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.List;

public class GetStorePurchaseHistoryTest extends AcceptanceTests {
    @Before
    public void setUp(){
        addUserStoresAndProducts(superUser);
        bridge.addToCurrentUserCart(products.get(0),1);
        bridge.buyCart(validPayment,validDelivery);
    }

    @Test
    public void getStorePurchaseHistorySuccess(){
       List<PurchaseTestData> history =
               bridge.getStorePurchasesHistory(stores.get(0).getStoreName());
       assertNotNull(history);
       assertTrue(history.get(0).getProductsAndAmountInPurchase().containsKey(products.get(0)));
    }

    @Test
    public void getStorePurchaseHistoryFailNotMyStore(){
        List<PurchaseTestData> history = bridge.getStorePurchasesHistory(stores.get(2).getStoreName());
        assertNull(history);
    }

    @Test
    public void getStorePurchaseHistoryFailStoreNotExist(){
        List<PurchaseTestData> history = bridge.getStorePurchasesHistory("notExist");
        assertNull(history);
    }

    @After
    public void tearDown(){
        deleteUserStoresAndProducts(superUser);
    }
}
