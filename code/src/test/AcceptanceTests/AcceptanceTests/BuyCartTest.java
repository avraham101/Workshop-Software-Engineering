package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.PurchaseTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        CartTestData currCart = bridge.getCurrentUsersCart();
        double totalAmount = currCart.getTotalAmount();
        PurchaseTestData receipt = bridge.buyCart(validPayment,validDelivery);
        assertNotNull(receipt);
        assertEquals(receipt.getTotalAmount(),totalAmount);

        HashMap<ProductTestData,Integer> prodAndAmountsInCart = currCart.getProductsAndAmountsInCart();
        HashMap<ProductTestData,Integer> prodAndAmountsInPurchase = receipt.getProductsAndAmountInPurchase();
        assertEquals(prodAndAmountsInCart,prodAndAmountsInPurchase);
        assertTrue(bridge.getCurrentUsersCart().isEmpty());
    }

    @Test
    public void buyCartFailEmptyCart(){
        assertNull(bridge.buyCart(validPayment,validDelivery));
    }
    @Test
    public void buyCartFailInvalidPayment(){
        addProductToCart();
        CartTestData expectedCart = bridge.getCurrentUsersCart();
        assertNull(bridge.buyCart(invalidPayment,validDelivery));
        CartTestData actualCart = bridge.getCurrentUsersCart();
        assertEquals(expectedCart,actualCart);
    }
    @Test
    public void buyCartFailInvalidDeliveryDetails(){

        addProductToCart();
        assertNull(bridge.buyCart(validPayment,invalidDelivery));
        assertTrue(!bridge.getCurrentUsersCart().isEmpty());
    }

    @Test
    public void buyCartFailProductNotInStore(){
        StoreTestData store = stores.get(0);
        ProductTestData product = store.getProducts().get(0);
        List<ProductTestData> productsToAdd = new ArrayList<>();
        productsToAdd.add(product);
        bridge.addToCurrentUserCart(product,1);
        deleteProducts(productsToAdd);
        assertNull(bridge.buyCart(validPayment,validDelivery));
    }
    @Test
    public void buyCartFailInvalidAmount(){
        addProductToCart();
        changeAmountOfProductInStore(stores.get(0).getProducts().get(0),0);
        assertNull(bridge.buyCart(validPayment,validDelivery));


    }
    private void addProductToCart(){
        bridge.addToCurrentUserCart(stores.get(0).getProducts().get(0),1);
    }

    @After
    public void tearDown(){
        bridge.resetSystem();
    }
}
