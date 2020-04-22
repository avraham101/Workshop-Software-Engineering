package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

/**
 * 4.10 - owner gets store history
 */
public class GetStorePurchaseHistoryTest extends AcceptanceTests {
    @Before
    public void setUp(){
        addUserStoresAndProducts(superUser);
        bridge.addToUserCart(superUser.getId(),products.get(0),1);
        bridge.buyCart(validPayment,validDelivery);
    }

    @Test
    public void getStorePurchaseHistorySuccess(){
       List<PurchaseTestData> history =
               bridge.userGetStorePurchasesHistory(stores.get(0).getStoreName());
       assertNotNull(history);
       assertTrue(history.get(0).getProductsAndAmountInPurchase().containsKey(products.get(0)));
    }

    @Test
    public void getStorePurchaseHistoryFailNotMyStore(){
        List<PurchaseTestData> history = bridge.userGetStorePurchasesHistory(stores.get(2).getStoreName());
        assertNull(history);
    }

    @Test
    public void getStorePurchaseHistoryFailStoreNotExist(){
        List<PurchaseTestData> history = bridge.userGetStorePurchasesHistory("notExist");
        assertNull(history);
    }

}
