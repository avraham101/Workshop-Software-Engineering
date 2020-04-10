package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.BasketTestData;
import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

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
        addCartToUser(cart0);

        notExistingBasket = new BasketTestData("notExistingBasket");
        productInNonExistingBaskets = products.get(5);
        productInNonExistingBaskets.setStoreName("notExistingBasket");
        notExistingBasket.addProductToBasket(productInNonExistingBaskets,13);
    }

    /**
     * use case 2.7.1 - watch cart details
     */

    @Test
    public void viewCartTestSuccess(){
        CartTestData actualCart = bridge.getCurrentUsersCart();
        CartTestData expectedCart = cart0;

        assertEquals(actualCart,expectedCart);
    }

    /**
     * use case 2.7.2 - delete product from cart
     */

    @Test
    public void deleteFromCartTestSuccess(){
        BasketTestData basketToDeleteFrom = cart0.getBaskets().get(0);
        ProductTestData productToDelete = products.get(1);
        int oldBasketSize = basketToDeleteFrom.getProductsAndAmountInBasket().size();

        bridge.deleteFromCurrentUserCart(productToDelete);
        BasketTestData updatedBasket = bridge.getCurrentUsersCart().getBaskets().get(0);
        int newBasketSize = updatedBasket.getProductsAndAmountInBasket().size();

        assertEquals(oldBasketSize - 1, newBasketSize);
        assertFalse(updatedBasket.getProductsAndAmountInBasket().containsKey(productToDelete));
    }

    @Test
    public void deleteFromCartTestSuccessDeletedBasket(){
        ProductTestData productToDelete = products.get(4);

        bridge.deleteFromCurrentUserCart(productToDelete);
        CartTestData actualCart = bridge.getCurrentUsersCart();

        int actualCartSize = actualCart.getBaskets().size();
        int expectedCartSize = cart0.getBaskets().size()-1;

        assertEquals(expectedCartSize, actualCartSize);
    }

    @Test
    public void deleteFromCartTestFailNotExistingBasket(){

        boolean isDeleted = bridge.deleteFromCurrentUserCart(productInNonExistingBaskets);
        assertFalse(isDeleted);
    }

    @Test
    public void deleteFromCartTestFailNotExistingProductInBasket(){
        ProductTestData nonExistingProductInBasket = products.get(2);

        boolean isDeleted = bridge.deleteFromCurrentUserCart(nonExistingProductInBasket);
        assertFalse(isDeleted);
    }

    /**
     * use case 2.7.3 edit amount of product
     */

    @Test
    public void changeAmountOfProductInCartSuccess(){
        BasketTestData basketToChangeAmountIn = cart0.getBaskets().get(0);
        ProductTestData productToChangeAmount = products.get(0);
        int oldAmountOfProduct = basketToChangeAmountIn.getProductsAndAmountInBasket().
                get(productToChangeAmount);
        int expectedAmount = oldAmountOfProduct * 2;

        boolean isChanged = bridge.changeCurrentUserAmountOfProductInCart
                (productToChangeAmount, expectedAmount);
        assertTrue(isChanged);

        CartTestData testCart = bridge.getCurrentUsersCart();

        int actualAmount = testCart.getBasket(basketToChangeAmountIn.getStoreName()).
                getProductsAndAmountInBasket().get(productToChangeAmount);
        assertEquals(expectedAmount,actualAmount);
    }

    @Test
    public void changeAmountOfProductInCartFailNotExistingBasket(){
        int oldAmount = notExistingBasket.
                getProductsAndAmountInBasket().get(productInNonExistingBaskets);
        int newAmount = oldAmount * 2;

        boolean isChanged = bridge.changeCurrentUserAmountOfProductInCart
                (productInNonExistingBaskets, newAmount);
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
                (nonExistingProductInBasket,newAmount);
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
                            (productToChangedAmount,newAmount0);
        int actualAmount0 = bridge.getCurrentUsersCart().getBasket(basketToChangedAmountFrom.getStoreName()).
                            getProductsAndAmountInBasket().get(productToChangedAmount);

        assertFalse(isChanged0);
        assertEquals(expectedAmount,actualAmount0);

        boolean isChanged1 = bridge.changeCurrentUserAmountOfProductInCart
                            (productToChangedAmount,newAmount1);
        int actualAmount1 = bridge.getCurrentUsersCart().getBasket(basketToChangedAmountFrom.getStoreName()).
                getProductsAndAmountInBasket().get(productToChangedAmount);

        assertFalse(isChanged1);
        assertEquals(expectedAmount,actualAmount1);
    }
}
