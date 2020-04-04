package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GetStorePurchaseHistoryTest extends AcceptanceTests {
    @Before
    public void setUp(){
        super.setUp();
        addStores(stores);
        addProducts(products);
        bridge.login(superUser.getUsername(),superUser.getPassword());
        bridge.addToCurrentUserCart(products.get(0),1);
        bridge.buyCart(validPayment,validDelivery);
        //TODO clear history?
    }

    @Test
    public void getStorePurchaseHistorySuccess(){
       List<PurchaseTestData> history=bridge.getStorePurchasesHistory(stores.get(0).getStoreName());
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
        deleteStores(stores);
        deleteProducts(products);
        bridge.logout(superUser.getUsername());
    }
}
