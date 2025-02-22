package Guest;

import Data.Data;
import Data.TestData;
import DataAPI.*;
import Domain.*;
import Stubs.CartStub;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class GuestTest {

    protected Guest guest;
    protected TestData data;
    protected Cart cart;

    @Before
    public void setUp(){
        Utils.Utils.TestMode();
        data=new TestData();
        cart = new CartStub("Guest");
        guest=new Guest(cart);
    }

    /**
     * set up for Reserved
     */
    protected void setUpReserved() {
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        guest.addProductToCart(store,product,product.getAmount());
    }

    /**
     * set up for Buy and Cancel
     */
    protected void setUpBuy() {
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        guest.addProductToCart(store,product,product.getAmount());
        guest.reserveCart();
    }

    /**
     * set up for the save
     */
    protected void setUpSave() {
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        guest.addProductToCart(store,product,product.getAmount());
        guest.reserveCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        guest.buyCart(paymentData, deliveryData);
    }

    /**
     * test use case 2.3 - Login
     */
    @Test
    public void loginTest() {
        User user=new User();
        Subscribe sub=new Subscribe("yuval","sabag");
        assertTrue(guest.login(user,sub));
        assertSame(user.getState(), sub);
    }


    /**
     * use case 2.7 add to cart
     */
    @Test
    public void testAddProductToCart() {
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        assertTrue(guest.addProductToCart(store,product,product.getAmount()));
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Test
    public void testReservedCart() {
        setUpReserved();
        assertFalse(guest.reserveCart());
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Test
    public void testBuyCart() {
        setUpBuy();
        int size = 0;
        double sum =0;
        for(Basket b:cart.getBaskets().values()) {
            Map<String,ProductInCart> products = b.getProducts();
            for(ProductInCart p:products.values()) {
                int amount = p.getAmount();
                sum += amount * p.getPrice();
                size++;
            }
        }
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        guest.buyCart(paymentData,deliveryData);
        assertEquals(sum,paymentData.getTotalPrice(),0.001);
        assertEquals(size,deliveryData.getProducts().size());
    }

    /**
     * test use case 3.1 - Logout
     */
    @Test
    public void logoutTest(){assertFalse(guest.logout(new User()));}

    /**
     * test use case 3.2 - Open Store
     */
    @Test
    public void openStoreTest() {
        StoreData storeData = data.getStore(Data.WRONG_STORE);
        assertNull(guest.openStore(storeData));
    }

    /**
     * test use case 3.3 - write review
     */
    @Test
    public void testAddReview() {
        ProductData productData = data.getProductData(Data.VALID);
        assertFalse(guest.isItPurchased(productData.getStoreName(), productData.getProductName()));
        Review review = data.getReview(Data.VALID);
        assertFalse(guest.addReview(review));
    }

    /**
     * use case 3.5 - add request
     */
    @Test
    public void addRequest() {
        Request request = data.getRequest(Data.WRONG_STORE);
        assertNull(guest.addRequest(request.getStoreName(), request.getContent()));
    }

    /**
     * test use case 3.7 - watch purchases
     */
    @Test
    public void testWatchPurchases() {
        List<Purchase> list = guest.watchMyPurchaseHistory().getValue();
        assertNull(list);
    }

    /**
     * test use case 4.1.1 -add product
     */
    @Test
    public void testAddProductToStore(){
        assertFalse(guest.addProductToStore(data.getProductData(Data.VALID)).getValue());
    }

    /**
     * test use case 4.1.2
     */
    @Test
    public void testEditProductInStore() {
        assertFalse(guest.editProductFromStore(data.getProductData(Data.VALID)).getValue());
    }

    /**
     * test use case 4.1.3
     */
    @Test
    public void testRemoveProductInStore() {
        ProductData productData = data.getProductData(Data.VALID);
        assertFalse(guest.removeProductFromStore(productData.getStoreName(),productData.getProductName()).getValue());
    }

    /**
     * test use case 4.5
     */
    @Test
    public void testAddManager() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        assertFalse(guest.addManager(subscribe, storeData.getName()).getValue());
    }

    /**
     * test use case 4.6.1
     */
    @Test
    public void testAddPermission() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        assertFalse(guest.addPermissions(data.getPermissionTypeList(),storeData.getName(),subscribe.getName()).getValue());
    }

    /**
     * getManagersOfStoreUserManaged tests
     */
    @Test
    public void getManagersOfStoreUserManagedGuest(){
        assertNull(guest.getManagersOfStoreUserManaged(data.getStore(Data.VALID).getName()).getValue());
    }
}