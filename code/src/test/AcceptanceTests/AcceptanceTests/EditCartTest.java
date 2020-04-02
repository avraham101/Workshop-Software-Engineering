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
    private BasketTestData notExistingBasket;
    private ProductTestData productInNonExistingBaskets;

    @Before
    public void setUp(){
        super.setUp();
        user0 = users.get(0);
        cart0 = user0.getCart();
        bridge.register(user0.getUsername(),user0.getPassword());
        bridge.addCartToUser(user0.getUsername(),cart0);

        notExistingBasket = new BasketTestData(stores.get(1).getStoreName());
        productInNonExistingBaskets = products.get(5);
        notExistingBasket.addProductToBasket(productInNonExistingBaskets,13);
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

    @Test
    public void deleteFromCartTestFailNotExistingBasket(){
        BasketTestData notExistingBasket = new BasketTestData(stores.get(1).getStoreName());
        ProductTestData productInNonExistingBaskets = products.get(5);
        notExistingBasket.addProductToBasket(productInNonExistingBaskets,13);

        boolean isDeleted = bridge.deleteFromCurrentUserCart(notExistingBasket,productInNonExistingBaskets);
        assertFalse(isDeleted);
    }

    @Test
    public void deleteFromCartTestFailNotExistingProductInBasket(){
        ProductTestData nonExistingProductInBasket = products.get(2);
        BasketTestData basketToDeleteFrom = cart0.getBaskets().get(0);

        boolean isDeleted = bridge.deleteFromCurrentUserCart(basketToDeleteFrom, nonExistingProductInBasket);
        assertFalse(isDeleted);
    }

    @Test
    public void changeAmountOfProductInCartSuccess(){
        BasketTestData basketToChangeAmountIn = user0.getCart().getBaskets().get(0);
        ProductTestData productToChangeAmount = products.get(0);
        int oldAmountOfProduct = basketToChangeAmountIn.getProductsAndAmountInBasket().get(productToChangeAmount);
        int expectedAmount = oldAmountOfProduct * 2;

        boolean isChanged = bridge.changeCurrentUserAmountOfProductInCart(basketToChangeAmountIn, productToChangeAmount, expectedAmount);
        assertTrue(isChanged);

        int actualAmount = bridge.getCurrentUsersCart().getBaskets().get(0).getProductsAndAmountInBasket().get(productToChangeAmount);
        assertEquals(expectedAmount,actualAmount);
    }

    @Test
    public void changeAmountOfProductInCartFailNotExistingBasket(){
        int oldAmount = notExistingBasket.getProductsAndAmountInBasket().get(productInNonExistingBaskets);
        int newAmount = oldAmount * 2;

        boolean isChanged = bridge.changeCurrentUserAmountOfProductInCart(notExistingBasket, productInNonExistingBaskets, newAmount);
        assertFalse(isChanged);
        BasketTestData basket = bridge.getCurrentUsersCart().getBasket(notExistingBasket.getStoreName());
        assertNull(basket);
    }

    @Test
    public void changeAmountOfProductInCartFailNotExistingProductInBasket(){
        ProductTestData nonExistingProductInBasket = products.get(2);
        BasketTestData basketToChangeAmountFrom = cart0.getBaskets().get(0);
        int newAmount = Integer.MAX_VALUE;

        boolean isChanged = bridge.changeCurrentUserAmountOfProductInCart(basketToChangeAmountFrom,nonExistingProductInBasket,newAmount);
        assertFalse(isChanged);
    }

    @Test
    public void changeAmountOfProductInCartFailNonPositiveAmount(){
        BasketTestData basketToDeleteFrom = cart0.getBaskets().get(0);
        ProductTestData productToDelete = products.get(1);
        int newAmount0 = Integer.MIN_VALUE;
        int newAmount1 = 0;
        int expectedAmount = basketToDeleteFrom.getProductsAndAmountInBasket().get(productToDelete);

        boolean isChanged0 = bridge.changeCurrentUserAmountOfProductInCart
                            (basketToDeleteFrom,productToDelete,newAmount0);
        int actualAmount0 = bridge.getCurrentUsersCart().getBaskets().get(0).
                            getProductsAndAmountInBasket().get(productToDelete);

        assertFalse(isChanged0);
        assertEquals(expectedAmount,actualAmount0);

        boolean isChanged1 = bridge.changeCurrentUserAmountOfProductInCart
                            (basketToDeleteFrom,productToDelete,newAmount1);
        int actualAmount1 = bridge.getCurrentUsersCart().getBaskets().get(0).
                getProductsAndAmountInBasket().get(productToDelete);

        assertFalse(isChanged1);
        assertEquals(expectedAmount,actualAmount1);
    }
}
