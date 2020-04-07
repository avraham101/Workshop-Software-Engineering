package LogicManagerTests;

import Data.*;
import DataAPI.CartData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Stubs.StoreStub;
import Stubs.SubscribeStub;
import Stubs.UserStub;
import Systems.HashSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//class for Unit test all stubs
import static org.junit.Assert.*;

public class LogicManagerAllStubsTest {

    protected LogicManager logicManager;
    protected User currUser;
    protected HashMap<String,Subscribe> users;
    protected HashMap<String,Store> stores;
    protected TestData data;

    /**
     * Adding Stores must be in type StoreStub
     * example: stores.put(Key,new StoreStub(...))
     * Adding Users must be in type UserStub
     * example: users.put(Key, new UserStub(...))
     */

    @Before
    public void setUp() {
        currUser=new UserStub();
        init();
        //make sure we are using SubscribeStub
        Subscribe dataSubscribe = data.getSubscribe(Data.ADMIN);
        Subscribe subscribe = users.get(dataSubscribe.getName());
        users.put(subscribe.getName(), new SubscribeStub(subscribe.getName(), subscribe.getPassword()));
    }

    protected void init() {
        data=new TestData();
        users=new HashMap<>();
        stores=new HashMap<>();
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        try {
            logicManager = new LogicManager(subscribe.getName(), subscribe.getPassword(), users, stores, currUser);
        } catch (Exception e) {
            fail();
        }
        Subscribe other=data.getSubscribe(Data.VALID2);
        logicManager.register(other.getName(),other.getPassword());
    }

    @Test
    public void test() {
        testExternalSystems();
        testRegister();
        testLogin();
        testOpenStore();
        testAddManagerToStore();
        testAddPermission();
        testAddProduct();
        testAddRequest();
        testStoreViewRequest();
        testReplayRequest();
        testViewSpecificProduct();
        testAddProductToCart();
        testWatchCartDetails();
        testEditProductsInCart();
        testBuyProducts();
        testWatchPurchaseHistory();
        testEditProduct();
        testWatchStoreHistory();
        testWatchUserHistory();
        testWriteReview();
        testViewDataStores();
        testViewProductsInStore();
        testAddProductToCart();
        testDeleteProductFromCart();
        testRemoveProductFromStore();
        testManageOwner();
        testRemovePermission();
        testRemoveManager();
        testLogout();
    }

    /**
     * test: use case 1.1 - Init System
     */
    private void testExternalSystems() {
        ProxyPayment proxyPayment = new ProxyPayment();
        assertTrue(proxyPayment.connect());
        ProxySupply proxySupply = new ProxySupply();
        assertTrue(proxySupply.connect());
        try {
            HashSystem hashSystem = new HashSystem();
            hashSystem.encrypt("testExternalSystems");
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * test: use case 2.2 - Register
     */
    public void testRegister() {
        testRegisterSuccess();
        testRegisterFailWrongName();
        testRegisterFailWrongPassword();
        testRegisterFailNull();
    }

    /**
     * part of test use case 2.2 - Register
     */
    public void testRegisterSuccess() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertTrue(logicManager.register(subscribe.getName(),subscribe.getPassword()));
    }

    /**
     * part of test use case 2.2 - Register
     */
    public void testRegisterFailWrongName() {
        Subscribe subscribe = data.getSubscribe(Data.WRONG_NAME);
        assertFalse(logicManager.register(subscribe.getName(),subscribe.getPassword()));
    }

    /**
     * part of test use case 2.2 - Register
     */
    public void testRegisterFailWrongPassword() {
        Subscribe subscribe = data.getSubscribe(Data.WRONG_PASSWORD);
        assertFalse(logicManager.register(subscribe.getName(), subscribe.getName()));
    }

    /**
     * part of test use case 2.2 - Register
     */
    public void testRegisterFailNull() {
        Subscribe subscribe = data.getSubscribe(Data.NULL);
        assertFalse(logicManager.register(subscribe.getName(), subscribe.getName()));
    }

    /**
     * test use case 2.3 - Login
     */
    public void testLogin() {
        testLoginFailNull();
        testLoginFailWrongName();
        testLoginFailWrongPassword();
        testLoginSuccess();
    }

    /**
     * part of use case 2.3 - Login
     */
    private void testLoginFailNull() {
        Subscribe subscribe = data.getSubscribe(Data.NULL);
        assertFalse(logicManager.login(subscribe.getName(), subscribe.getPassword()));
    }

    /**
     * part of use case 2.3 - Login
     */
    private void testLoginFailWrongName() {
        Subscribe subscribe = data.getSubscribe(Data.WRONG_NAME);
        assertFalse(logicManager.login(subscribe.getName(), subscribe.getPassword()));
    }

    /**
     * part of use case 2.3 - Login
     */
    private void testLoginFailWrongPassword() {
        Subscribe subscribe = data.getSubscribe(Data.WRONG_PASSWORD);
        assertFalse(logicManager.login(subscribe.getName(), subscribe.getPassword()));
    }

    /**
     * use case 2.4.1 - view all stores details
     */
    protected void testViewDataStores() {
        List<StoreData> expected = new LinkedList<>();
        expected.add(data.getStore(Data.VALID));
        assertEquals(expected, logicManager.viewStores());
        assertNotEquals(null, logicManager.viewStores());
    }

    /**
     * part of use case 2.3 - Login
     */
    protected void testLoginSuccess() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertTrue(logicManager.login(subscribe.getName(),subscribe.getPassword()));
    }


