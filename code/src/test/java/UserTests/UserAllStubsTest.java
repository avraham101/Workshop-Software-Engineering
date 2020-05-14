package UserTests;

import Data.Data;
import Data.TestData;
import DataAPI.*;
import Domain.*;
import Stubs.AdminStub;
import Stubs.GuestStub;
import Stubs.SubscribeStub;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

//class for Unit test all stubs
//class for Unit test all stubs


public class UserAllStubsTest {

    protected User user;
    protected UserState userState;
    protected TestData data;

    @Before
    public void setUp() {
        data =new TestData();
    }

    /**--------------------------------set-ups-------------------------------------------------------------------*/

    /**
     * init guest state
     */
    protected void setUpGuest() {
        userState = new GuestStub();
        user = new User(userState);
    }

    /**
     * init subscribe state
     */
    protected void setUpSubscribe() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        userState = new SubscribeStub(subscribe.getName(), subscribe.getPassword());
        user = new User(userState);
    }

    /**
     * init admin state
     */
    protected void setUpAdmin(){
        Subscribe s=data.getSubscribe(Data.ADMIN);
        userState = new AdminStub(s.getName(), s.getPassword());
        user = new User(userState);
    }

    /**
     * set up a store for the subscribed user
     */
    private void setUpOpenStore(){
        setUpSubscribe();
        StoreData storeData = new StoreData("Store", "description");
        user.openStore(storeData);
    }

    /**
     * set up add manager for the store of subscribed user
     */
    protected void setUpAddedManager(){
        setUpOpenStore();
        user.addManager(data.getSubscribe(Data.ADMIN), data.getStore(Data.VALID).getName());
    }

    /**
     * set up add manager  for the store of subscribed user and add to him permissions
     */
    private void setUpPermissionAdded(){
        setUpAddedManager();
        user.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName());
    }

    /**
     * set up add product to a store
     */
    protected void setUpProductAdded(){
        setUpOpenStore();
        user.addProductToStore(data.getProductData(Data.VALID));
    }

    /**
     * set up add discount to a store
     */
    private void setUpDiscountAdded() {
        setUpProductAdded();
        user.addDiscountToStore(data.getStore(Data.VALID).getName(),
                data.getDiscounts(Data.VALID).get(0));
    }

    /**
     * set up product added to cart
     */
    protected void setUpProductAddedToCart(){
        setUpProductAdded();
        Store store = data.getRealStore(Data.VALID);
        Product p = data.getRealProduct(Data.VALID);
        user.addProductToCart(store,p,p.getAmount());
    }



    /**
     * set up the subscribe reserved Cart
     */
    protected void setUpReservedCart() {
        setUpProductAddedToCart();
        user.reservedCart();
    }

    /**
     * set up the subscribe reserved Cart
     */
    protected void setUpBuyCart() {
        setUpReservedCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        user.buyCart(paymentData, deliveryData);
    }

    protected void setUpBougtAndSaved(){
        setUpBuyCart();
        user.savePurchase(user.getUserName());
    }

    /**--------------------------------set-ups-------------------------------------------------------------------*/

    /**
     * test use case 2.3 - Login
     */
    @Test
    public void testLoginGuest() {
        setUpGuest();
        Subscribe sub=data.getSubscribe(Data.VALID);
        assertTrue(user.login(sub));
    }

    /**
     * test use case 2.3 - Login
     */
    @Test
    public void testLoginSubscribe() {
        setUpSubscribe();
        assertFalse(user.login(data.getSubscribe(Data.VALID)));
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
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testReservedCart() {
        setUpProductAddedToCart();
        assertTrue(user.reservedCart());
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testSavePurchase() {
        setUpBuyCart();
        String name = data.getSubscribe(Data.VALID).getName();
        user.savePurchase(name);
        assertEquals(0, user.watchMyPurchaseHistory().getValue().size());
    }

    /**
     * test: use case 3.1 - Logout
     */
    @Test
    public void testLogoutGuest(){
        setUpGuest();
        assertFalse(user.logout());
    }

    /**
     * test: use case 3.1 - Logout
     */
    @Test
    public void testLogoutSubscribe(){
        setUpSubscribe();
        assertTrue(user.logout());
    }

    /**
     * test: use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreGuest() {
        setUpGuest();
        StoreData storeData = new StoreData("Store","description");
        assertNull(user.openStore(storeData));
    }

    /**
     * test: use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreSubscribe() {
        setUpSubscribe();
        StoreData storeData = new StoreData("Store", "description");
        Store store = user.openStore(storeData);
        assertEquals(storeData.getName(), store.getName());
    }

    /**
     * use case 3.3 - write review
     */
    @Test
    public void testWriteReviewGuest() {
        setUpGuest();
        Review review = data.getReview(Data.VALID);
        assertFalse(user.addReview(review));
    }

    /**
     * use case 3.3 - write review
     */
    @Test
    public void testWriteReviewSubscribe() {
        setUpReservedCart();
        Review review = data.getReview(Data.VALID);
        assertTrue(user.addReview(review));
    }

    /**
     * test use case 3.5 - add request
     */
    @Test
    public void testAddRequestGuest() {
        setUpGuest();
        Request request = data.getRequest(Data.VALID);
        assertNull(user.addRequest(request.getId(),request.getStoreName(),request.getComment())); }

    /**
     * test use case 3.5 - add request
     */
    @Test
    public void testAddRequestSubscribe() {
        setUpOpenStore();
        Request request = data.getRequest(Data.VALID);
        assertNotNull(user.addRequest(request.getId(),request.getStoreName(),request.getComment())); }

        /**
     * test use case 3.7 - watch purchases
     */
    @Test
    public void testWatchPurchasesGuest() {
        setUpGuest();
        List<Purchase> list = user.watchMyPurchaseHistory().getValue();
        assertNull(list);
    }

    /**
     * test use case 3.7 - watch purchases
     */
    @Test
    public void testWatchPurchasesSubscribe() {
       setUpBougtAndSaved();
        List<Purchase> list = user.watchMyPurchaseHistory().getValue();
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    /**
     * test: use case 4.1.1 -add product to store
     * guest can't add product
     */
    @Test
    public void testAddProductToStoreGuest(){
        setUpGuest();
        assertFalse(user.addProductToStore(data.getProductData(Data.VALID)).getValue());
    }

    /**
     *test: use case 4.1.1 - add product to store in subscribe state
     */
    @Test
    public void testAddProductToStoreSubscribe(){
        setUpOpenStore();
        assertTrue(user.addProductToStore(data.getProductData(Data.VALID)).getValue());
    }

    /**
     * test : use case 4.1.2 -remove product to store
     * guest can't do it
     */
    @Test
    public void testRemoveProductFromStoreGuest(){
        setUpGuest();
        ProductData product= data.getProductData(Data.VALID);
        assertFalse(user.removeProductFromStore(product.getStoreName(),product.getProductName()).getValue());
    }

    /**
     * test : use case 4.1.2 -remove product to store
     * guest can't do it
     */
    @Test
    public void testRemoveProductFromStoreSubscribe(){
        setUpProductAdded();
        ProductData product= data.getProductData(Data.VALID);
        assertTrue(user.removeProductFromStore(product.getStoreName(),product.getProductName()).getValue());
    }

    /**
     * test use case 4.1.3 - edit product from store
     */
    @Test
    public void testEditProductFromStoreGuest(){
        setUpGuest();
        assertFalse(user.editProductFromStore(data.getProductData(Data.EDIT)).getValue());
    }

    /**
     * test use case 4.1.3 - edit product from store
     */
    @Test
    public void testEditProductFromStoreSubscribe() {
        setUpProductAdded();
        assertTrue(user.editProductFromStore(data.getProductData(Data.EDIT)).getValue());
    }


    /**
     * use case 4.2.1.1 -add product to store
     */
    @Test
    public void testAddDiscountToStoreSuccessSubscribe(){
        setUpProductAdded();
        assertTrue(user.addDiscountToStore(data.getStore(Data.VALID).getName(),
                data.getDiscounts(Data.VALID).get(0)).getValue());
    }


    /**
     * use case 4.2.1.1 -add product to store
     */
    @Test
    public void testAddDiscountToStoreFailGuest(){
        setUpGuest();
        assertFalse(user.addDiscountToStore(data.getStore(Data.VALID).getName(),
                data.getDiscounts(Data.VALID).get(0)).getValue());
    }

    /**
     * use case 4.2.1.2 -remove product from store
     */
    @Test
    public void testDeleteDiscountFromStoreSuccessSubscribe(){
        setUpDiscountAdded();
        assertTrue(user.deleteDiscountFromStore(0,data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * use case 4.2.1.2 -remove product from store
     */
    @Test
    public void testDeleteDiscountFromStoreFailGuest(){
        setUpGuest();
        assertFalse(user.deleteDiscountFromStore(0,data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * test use case 4.5 -addManager
     */
    @Test
    public void testAddManagerGuest(){
        setUpGuest();
        assertFalse(user.addManager(data.getSubscribe(Data.ADMIN), data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * test add manager subscribed
     */
    @Test
    public void testAddManagerSubscribe(){
        setUpOpenStore();
        assertTrue(user.addManager(data.getSubscribe(Data.ADMIN), data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * test se case 4.6.1 - add permissions
     */
    @Test
    public void testAddPermissionsGuest(){
        setUpGuest();
        assertFalse(user.addPermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(), data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * test se case 4.6.1 - add permissions
     */
    @Test
    public void testAddPermissionsSubscribe(){
        setUpAddedManager();
        assertTrue(user.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()).getValue());
    }

    /**
     * test use case 4.6.2 - remove permissions
     */
    @Test
    public void testRemovePermissionsGuest(){
        setUpGuest();
        assertFalse(user.removePermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(), data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * test use case 4.6.2 - remove permissions
     */
    @Test
    public void testRemovePermissionsSubscribe(){
        setUpPermissionAdded();
        assertTrue(user.removePermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()).getValue());
    }

    /**
     * test use case 4.7 -remove manager
     */
    @Test
    public void testRemoveManagerGuest(){
        setUpGuest();
        assertFalse(user.removeManager(data.getSubscribe(Data.ADMIN).getName(), data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * test use case 4.7 -remove manager
     */
    @Test
    public void testRemoveManagerSubscribe(){
        setUpAddedManager();
        assertTrue(user.removeManager(data.getSubscribe(Data.ADMIN).getName(), data.getStore(Data.VALID).getName()).getValue());
    }


    /**
     * test use case 4.9.1 - view request
     */
    @Test
    public void testViewRequestGuest() {
        setUpGuest();
        assertTrue(user.viewRequest(data.getStore(Data.VALID).getName()).isEmpty());
    }

    /**
     * test use case 4.9.2 - reply request
     */
    @Test
    public void testReplayRequestGuest(){
        setUpGuest();
        assertNull(user.replayToRequest(data.getRequest(Data.VALID).getStoreName()
            , data.getRequest(Data.VALID).getId(), "I want replay but can't").getValue());}

    /**
     * use case 6.4.1 - watch user store
     */
    @Test
    public void testCanWatchUserHistoryGuest(){
        setUpGuest();
        assertFalse(user.canWatchUserHistory());
    }

    /**
     * use case 6.4.1 - admin watch user store
     */
    @Test
    public void testCanWatchUserHistorySubscribe() {
        setUpSubscribe();
        assertFalse(user.canWatchUserHistory());
    }

    /**
     * use case 6.4.1 - admin watch user store
     */
    @Test
    public void testCanWatchUserHistoryAdmin(){
        setUpAdmin();
        assertTrue(user.canWatchUserHistory());
    }

    /**
     * use case 6.4.2 ,4.10- watch store
     */
    @Test
    public void testCanWatchStoreHistoryGuest(){
        setUpGuest();
        assertFalse(user.canWatchStoreHistory(data.getStore(Data.VALID).getName()));
    }

    /**
     * use case 6.4.2 - watch store
     */
    @Test
    public void testCanWatchStoreHistorySubscribe(){
        setUpOpenStore();
        assertTrue(user.canWatchStoreHistory(data.getStore(Data.VALID).getName()));
    }

    /**
     * use case 6.4.2 - admin watch store
     */
    @Test
    public void testCanWatchStoreHistoryAdmin(){
        setUpAdmin();
        assertTrue(user.canWatchStoreHistory(data.getStore(Data.VALID).getName()));
    }

}
