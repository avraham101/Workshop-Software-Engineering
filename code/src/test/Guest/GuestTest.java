package Guest;

import Data.Data;
import Data.TestData;
import Domain.Cart;
import Domain.Guest;
import Domain.Subscribe;
import Domain.User;
import Stubs.CartStub;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuestTest {

    protected Guest guest;
    protected TestData data;
    protected Cart cart;

    @Before
    public void setUp(){
        data=new TestData();
        cart = new CartStub();
        guest=new Guest(cart);
    }

    /**
     * main test class for guest
     */
    @Test
    public void test(){
        logoutTest();
        loginTest();
        openStoreTest();
        addProductTest();
        addRequest();
    }

    /**
     * test use case 2.3 - Login
     */
    private void loginTest() {
        assertTrue(guest.login(new User(),new Subscribe("yuval","sabag")));
    }


    /**
     * use case 2.7 add to cart
     */
    public void testaddProductToCart() {
        //TODO
    }

    /**
     * use case - 2.8 buy cart
     */
    public void testbuyCart() {
        //TODO
    }

    /**
     * test use case 3.1 - Logout
     */
    private void logoutTest(){assertFalse(guest.logout(new User()));}

    /**
     * test use case 3.2 - Open Store
     */
    private void openStoreTest() {
        assertNull(guest.openStore(null, null, null));
    }

    private void addRequest() {assertNull(guest.addRequest("Store", "good store"));}


    /**
     * test use case 4.9.1 -add product
     */

    private  void addProductTest(){
        assertFalse(guest.addProductToStore(data.getProductData(Data.VALID)));
    }
}