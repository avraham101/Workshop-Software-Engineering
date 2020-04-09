package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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
        assertFalse(history.size() != 0);
    }

    @Test
    public void getStorePurchaseHistoryFailStoreNotExist(){
        List<PurchaseTestData> history = bridge.getStorePurchasesHistory("notExist");
        assertFalse(history.size() != 0);
    }

}
