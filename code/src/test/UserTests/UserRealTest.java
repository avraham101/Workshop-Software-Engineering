package UserTests;

import Data.*;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.*;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class UserRealTest extends UserAllStubsTest{
    @Before
    public void setUp(){
        super.setUp();
    }

    @Override
    protected void setUpGuest() {
        userState = new Guest();
        user = new User(userState);
    }

    /**
     * set up to subscribe state
     */
    @Override
    protected void setUpSubscribe(){
        setUpGuest();
        user.login(data.getSubscribe(Data.VALID));
    }

    /**
     * set up to reserved to cart
     */
    public void setUpReserved() {
        setUpProductAdded();
        Store store = data.getRealStore(Data.VALID);
        Product p = data.getRealProduct(Data.VALID);
        user.addProductToCart(store,p,p.getAmount());
    }

    /**
     * test use case 2.3 - Login
     * user: Yuval Sabag
     */
    @Override @Test
    public void testLoginGuest(){
        super.testLoginGuest();
        assertEquals(user.getPassword(), data.getSubscribe(Data.VALID).getPassword());
        assertEquals(user.getUserName(), data.getSubscribe(Data.VALID).getName());
    }

    /**
     * use case 2.7 - add product to cart
     */
    @Test
    public void testAddProductToCart() {
        setUpProductAdded();
        Store store = data.getRealStore(Data.VALID);
        Product p = data.getRealProduct(Data.VALID);
        assertTrue(user.addProductToCart(store,p,p.getAmount()));
        assertTrue(user.getState().getCart().getBasket(store.getName()).getProducts().containsKey(p));
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testBuyCart() {
        setUpReservedCart();
        Cart cart = user.getState().getCart();
        int size = 0;
        double sum =0;
        for(Basket b:cart.getBaskets().values()) {
            HashMap<Product,Integer> products = b.getProducts();
            for(Product p:products.keySet()) {
                int amount = products.get(p);
                sum += amount * p.getPrice();
                size++;
            }
        }
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertTrue(user.buyCart(paymentData,deliveryData));
        assertEquals(sum,paymentData.getTotalPrice(),0.001);
        assertEquals(size,deliveryData.getProducts().size());
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testBuyCartFail(){
        setUpReservedCart();
        Cart cart = user.getState().getCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        cart.getBaskets().get(data.getStore(Data.VALID).getName()).getStore().setPurchasePolicy(
                new BasketPurchasePolicy(0));
        assertFalse(user.buyCart(paymentData,deliveryData));
        assertEquals(0,paymentData.getTotalPrice(),0.001);
        assertTrue(deliveryData.getProducts().isEmpty());

    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testSavePurchase() {
        setUpReservedCart();
        Store store = null;
        int storeExpected = 0;
        for(Basket basket: user.getState().getCart().getBaskets().values()) {
            store = basket.getStore();
            storeExpected = store.getPurchases().size() + 1;
            break;
        }
        int number =  user.getState().getCart().getBaskets().keySet().size();
        String name = data.getSubscribe(Data.VALID).getName();
        user.savePurchase(name);
        assertEquals(number, user.watchMyPurchaseHistory().getValue().size());
        assertEquals(storeExpected, store.getPurchases().size());
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testCancel() {
        setUpProductAddedToCart();
        int expected = amountProductInStore();
        user.reservedCart();
        user.cancelCart();
        int result = amountProductInStore();
        assertEquals(expected,result);
    }

    /**
     * use case 2.8
     * help function for getting the amount
     * @return
     */
    private int amountProductInStore() {
        Cart cart = user.getState().getCart();
        Store store = null;
        for(Basket b: cart.getBaskets().values()) {
            store = b.getStore();
            break;
        }
        assertNotNull(store);
        Product product = null;
        for(Product p :store.getProducts().values()) {
            product = p;
            break;
        }
        assertNotNull(product);
        return product.getAmount();
    }



    /**
     * use case 3.1 - logout
     */
    @Override @Test
    public void testLogoutSubscribe() {
        setUpSubscribe();
        Subscribe sub= (Subscribe) user.getState();
        super.testLogoutSubscribe();
        assertEquals(sub.getSessionNumber().get(),-1);
        assertTrue(user.getState() instanceof Guest);
    }

    /**
     * use case 3.3 - write review
     */
    @Override @Test
    public void testWriteReviewSubscribe() {
        setUpBougtAndSaved();
        Review review = data.getReview(Data.VALID);
        assertTrue(user.addReview(review));
        List<Review> reviewList = user.getState().getReviews();
        assertEquals(1, reviewList.size());
        assertEquals(review, reviewList.get(0));
    }

    /**
     * use case 3.3 - write review
     */
    @Test
    public void testWriteWrongReviewSubscribe(){
        setUpReserved();
        Review review = data.getReview(Data.WRONG_PRODUCT);
        assertFalse(user.addReview(review));
    }

    /**
     * test use case 3.7 - watch purchases
     */
    @Override @Test
    public void testWatchPurchasesSubscribe() {
        setUpBougtAndSaved();
        List<Purchase> list = user.watchMyPurchaseHistory().getValue();
        assertNotNull(list);
        assertEquals(1, list.size());
    }

    /**
     * test 4.1.1 use case -add product
     */
    @Override @Test
    public void testAddProductToStoreSubscribe() {
        super.testAddProductToStoreSubscribe();
        Product product=((Subscribe) user.getState()).getPermissions().get(data.getStore(Data.VALID).getName()).
                getStore().getProducts().get(data.getProductData(Data.VALID).getProductName());
        assertTrue(product.equal(data.getProductData(Data.VALID)));
    }

    /**
     * test 4.1.2 use case - remove product
     */
    @Override @Test
    public void testRemoveProductFromStoreSubscribe() {
        super.testRemoveProductFromStoreSubscribe();
        Subscribe sub=(Subscribe)user.getState();
        assertFalse(sub.getPermissions().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.3 -edit product
     */
    @Override @Test
    public void testEditProductFromStoreSubscribe() {
        super.testEditProductFromStoreSubscribe();
        ProductData product= data.getProductData(Data.EDIT);
        Subscribe sub=(Subscribe) user.getState();
        assertTrue(sub.getPermissions().get(product.getStoreName()).getStore()
                .getProducts().get(product.getProductName()).equal(product));
    }

    /**
     * use case 4.2.1.1 -add product to store
     */
    @Test @Override
    public void testAddDiscountToStoreSuccessSubscribe(){
        super.testAddDiscountToStoreSuccessSubscribe();
        Subscribe sub=(Subscribe) user.getState();
        Store store=sub.getPermissions().get(data.getStore(Data.VALID).getName()).getStore();
        assertEquals(data.getDiscounts(Data.VALID).get(0),store.getDiscount().get(0));
    }

    /**
     * use case 4.2.1.2 -remove product from store
     */
    @Test @Override
    public void testDeleteDiscountFromStoreSuccessSubscribe(){
        super.testDeleteDiscountFromStoreSuccessSubscribe();
        Subscribe sub=(Subscribe) user.getState();
        Store store=sub.getPermissions().get(data.getStore(Data.VALID).getName()).getStore();
        assertTrue(store.getDiscount().isEmpty());
    }

    /**
     * use case 4.5 - add manager
     */
    @Override @Test
    public void testAddManagerSubscribe() {
        super.testAddManagerSubscribe();
        Subscribe sub=(Subscribe) user.getState();
        assertTrue(sub.getGivenByMePermissions().get(0).getStore().getPermissions()
                .containsKey(data.getSubscribe(Data.ADMIN).getName()));
        Store store=sub.getGivenByMePermissions().get(0).getStore();
        Subscribe newManager=data.getSubscribe(Data.ADMIN);
        Permission p=store.getPermissions().get(newManager.getName());
        assertNotNull(p);
        assertEquals(p.getStore().getName(),store.getName());
        newManager=p.getOwner();
        assertNotNull(newManager);
        assertTrue(newManager.getPermissions().containsKey(store.getName()));
    }

    /**
     * 4.6.1 - add permission
     */
    @Override @Test
    public void testAddPermissionsSubscribe() {
        super.testAddPermissionsSubscribe();
        Subscribe sub=(Subscribe) user.getState();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType()
                .containsAll(data.getPermissionTypeList()));
    }

    /**
     * check use case 4.6.2 - remove permissions
     */
    @Override @Test
    public void testRemovePermissionsSubscribe() {
        super.testRemovePermissionsSubscribe();
        Subscribe sub=(Subscribe) user.getState();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType().
                isEmpty());
    }

    /**
     * test use case 4.7 - remove manager
     * make user admin manage user niv(VALID2)
     * remove Admin from being manager and check that niv was removed from being a manager recursively
     */
    @Override @Test
    public void testRemoveManagerSubscribe() {
        setUpAddedManager();
        Subscribe sub=(Subscribe) user.getState();
        Permission p=sub.getGivenByMePermissions().get(0);
        Subscribe niv=data.getSubscribe(Data.VALID2);
        String storeName=p.getStore().getName();
        //add another manager
        p.getOwner().addManager(niv,storeName);
        assertTrue(user.removeManager(data.getSubscribe(Data.ADMIN).getName(), data.getStore(Data.VALID).getName()).getValue());
        assertFalse(niv.getPermissions().containsKey(storeName));
        assertFalse(p.getOwner().getPermissions().containsKey(storeName));
    }
}
