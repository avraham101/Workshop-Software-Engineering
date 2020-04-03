package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class buyCart extends AcceptanceTests {
    @Before
    public void setUp(){
        super.setUp();
        bridge.addStores(stores);
        bridge.addProducts(products);

    }
    @Test
    public void buyCartSuccess(){
        bridge.addToCurrentUserCart(stores.get(0).getStoreName(),
                stores.get(0).getProducts().get(0),1);
        CartTestData currCart = bridge.getCurrentUsersCart();
        double totalAmount = currCart.getTotalAmount();
        PurchaseTestData receipt = bridge.buyCart(validPayment,validDelivery);
        assertNotNull(receipt);
        assertEquals(receipt.getTotalAmount(),totalAmount);
      //  assertEquals(receipt.getProductsAndAmountInPurchase(), );//TODO check recipt pruducts==prev cart??
        assertTrue(bridge.getCurrentUsersCart().isEmpty());

    }

    @Test
    public void buyCartFailEmptyCart(){
        bridge.addToCurrentUserCart(stores.get(0).getStoreName(),
                stores.get(0).getProducts().get(0),1);
        assertNull(bridge.buyCart(validPayment,validDelivery));
    }
    @Test
    public void buyCartFailInvalidPayment(){
        bridge.addToCurrentUserCart(stores.get(0).getStoreName(),
                stores.get(0).getProducts().get(0),1);
        assertNull(bridge.buyCart(invalidPayment,validDelivery));
        assertTrue(!bridge.getCurrentUsersCart().isEmpty());
    }
    @Test
    public void buyCartFailInvalidDeliveryDetails(){
        bridge.addToCurrentUserCart(stores.get(0).getStoreName(),
                stores.get(0).getProducts().get(0),1);
        assertNull(bridge.buyCart(validPayment,invalidDelivery));
        assertTrue(!bridge.getCurrentUsersCart().isEmpty());
    }

    @Test
    public void buyCartFailProductNotInStore(){
        StoreTestData store = stores.get(0);
        ProductTestData product = store.getProducts().get(0);
        bridge.addToCurrentUserCart(store.getStoreName(),product,1);
       // bridge.deleteProducts(product);
        assertNull(bridge.buyCart(validPayment,validDelivery));
    }

    @After
    public void tearDown(){
        bridge.deleteProducts(products);
        bridge.deleteStores(stores);
    }
}
