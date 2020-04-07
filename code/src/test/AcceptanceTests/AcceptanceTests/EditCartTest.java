package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.BasketTestData;
import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
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
        user0 = superUser;
        addUserStoresAndProducts(user0);
        cart0 = user0.getCart();
        bridge.addCartToUser(user0.getUsername(),cart0);

        notExistingBasket = new BasketTestData("notExistingBasket");
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

        assertEquals(oldBasketSize - 1, newBasketSize);
        assertFalse(updatedBasket.getProductsAndAmountInBasket().containsKey(productToDelete));
    }

    @Test
    public void deleteFromCartTestSuccessDeletedBasket(){
        BasketTestData basketToDeleteFrom = cart0.getBaskets().get(1);
        ProductTestData productToDelete = products.get(4);

        bridge.deleteFromCurrentUserCart(basketToDeleteFrom, productToDelete);
        CartTestData actualCart = bridge.getCurrentUsersCart();

        int actualCartSize = actualCart.getBaskets().size();
        int expectedCartSize = cart0.getBaskets().size()-1;

        assertEquals(expectedCartSize, actualCartSize);
    }

    @Test
    public void deleteFromCartTestFailNotExistingBasket(){

        boolean isDeleted = bridge.deleteFromCurrentUserCart(notExistingBasket,
                                                            productInNonExistingBaskets);
        assertFalse(isDeleted);
    }

    @Test
    public void deleteFromCartTestFailNotExistingProductInBasket(){
        ProductTestData nonExistingProductInBasket = products.get(2);
        BasketTestData basketToDeleteFrom = cart0.getBaskets().get(0);

        boolean isDeleted = bridge.deleteFromCurrentUserCart(basketToDeleteFrom,
                                                            nonExistingProductInBasket);
        assertFalse(isDeleted);
    }

    @Test
    public void changeAmountOfProductInCartSuccess(){
        BasketTestData basketToChangeAmountIn = cart0.getBaskets().get(0);
        ProductTestData productToChangeAmount = products.get(0);
        int oldAmountOfProduct = basketToChangeAmountIn.getProductsAndAmountInBasket().
                get(productToChangeAmount);
        int expectedAmount = oldAmountOfProduct * 2;

        boolean isChanged = bridge.changeCurrentUserAmountOfProductInCart
                (basketToChangeAmountIn, productToChangeAmount, expectedAmount);
        assertTrue(isChanged);

        int actualAmount = bridge.getCurrentUsersCart().getBaskets().get(0).
                getProductsAndAmountInBasket().get(productToChangeAmount);
        assertEquals(expectedAmount,actualAmount);
    }

    @Test
    public void changeAmountOfProductInCartFailNotExistingBasket(){
        int oldAmount = notExistingBasket.
                getProductsAndAmountInBasket().get(productInNonExistingBaskets);
        int newAmount = oldAmount * 2;

        boolean isChanged = bridge.changeCurrentUserAmountOfProductInCart
                (notExistingBasket, productInNonExistingBaskets, newAmount);
        assertFalse(isChanged);
        BasketTestData basket = bridge.getCurrentUsersCart().getBasket(notExistingBasket.getStoreName());
        assertNull(basket);
    }

    @Test
    public void changeAmountOfProductInCartFailNotExistingProductInBasket(){
        ProductTestData nonExistingProductInBasket = products.get(2);
        BasketTestData basketToChangeAmountFrom = cart0.getBaskets().get(0);
        int newAmount = 50;

        boolean isChanged = bridge.changeCurrentUserAmountOfProductInCart
                (basketToChangeAmountFrom,nonExistingProductInBasket,newAmount);
        assertFalse(isChanged);
        assertFalse(basketToChangeAmountFrom.getProductsAndAmountInBasket().containsKey(nonExistingProductInBasket));
    }

    @Test
    public void changeAmountOfProductInCartFailNonPositiveAmount(){
        BasketTestData basketToChangedAmountFrom = cart0.getBaskets().get(0);
        ProductTestData productToChangedAmount = products.get(1);
        int newAmount0 = Integer.MIN_VALUE;
        int newAmount1 = 0;
        int expectedAmount = basketToChangedAmountFrom.getProductsAndAmountInBasket().
                get(productToChangedAmount);

        boolean isChanged0 = bridge.changeCurrentUserAmountOfProductInCart
                            (basketToChangedAmountFrom,productToChangedAmount,newAmount0);
        int actualAmount0 = bridge.getCurrentUsersCart().getBaskets().get(0).
                            getProductsAndAmountInBasket().get(productToChangedAmount);

        assertFalse(isChanged0);
        assertEquals(expectedAmount,actualAmount0);

        boolean isChanged1 = bridge.changeCurrentUserAmountOfProductInCart
                            (basketToChangedAmountFrom,productToChangedAmount,newAmount1);
        int actualAmount1 = bridge.getCurrentUsersCart().getBaskets().get(0).
                getProductsAndAmountInBasket().get(productToChangedAmount);

        assertFalse(isChanged1);
        assertEquals(expectedAmount,actualAmount1);
    }


    @After
    public void tearDown(){
        deleteUserStoresAndProducts(user0);
    }
}
