package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuyCartTest extends AcceptanceTests {
    @Before
    public void setUp(){
        addStores(stores);
        addProducts(products);
    }

    @Test
    public void buyCartSuccess(){
        addProductToCart();
        //CartTestData currCart = bridge.getCurrentUsersCart();
        //double totalAmount = currCart.getTotalAmount();
        boolean approval = bridge.buyCart(validPayment,validDelivery);
        assertTrue(approval);
        CartTestData currCart = bridge.getCurrentUsersCart();
        assertTrue(currCart.isEmpty());
    }

    @Test
    public void buyCartFailEmptyCart(){
        assertFalse(bridge.buyCart(validPayment,validDelivery));
    }
    @Test
    public void buyCartFailInvalidPayment(){
        addProductToCart();
        CartTestData expectedCart = bridge.getCurrentUsersCart();
        assertFalse(bridge.buyCart(invalidPayment,validDelivery));
        CartTestData actualCart = bridge.getCurrentUsersCart();
        assertEquals(expectedCart,actualCart);
    }
    @Test
    public void buyCartFailInvalidDeliveryDetails(){

        addProductToCart();
        assertFalse(bridge.buyCart(validPayment,invalidDelivery));
        assertTrue(!bridge.getCurrentUsersCart().isEmpty());
    }

    @Test
    public void buyCartFailProductNotInStore(){
        StoreTestData store = stores.get(0);
        ProductTestData product = store.getProducts().get(0);
        product.setStoreName(stores.get(1).getStoreName());//TODO SHOW ROY
        bridge.addToCurrentUserCart(product,1);

        assertFalse(bridge.buyCart(validPayment,validDelivery));

    }
    @Test
    public void buyCartFailInvalidAmount(){
        addProductToCart();
        changeAmountOfProductInStore(stores.get(0).getProducts().get(0),0);
        assertFalse(bridge.buyCart(validPayment,validDelivery));


    }
    private void addProductToCart(){

        bridge.addToCurrentUserCart(stores.get(0).getProducts().get(0),1);
    }

}
