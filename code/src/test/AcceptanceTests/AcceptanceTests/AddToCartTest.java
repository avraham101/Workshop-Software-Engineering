package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.BasketTestData;
import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class AddToCartTest extends AcceptanceTests {

    private UserTestData user0;
    private CartTestData cart0;

    @Before
    public void setUp(){
        super.setUp();
        user0 = superUser;
        bridge.register(user0.getUsername(),user0.getPassword());
        bridge.login(user0.getUsername(),user0.getPassword());
        addStores(stores);
        addProducts(products);
        cart0 = user0.getCart();
    }
    @Test
    public void addToCartTestSuccessExistingBasket(){
        List<BasketTestData> baskets = cart0.getBaskets();

        for(BasketTestData basket : baskets){
            String storeName = basket.getStoreName();
            for(Map.Entry<ProductTestData,Integer> entry : basket.getProductsAndAmountInBasket().entrySet()){
                ProductTestData productToAdd = entry.getKey();
                Integer amount = entry.getValue();
                boolean isAdded = bridge.addToCurrentUserCart(storeName,productToAdd,amount);
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
        String storeName = notExistingStore.getStoreName();

        boolean isAdded = bridge.addToCurrentUserCart(storeName,productToAdd,amount);
        assertFalse(isAdded);
    }

    @Test
    public void addToCartTestFailProductNotInStore(){
        ProductTestData productToAdd = products.get(8);
        String storeName = stores.get(0).getStoreName();
        int amount = 10;

        boolean isAdded = bridge.addToCurrentUserCart(storeName,productToAdd,amount);
        assertFalse(isAdded);
    }

    @Test
    public void addToCartFailNonPositiveAmount(){
        ProductTestData productToAdd = products.get(8);
        int amount = Integer.MIN_VALUE;
        String storeName = productToAdd.getStoreName();

        boolean isAdded = bridge.addToCurrentUserCart(storeName,productToAdd,amount);
        assertFalse(isAdded);

        amount = 0;
        isAdded = bridge.addToCurrentUserCart(storeName,productToAdd,amount);
        assertFalse(isAdded);

    }

    @After
    public void tearDown(){
        deleteProducts(products);
        deleteStores(stores);
        deleteUser(user0.getUsername());
    }
}
