package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.BasketTestData;
import AcceptanceTests.AcceptanceTestDataObjects.CartTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
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
        addCartToUser(user0.getId(),cart0);

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
        CartTestData actualCart = bridge.getUsersCart(user0.getId());
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

        bridge.deleteFromUserCart(user0.getId(),productToDelete);
        BasketTestData updatedBasket = bridge.getUsersCart(user0.getId()).getBaskets().get(0);
        int newBasketSize = updatedBasket.getProductsAndAmountInBasket().size();

        assertEquals(oldBasketSize - 1, newBasketSize);
        assertFalse(updatedBasket.getProductsAndAmountInBasket().containsKey(productToDelete));
    }

    @Test
    public void deleteFromCartTestSuccessDeletedBasket(){
        ProductTestData productToDelete = products.get(4);

        bridge.deleteFromUserCart(user0.getId(),productToDelete);
        CartTestData actualCart = bridge.getUsersCart(user0.getId());

        int actualCartSize = actualCart.getBaskets().size();
        int expectedCartSize = cart0.getBaskets().size()-1;

        assertEquals(expectedCartSize, actualCartSize);
    }

    @Test
    public void deleteFromCartTestFailNotExistingBasket(){

        boolean isDeleted = bridge.deleteFromUserCart(user0.getId(),productInNonExistingBaskets);
        assertFalse(isDeleted);
    }

    @Test
    public void deleteFromCartTestFailNotExistingProductInBasket(){
        ProductTestData nonExistingProductInBasket = products.get(2);

        boolean isDeleted = bridge.deleteFromUserCart(user0.getId(),nonExistingProductInBasket);
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

        boolean isChanged = bridge.changeUserAmountOfProductInCart
                (user0.getId(),productToChangeAmount, expectedAmount);
        assertTrue(isChanged);

        CartTestData testCart = bridge.getUsersCart(user0.getId());

        int actualAmount = testCart.getBasket(basketToChangeAmountIn.getStoreName()).
                getProductsAndAmountInBasket().get(productToChangeAmount);
        assertEquals(expectedAmount,actualAmount);
    }

    @Test
    public void changeAmountOfProductInCartFailNotExistingBasket(){
        int oldAmount = notExistingBasket.
                getProductsAndAmountInBasket().get(productInNonExistingBaskets);
        int newAmount = oldAmount * 2;

        boolean isChanged = bridge.changeUserAmountOfProductInCart
                (user0.getId(),productInNonExistingBaskets, newAmount);
        assertFalse(isChanged);
        BasketTestData basket = bridge.getUsersCart(user0.getId()).getBasket(notExistingBasket.getStoreName());
        assertNull(basket);
    }

    @Test
    public void changeAmountOfProductInCartFailNotExistingProductInBasket(){
        ProductTestData nonExistingProductInBasket = products.get(2);
        BasketTestData basketToChangeAmountFrom = cart0.getBaskets().get(0);
        int newAmount = 50;

        boolean isChanged = bridge.changeUserAmountOfProductInCart
                (user0.getId(),nonExistingProductInBasket,newAmount);
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

        boolean isChanged0 = bridge.changeUserAmountOfProductInCart
                            (user0.getId(),productToChangedAmount,newAmount0);
        int actualAmount0 = bridge.getUsersCart(user0.getId()).getBasket(basketToChangedAmountFrom.getStoreName()).
                            getProductsAndAmountInBasket().get(productToChangedAmount);

        assertFalse(isChanged0);
        assertEquals(expectedAmount,actualAmount0);

        boolean isChanged1 = bridge.changeUserAmountOfProductInCart
                            (user0.getId(),productToChangedAmount,newAmount1);
        int actualAmount1 = bridge.getUsersCart(user0.getId()).getBasket(basketToChangedAmountFrom.getStoreName()).
                getProductsAndAmountInBasket().get(productToChangedAmount);

        assertFalse(isChanged1);
        assertEquals(expectedAmount,actualAmount1);
    }

    @After
    public void tearDown(){
        removeUser(user0.getUsername());
        removeProducts(products);
        removeStores(stores);
    }
}
