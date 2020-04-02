package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.BasketTestData;
import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class EditCartTest extends AcceptanceTests {

    private UserTestData user0;
    private CartTestData cart0;

    @Before
    public void setUp(){
        super.setUp();
        user0 = users.get(0);
        cart0 = user0.getCart();
        bridge.register(user0.getUsername(),user0.getPassword());
        bridge.addCartToUser(user0.getUsername(),cart0);
    }

    @Test
    public void viewCartTestSuccess(){
        CartTestData actualCart = bridge.getCurrentUsersCart();
        CartTestData expectedCart = cart0;

        assertEquals(actualCart,expectedCart);
    }

    @Test
    public void deleteFromCartTestSuccess(){
        BasketTestData basketToDeleteFrom = cart0.getBaskets().get(0);
        ProductTestData productToDelete = products.get(1);
        int oldBasketSize = basketToDeleteFrom.getProductsAndAmountInBasket().size();

        bridge.deleteFromCurrentUserCart(basketToDeleteFrom, productToDelete);
        BasketTestData updatedBasket = bridge.getCurrentUsersCart().getBaskets().get(0);
        int newBasketSize = updatedBasket.getProductsAndAmountInBasket().size();

        assertEquals(oldBasketSize, newBasketSize);
        assertFalse(updatedBasket.getProductsAndAmountInBasket().containsKey(productToDelete));
    }

    @Test
    public void deleteFromCartTestSuccessDeletedBasket(){
        BasketTestData basketToDeleteFrom = cart0.getBaskets().get(1);
        ProductTestData productToDelete = products.get(4);
        CartTestData expectedCart = new CartTestData(Arrays.asList(cart0.getBaskets().get(0),
                                                    cart0.getBaskets().get(2)));

        bridge.deleteFromCurrentUserCart(basketToDeleteFrom, productToDelete);
        CartTestData actualCart = bridge.getCurrentUsersCart();

        assertEquals(expectedCart, actualCart);
    }




}
