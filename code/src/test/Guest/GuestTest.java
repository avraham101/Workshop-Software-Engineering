package Guest;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Stubs.CartStub;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

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
        testAddProductToStore();
        testEditProductInStore();
        testRemoveProductInStore();
        testAddProductToCart();
        testbuyCart();
        testWatchPurchases();
        addRequest();
        testAddReview();
        testAddManager();
        testAddPermission();
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
    public void testAddProductToCart() {
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        assertTrue(guest.addProductToCart(store,product,product.getAmount()));
    }

    /**
     * use case - 2.8 buy cart
     */
    public void testbuyCart() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(guest.buyCart(paymentData,deliveryData.getAddress()));
    }

    /**
     * test use case 3.1 - Logout
     */
    private void logoutTest(){assertFalse(guest.logout(new User()));}

    /**
     * test use case 3.2 - Open Store
     */
    private void openStoreTest() {
        StoreData storeData = data.getStore(Data.WRONG_STORE);
        assertNull(guest.openStore(storeData, null, null));
    }

    /**
     * test use case 3.3 - write review
     */
    private void testAddReview() {
        ProductData productData = data.getProductData(Data.VALID);
        assertFalse(guest.isItPurchased(productData.getStoreName(), productData.getProductName()));
        Review review = data.getReview(Data.VALID);
        assertFalse(guest.addReview(review));
    }

    /**
     * use case 3.5 - add request
     */
    private void addRequest() {
        Request request = data.getRequest(Data.WRONG_STORE);
        assertNull(guest.addRequest(request.getStoreName(), request.getContent()));
    }

    /**
     * test use case 3.7 - watch purchases
     */
    private void testWatchPurchases() {
        List<Purchase> list = guest.watchMyPurchaseHistory();
        assertNull(list);
    }

    /**
     * test use case 4.1.1 -add product
     */
    private  void testAddProductToStore(){
        assertFalse(guest.addProductToStore(data.getProductData(Data.VALID)));
    }

    /**
     * test use case 4.1.2
     */
    private void testEditProductInStore() {
        assertFalse(guest.editProductFromStore(data.getProductData(Data.VALID)));
    }

    /**
     * test use case 4.1.3
     */
    private void testRemoveProductInStore() {
        ProductData productData = data.getProductData(Data.VALID);
        assertFalse(guest.removeProductFromStore(productData.getStoreName(),productData.getProductName()));
    }

    /**
     * test use case 4.5
     */
    private void testAddManager() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        assertFalse(guest.addManager(subscribe, storeData.getName()));
    }

    /**
     * test use case 4.6.1
     */
    private void testAddPermission() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        assertFalse(guest.addPermissions(data.getPermissionTypeList(),storeData.getName(),subscribe.getName()));
    }
}