    /**
     * test: use case 3.1 - Logout
     */
    protected void testLogout() {
        assertTrue(currUser.logout());
    }

    /**
     * test use case 3.2 - Open Store
     */
    protected void testOpenStore() {
        testOpenStoreFail();
        testOpenStoreSucces();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    private void testOpenStoreFail() {
        assertFalse(logicManager.openStore(data.getStore(Data.NULL)));
        assertFalse(logicManager.openStore(data.getStore(Data.NULL_NAME)));
        assertFalse(logicManager.openStore(data.getStore(Data.NULL_PURCHASE)));
        assertFalse(logicManager.openStore(data.getStore(Data.NULL_DISCOUNT)));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    protected void testOpenStoreSucces(){
        StoreData storeData = data.getStore(Data.VALID);
        assertTrue(logicManager.openStore(storeData));
        Store store = stores.get(storeData.getName());
        Permission permission = new Permission(data.getSubscribe(Data.VALID));
        StoreStub storeStub = new StoreStub(store.getName(),store.getPurchesPolicy(),
                store.getDiscount(),permission,store.getSupplySystem(),
                store.getPaymentSystem());
        permission.setStore(storeStub);
        stores.put(storeData.getName(),storeStub);
    }

    /**
     * use case 3.5 -add request
     * ------
     * in this level we test the:
     * 1. enter null content
     * 2. enter request to invalid store
     */
    public void testAddRequest(){
        testAddRequestSuccess();
        testAddRequestFail();
    }

    private void testAddRequestSuccess() {
        Request request = data.getRequest(Data.VALID);
        assertNotNull(currUser.addRequest(request.getStoreName(),request.getContent()));
    }

    private void testAddRequestFail() {
        Request request1 = data.getRequest(Data.WRONG_STORE);
        Request request2 = data.getRequest(Data.NULL);
        assertFalse(logicManager.addRequest(request1.getStoreName(), request1.getContent()));
        assertFalse(logicManager.addRequest(request2.getStoreName(), request2.getContent()));
    }

    /**
     * use case 4.9.1 -view request
     */
    public void testStoreViewRequest(){
        testStoreViewRequestSuccess();
        testStoreViewRequestFail();
    }

    private void testStoreViewRequestSuccess() {
        Request request = data.getRequest(Data.VALID);
        assertFalse(currUser.viewRequest(request.getStoreName()).isEmpty());
    }

    private void testStoreViewRequestFail() {
        Request request1 = data.getRequest(Data.NULL_NAME);
        Request request2 = data.getRequest(Data.WRONG_STORE);
        assertTrue(logicManager.viewStoreRequest(request1.getStoreName()).isEmpty());
        assertTrue(logicManager.viewStoreRequest(request2.getStoreName()).isEmpty());
    }

    /**
     * use case 4.9.2 -replay request
     */
    public void testReplayRequest(){
        testReplayRequestSuccess();
        testReplayRequestFail();
    }

    private void testReplayRequestSuccess() {
        Request request = data.getRequest(Data.VALID);
        assertNotNull(currUser.replayToRequest(request.getStoreName(),request.getId(), request.getContent()));
    }

    private void testReplayRequestFail() {
        Request request1 = data.getRequest(Data.WRONG_STORE);
        Request request2 = data.getRequest(Data.NULL);
        assertNull(logicManager.replayRequest(request1.getStoreName(), request1.getId(), request1.getContent()));
        assertNull(logicManager.replayRequest(request2.getStoreName(), request2.getId(), request2.getContent()));
    }
    /**
     * use case 3.3 - write review
     */
    protected void testWriteReview() {
        testWriteReviewInvalid();
        testWriteReviewValid();
    }

    /**
     * part of use case 3.3 - write review
     */
    private void testWriteReviewInvalid() {
        Review review = data.getReview(Data.NULL_STORE);
        assertFalse(logicManager.addReview(review.getStore(),review.getProductName(),review.getContent()));

        review = data.getReview(Data.NULL_PRODUCT);
        assertFalse(logicManager.addReview(review.getStore(),review.getProductName(),review.getContent()));

        review = data.getReview(Data.NULL_CONTENT);
        assertFalse(logicManager.addReview(review.getStore(),review.getProductName(),review.getContent()));

        review = data.getReview(Data.EMPTY_CONTENT);
        assertFalse(logicManager.addReview(review.getStore(),review.getProductName(),review.getContent()));

        review = data.getReview(Data.WRONG_STORE);
        assertFalse(logicManager.addReview(review.getStore(),review.getProductName(),review.getContent()));

        review = data.getReview(Data.NULL_PRODUCT);
        assertFalse(logicManager.addReview(review.getStore(),review.getProductName(),review.getContent()));

    }

    /**
     * part of use case 3.3 - write review
     */
    protected void testWriteReviewValid() {
        Review review = data.getReview(Data.VALID);
        assertTrue(logicManager.addReview(review.getStore(),review.getProductName(),review.getContent()));
    }


    /**
     * use case 4.1.1 - add product
     */

    //TODO: Added
    protected void testAddProduct(){
        testAddProductFail();
        testProductSuccess();
    }

    protected void testProductSuccess() {
        assertTrue(logicManager.addProductToStore(data.getProductData(Data.VALID)));
    }

    protected void testAddProductFail(){
        assertFalse(logicManager.addProductToStore(null));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NULL_NAME)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.WRONG_STORE)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NULL_CATEGORY)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NULL_DISCOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NEGATIVE_AMOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NEGATIVE_PRICE)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NULL_PURCHASE)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.OVER_100_PERCENTAGE)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.WRONG_DISCOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NEGATIVE_PERCENTAGE)));
    }

    /**
     * use case 2.4.2 - view the products in some store test
     */
    protected void testViewProductsInStore() {
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        assertEquals(expected, logicManager.viewProductsInStore(storeName));

        expected.add(data.getProductData(Data.NULL_CATEGORY));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.remove(data.getProductData(Data.NULL_CATEGORY));

        expected.add(data.getProductData((Data.NULL_NAME)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.add(data.getProductData((Data.NULL_NAME)));

        expected.add(data.getProductData((Data.NULL_DISCOUNT)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.add(data.getProductData((Data.NULL_DISCOUNT)));

        expected.add(data.getProductData((Data.NULL_PURCHASE)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.add(data.getProductData((Data.NULL_PURCHASE)));
        expected.add(data.getProductData((Data.NULL_PURCHASE)));
    }

    /**
     * use case 2.5 - view specific product
     */
    protected void testViewSpecificProduct() {
        testViewSpecificProductWrongSearch();
        testViewSpecificProductWrongFilter();
        testViewSpecificProductSearchAndFilter();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    protected void testViewSpecificProductWrongSearch() {
        Filter filter = data.getFilter(Data.NULL_SEARCH);
        List<ProductData> products = logicManager.viewSpecificProducts(filter);
        assertTrue(products.isEmpty());
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    protected void testViewSpecificProductWrongFilter() {
        Filter filter = data.getFilter(Data.NULL);
        List<ProductData> products = logicManager.viewSpecificProducts(filter);
        assertTrue(products.isEmpty());

        filter = data.getFilter(Data.NULL_VALUE);
        products = logicManager.viewSpecificProducts(filter);
        assertTrue(products.isEmpty());

        filter = data.getFilter(Data.NEGATIVE_MIN);
        products = logicManager.viewSpecificProducts(filter);
        assertTrue(products.isEmpty());

        filter = data.getFilter(Data.NEGATIVE_MAX);
        products = logicManager.viewSpecificProducts(filter);
        assertTrue(products.isEmpty());

        filter = data.getFilter(Data.NULL_CATEGORY);
        products = logicManager.viewSpecificProducts(filter);
        assertTrue(products.isEmpty());
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    private void testViewSpecificProductSearchAndFilter() {
        Filter filter = data.getFilter(Data.VALID);
        filter.setSearch(Search.NONE);
        List<ProductData> products = logicManager.viewSpecificProducts(filter);
        assertNotNull(products);
        assertTrue(products.isEmpty());

        filter.setSearch(Search.PRODUCT_NAME);
        products = logicManager.viewSpecificProducts(filter);
        assertNotNull(products);
        assertTrue(products.isEmpty());

        filter.setSearch(Search.KEY_WORD);
        products = logicManager.viewSpecificProducts(filter);
        assertNotNull(products);
        assertTrue(products.isEmpty());

        filter.setSearch(Search.CATEGORY);
        products = logicManager.viewSpecificProducts(filter);
        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    /**
     * use case 4.1.2 -delete product
     */
    private void testRemoveProductFromStore(){
        testRemoveProductSuccess();
        testRemoveProductFail();
    }

    /**
     * test remove with no exist store
     */

    private void testRemoveProductFail() {
        assertFalse(logicManager.removeProductFromStore(data.getSubscribe(Data.VALID).getName()
                ,data.getProductData(Data.VALID).getProductName()));
    }

    protected void testRemoveProductSuccess() {
        ProductData p=data.getProductData(Data.VALID);
        assertTrue(logicManager.removeProductFromStore(p.getStoreName(),p.getProductName()));
    }

    /**
     * use case 2.7.1 fails tests
     */
    protected void testWatchCartDetails() {
        testWatchCartDetailsNull();
        testWatchCartDetailsNullStore();
    }

    /**
     * test use case 4.1.3 - edit product in store
     */
    protected void testEditProduct(){
        testEditProductFail();
        testEditProductSuccess();
    }

    protected void testEditProductSuccess() {
        assertTrue(logicManager.editProductFromStore(data.getProductData(Data.EDIT)));
    }

    protected void testEditProductFail() {
        assertFalse(logicManager.editProductFromStore(null));
        assertFalse(logicManager.editProductFromStore(data.getProductData(Data.NULL_NAME)));
        assertFalse(logicManager.editProductFromStore(data.getProductData(Data.WRONG_STORE)));
        assertFalse(logicManager.editProductFromStore(data.getProductData(Data.NULL_CATEGORY)));
        assertFalse(logicManager.editProductFromStore(data.getProductData(Data.NULL_DISCOUNT)));
        assertFalse(logicManager.editProductFromStore(data.getProductData(Data.NEGATIVE_AMOUNT)));
        assertFalse(logicManager.editProductFromStore(data.getProductData(Data.NEGATIVE_PRICE)));
        assertFalse(logicManager.editProductFromStore(data.getProductData(Data.NULL_PURCHASE)));
        assertFalse(logicManager.editProductFromStore(data.getProductData(Data.OVER_100_PERCENTAGE)));
        assertFalse(logicManager.editProductFromStore(data.getProductData(Data.WRONG_DISCOUNT)));
        assertFalse(logicManager.editProductFromStore(data.getProductData(Data.NEGATIVE_PERCENTAGE)));
    }


    /**
     * test : use case 4.3 add owner
     */
    private void testManageOwner(){
        testManageOwnerFail();
        testManageOwnerSuccess();
    }

    protected void testManageOwnerSuccess() {
        assertTrue(logicManager.manageOwner(data.getStore(Data.VALID).getName(),
                data.getSubscribe(Data.VALID2).getName()));
    }

    private void testManageOwnerFail() {
        assertFalse(logicManager.manageOwner(data.getStore(Data.VALID).getName()
                ,data.getStore(Data.VALID).getName()));
        assertFalse(logicManager.manageOwner(data.getSubscribe(Data.VALID).getName(),
                data.getSubscribe(Data.VALID2).getName()));
    }

    /**
     * use case 4.5 add manager
     */
    protected void testAddManagerToStore(){
        testAddManagerToStoreFail();
        testAddManagerStoreSuccess();
    }

    protected void testAddManagerStoreSuccess() {
        assertTrue(logicManager.addManager(data.getSubscribe(Data.ADMIN).getName(),data.getStore(Data.VALID).getName()));
    }

    /**
     * test not existing store or not existing user
     */
    private void testAddManagerToStoreFail() {
        String storeName=data.getStore(Data.VALID).getName();
        String userName=data.getSubscribe(Data.ADMIN).getName();
        //invalid username
        assertFalse(logicManager.addManager(storeName,storeName));
        //invalid storeName
        assertFalse(logicManager.addManager(userName,userName));
    }

    /**
     * test use case 4.6.1- add permission
     */
    private void testAddPermission(){
        testAddPermissionFail();
        testAddPermissionSuccess();
    }

    /**
     * test:
     * 1. wrong user name
     * 2. wrong store name
     * 3. null list
     * 4. list with null
     */
    private void testAddPermissionFail() {
        String user=data.getSubscribe(Data.ADMIN).getName();
        String store=data.getStore(Data.VALID).getName();
        List<PermissionType> types=data.getPermissionTypeList();
        assertFalse(logicManager.addPermissions(types,store,store));
        assertFalse(logicManager.addPermissions(types,user,user));
        assertFalse(logicManager.addPermissions(null,store,user));
        types.add(null);
        assertFalse(logicManager.addPermissions(types,store,user));
        types.remove(null);
    }

    protected void testAddPermissionSuccess() {
        assertTrue(currUser.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()));
    }

    /**
     * test use case 4.6.2 - remove permissions
     */
    public void testRemovePermission(){
        testRemovePermissionFail();
        testRemovePermissionSuccess();
    }

    /**
     * test use case 4.7 - remove manager
     */
    private void testRemoveManager(){
        testRemoveManagerFail();
        testRemoveManagerSuccess();
    }

    protected void testRemoveManagerSuccess() {
        assertTrue(logicManager.removeManager(data.getSubscribe(Data.ADMIN).getName(),data.getStore(Data.VALID).getName()));
    }

    /**
     * test not existing store or not existing user
     */
    private void testRemoveManagerFail() {
        String storeName=data.getStore(Data.VALID).getName();
        String userName=data.getSubscribe(Data.ADMIN).getName();
        //invalid username
        assertFalse(logicManager.removeManager(storeName,storeName));
        //invalid storeName
        assertFalse(logicManager.removeManager(userName,userName));
    }

    /**
     * test:
     * 1. wrong user name
     * 2. wrong store name
     * 3. null list
     * 4. list with null
     */
    private void testRemovePermissionFail() {
        String user=data.getSubscribe(Data.ADMIN).getName();
        String store=data.getStore(Data.VALID).getName();
        List<PermissionType> types=data.getPermissionTypeList();
        assertFalse(logicManager.removePermissions(types,store,store));
        assertFalse(logicManager.removePermissions(types,user,user));
        assertFalse(logicManager.removePermissions(null,store,user));
        types.add(null);
        assertFalse(logicManager.removePermissions(types,store,user));
        types.remove(null);
    }

    protected void testRemovePermissionSuccess() {
        assertTrue(currUser.removePermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()));
    }

    /**
     * use case 6.4.1 - watch User History
     */
    private void testWatchUserHistory(){
        testWatchUserHistoryUserNotExist();
        testWatchUserHistorySuccess();
    }

    /**
     * test user that not exist on users map
     */
    private void testWatchUserHistoryUserNotExist() {
        assertNull(logicManager.watchUserPurchasesHistory(data.getStore(Data.VALID).getName()));
    }

    /**
     * test success
     */
    protected void testWatchUserHistorySuccess() {
        assertNotNull(logicManager.watchUserPurchasesHistory(data.getSubscribe(Data.VALID).getName()));
    }

    /**
     * use case 6.4.2 , 4.10 - watch store history
     */
    private void testWatchStoreHistory(){
        testWatchStoreHistoryStoreNotExist();
        testWatchStoreHistorySuccess();
    }

    /**
     * test store that not exist on users map
     */
    private void testWatchStoreHistoryStoreNotExist() {
        assertNull(logicManager.watchStorePurchasesHistory(data.getSubscribe(Data.VALID).getName()));
    }

    /**
     * test success
     */
    protected void testWatchStoreHistorySuccess() {
        assertNotNull(logicManager.watchStorePurchasesHistory(data.getStore(Data.VALID).getName()));
    }

    /**
     * use case 2.7.1 fail when the product is null
     */
    private void testWatchCartDetailsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        CartData cartData = logicManager.watchCartDetatils();
        assertFalse(cartData.getProducts().contains(productData));
    }

    /**
     * use case 2.7.1 fail when the basket is null
     */
    private void testWatchCartDetailsNullStore() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        CartData cartData = logicManager.watchCartDetatils();
        assertFalse(cartData.getProducts().contains(productData));
    }


    /**
     * use case 2.7.2 delete product from cart
     * fails tests
     */
    protected void testDeleteProductFromCart() {
        testDeleteProductFromCartBasketIsNull();
        testDeleteProductFromCartProductIsNull();
    }

    /**
     * use case 2.7.2 delete product from cart
     * fails test - product is null
     */
    private void testDeleteProductFromCartProductIsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        assertFalse(logicManager.deleteFromCart(productData.getProductName(),productData.getStoreName()));
    }

    /**
     * use case 2.7.2 delete product from cart
     * fails test - basket is null
     */
    private void testDeleteProductFromCartBasketIsNull() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.deleteFromCart(productData.getProductName(),productData.getStoreName()));
    }

    /**
     * use case 2.7.3 fail tests
     */
    protected void testEditProductsInCart() {
        testEditProductsInCartBasketIsNull();
        testEditProductsInCartNegativeAmount();
        testEditProductsInCartProductIsNull();
    }

    /**
     * use case 2.7.3 fail test when the product is null
     */
    private void testEditProductsInCartProductIsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        assertFalse(logicManager.editProductInCart(productData.getProductName(),productData.getStoreName(),productData.getAmount()));
    }

    /**
     * use case 2.7.3 fail test when the amount is negative
     */
    private void testEditProductsInCartNegativeAmount() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.editProductInCart(productData.getProductName(),productData.getStoreName(),productData.getAmount()));
    }

    /**
     * use case 2.7.3 fail test when the basket is null
     */
    private void testEditProductsInCartBasketIsNull() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.editProductInCart(productData.getProductName(),productData.getStoreName(),productData.getAmount() + 1));
    }


    /**
     *  use case 2.7.4 - add product to cart
     */
    protected void testAddProductToCart() {
        testAddProductToCartInvalidStore();
    }

    /**
     * part of use case 2.7.4 - add product to cart
     */
    private void testAddProductToCartInvalidStore() {
        ProductData product = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.aadProductToCart(product.getProductName(),product.getStoreName(),product.getAmount()));
    }

    /**
     * use case 2.8 - test buy Products
     */
    protected void testBuyProducts() {
        testFailBuyProducts();
        testSuccessBuyProducts();
    }

    /**
     * use case 2.8 - test buy Products
     * success tests
     */
     private void testSuccessBuyProducts() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        assertTrue(logicManager.purchaseCart(paymentData, address));
    }

    /**
     * use case 2.8 - test buy Products
     * fails tests
     */
    private void testFailBuyProducts() {
        // null data payment
        assertFalse(logicManager.purchaseCart(null, null));
        // null address in payment
        PaymentData paymentData = data.getPaymentData(Data.NULL_ADDRESS);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(paymentData, address));
        // empty address in payment
        paymentData = data.getPaymentData(Data.EMPTY_ADDRESS);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(paymentData, address));
        // null payment
        paymentData = data.getPaymentData(Data.NULL_PAYMENT);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(paymentData, address));
        // empty payment
        paymentData = data.getPaymentData(Data.EMPTY_PAYMENT);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(paymentData, address));
        // null name in payment
        paymentData = data.getPaymentData(Data.NULL_NAME);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(paymentData, address));
        // empty name in payment
        paymentData = data.getPaymentData(Data.EMPTY_NAME);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(paymentData, address));
        // null address
        paymentData = data.getPaymentData(Data.VALID);
        address = data.getDeliveryData(Data.NULL_ADDRESS).getAddress();
        assertFalse(logicManager.purchaseCart(paymentData, address));
        // empty address
        paymentData = data.getPaymentData(Data.VALID);
        address = data.getDeliveryData(Data.EMPTY_ADDRESS).getAddress();
        assertFalse(logicManager.purchaseCart(paymentData, address));
    }

    /**
     * use case 3.7 - watch purchase history
     */
    public void testWatchPurchaseHistory() {
        List<Purchase> purchases = logicManager.watchMyPurchaseHistory();
        assertNotNull(purchases);
        assertTrue(purchases.isEmpty());
    }

}