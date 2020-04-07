package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.BasketTestData;
import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.*;

public class AddToCartTest extends AcceptanceTests {

    private UserTestData user0;
    private CartTestData cart0;

    @Before
    public void setUp(){
        user0 = superUser;
        addUserStoresAndProducts(user0);
        cart0 = user0.getCart();
    }
    @Test
    public void addToCartTestSuccessExistingBasket(){
        List<BasketTestData> baskets = cart0.getBaskets();

        for(BasketTestData basket : baskets){
            for(Map.Entry<ProductTestData,Integer> entry : basket.getProductsAndAmountInBasket().entrySet()){
                ProductTestData productToAdd = entry.getKey();
                Integer amount = entry.getValue();
                boolean isAdded = bridge.addToCurrentUserCart(productToAdd,amount);
                assertTrue(isAdded);
            }
        }
        CartTestData actualCart = bridge.getCurrentUsersCart();
        assertEquals(cart0,actualCart);
    }

    @Test
    public void addToCartTestFailNotExistingStore(){
        ProductTestData productToAdd = products.get(8);
        int amount = 10;
        String storeName = productToAdd.getStoreName();
        productToAdd.setStoreName(notExistingStore.getStoreName());

        boolean isAdded = bridge.addToCurrentUserCart(productToAdd,amount);
        assertFalse(isAdded);

        productToAdd.setStoreName(storeName);
    }

    @Test
    public void addToCartTestFailProductNotInStore(){
        ProductTestData productToAdd = products.get(8);
        int amount = 10;

        deleteProducts(new ArrayList<>(Collections.singletonList(productToAdd)));
        boolean isAdded = bridge.addToCurrentUserCart(productToAdd,amount);
        assertFalse(isAdded);
    }

    @Test
    public void addToCartFailNonPositiveAmount(){
        ProductTestData productToAdd = products.get(8);
        int amount = Integer.MIN_VALUE;

        boolean isAdded = bridge.addToCurrentUserCart(productToAdd,amount);
        assertFalse(isAdded);

        amount = 0;
        isAdded = bridge.addToCurrentUserCart(productToAdd,amount);
        assertFalse(isAdded);
    }

    @After
    public void tearDown(){
        deleteUserStoresAndProducts(user0);
    }
}
