package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.BasketTestData;
import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.*;

/**
 * use case 2.7.4 - add product to the cart
 */

public class AddToCartTest extends AcceptanceTests {

    private UserTestData user0;
    private CartTestData cart0;

    @Before
    public void setUp(){
        user0 = superUser;
        addUserStoresAndProducts(user0);
        cart0 = user0.getCart();
        addCartToUser(user0.getId(),cart0);
    }
    @Test
    public void addToCartTestSuccessExistingBasket(){
        List<BasketTestData> baskets = cart0.getBaskets();

        for(BasketTestData basket : baskets){
            for(Map.Entry<ProductTestData,Integer> entry : basket.getProductsAndAmountInBasket().entrySet()){
                ProductTestData productToAdd = entry.getKey();
                Integer amount = entry.getValue();
                boolean isAdded = bridge.addToUserCart(user0.getId(),productToAdd,amount);
                assertTrue(isAdded);
            }
        }
        CartTestData actualCart = bridge.getUsersCart(user0.getId());
        assertEquals(cart0,actualCart);
    }

    @Test
    public void addToCartTestFailNotExistingStore(){
        ProductTestData productToAdd = products.get(8);
        int amount = 10;
        String storeName = productToAdd.getStoreName();
        productToAdd.setStoreName(notExistingStore.getStoreName());

        boolean isAdded = bridge.addToUserCart(user0.getId(),productToAdd,amount);
        assertFalse(isAdded);

        productToAdd.setStoreName(storeName);
    }

    @Test
    public void addToCartTestFailProductNotInStore(){
        ProductTestData productToAdd = products.get(8);
        int amount = 10;

        logoutAndDeleteProduct(productToAdd);
        boolean isAdded = bridge.addToUserCart(user0.getId(),productToAdd,amount);
        assertFalse(isAdded);
    }

    private void logoutAndDeleteProduct(ProductTestData productToDelete){
        logoutAndLogin(admin);
        bridge.deleteProduct(admin.getId(),productToDelete);
        logoutAndLogin(user0);
    }

    @Test
    public void addToCartFailNonPositiveAmount(){
        ProductTestData productToAdd = products.get(8);
        int amount = Integer.MIN_VALUE;

        boolean isAdded = bridge.addToUserCart(user0.getId(),productToAdd,amount);
        assertFalse(isAdded);

        amount = 0;
        isAdded = bridge.addToUserCart(user0.getId(),productToAdd,amount);
        assertFalse(isAdded);
    }

}
