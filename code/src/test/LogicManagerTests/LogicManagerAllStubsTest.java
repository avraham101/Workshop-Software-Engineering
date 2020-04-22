package LogicManagerTests;

import Data.*;
import DataAPI.*;
import Domain.*;
import Stubs.*;
import Systems.HashSystem;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

//class for Unit test all stubs
import static org.junit.Assert.*;

public class LogicManagerAllStubsTest {

    protected LogicManager logicManager;
    protected User currUser;
    protected ConcurrentHashMap<Integer,User> connectedUsers;
    protected ConcurrentHashMap<String,Subscribe> users;
    protected ConcurrentHashMap<String,Store> stores;
    protected PaymentSystem paymentSystem;
    protected SupplySystem supplySystem;
    protected TestData data;

    /**
     * Adding Stores must be in type StoreStub
     * example: stores.put(Key,new StoreStub(...))
     * Adding Users must be in type UserStub
     * example: users.put(Key, new UserStub(...))
     */

    @Before
    public void setUp() {
        init();
        //make sure we are using SubscribeStub
        Subscribe dataSubscribe = data.getSubscribe(Data.ADMIN);
        Subscribe subscribe = users.get(dataSubscribe.getName());
        users.put(subscribe.getName(), new SubscribeStub(subscribe.getName(), subscribe.getPassword()));
    }

    protected void init() {
        data=new TestData();
        users=new ConcurrentHashMap<>();
        stores=new ConcurrentHashMap<>();
        connectedUsers =new ConcurrentHashMap<>();
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        supplySystem=new ProxySupply();
        paymentSystem=new ProxyPayment();
        try {
            logicManager = new LogicManager(subscribe.getName(), subscribe.getPassword(), users, stores,
                    connectedUsers,paymentSystem,supplySystem);
        } catch (Exception e) {
            fail();
        }
        Subscribe other=data.getSubscribe(Data.VALID2);
        logicManager.register(other.getName(),other.getPassword());
    }

    /**--------------------------------set-ups-------------------------------------------------------------------*/

    /**
     * set up connect
     */
    protected void setUpConnect(){
        logicManager.connectToSystem();
        logicManager.connectToSystem();
        logicManager.connectToSystem();
        //work with the regular user has current user
        connectedUsers.put(data.getId(Data.VALID),new UserStub());
        connectedUsers.put(data.getId(Data.ADMIN),new UserStub());
        connectedUsers.put(data.getId(Data.VALID2),new UserStub());
        currUser=connectedUsers.get(data.getId(Data.VALID));
    }

    /**
     * set up for register a user
     */
    private void setUpRegisteredUser(){
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        logicManager.register(subscribe.getName(),subscribe.getPassword());
    }

    /**
     * set up for valid user to be login and registered
     */
    protected void setUpLogedInUser(){
        setUpRegisteredUser();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        logicManager.login(data.getId(Data.VALID), subscribe.getName(),subscribe.getPassword());
    }

    /**
     * set up for opening a valid store
     */
    protected void setUpOpenedStore(){
        setUpLogedInUser();
        StoreData storeData = data.getStore(Data.VALID);
        logicManager.openStore(data.getId(Data.VALID), storeData);
        Store store = stores.get(storeData.getName());
        Permission permission = new Permission(data.getSubscribe(Data.VALID));
        StoreStub storeStub = new StoreStub(store.getName(),store.getPurchasePolicy(),
                store.getDiscount(),permission);
        permission.setStore(storeStub);
        stores.put(storeData.getName(),storeStub);
    }

    /**
     * set up for adding a new manager, sub manager of the connected user
     */
    private void setUpManagerAdded(){
        setUpOpenedStore();
        logicManager.addManager(data.getId(Data.VALID),data.getSubscribe(Data.ADMIN).getName(),data.getStore(Data.VALID).getName());
    }

    /**
     * set up manager added and make the new manager adding another manager
     * set up to check use case 4.7 that the mangers will be removed and all the managers he managed
     * will be removed as well
     */
    protected void setUpManagerAddedSubManagerAdded(){
        setUpPermissionsAdded();
    }

    /**
     * set up a sub manager of current logged in user with permissions of ADD_MANAGER and ADD_OWNER
     */
    protected void setUpPermissionsAdded(){
        setUpManagerAdded();
        currUser.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName());
    }

    /**
     * set up for the store to be opened and a valid product to be added
     */
    protected void setUpProductAdded(){
        setUpOpenedStore();
        logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.VALID));
    }

    /**
     * set up for a state where a valid request was added for a valid store
     */
    private void setUpRequestAdded(){
        setUpOpenedStore();
        Request request = data.getRequest(Data.VALID);
        logicManager.addRequest(data.getId(Data.VALID),request.getStoreName(),request.getContent());
    }

    /**
     * set up a valid product in a valid cart
     * //extended by the tests of LogicManageRealTests
     */
    protected void setUpProductAddedToCart(){
        setUpProductAdded();
    }

    /**
     * set up a user and store with a valid non empty purchase history
     */
    protected void setUpBoughtProduct(){
        setUpProductAddedToCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        logicManager.purchaseCart(data.getId(Data.VALID), paymentData, address);
    }

    /**
     * set up bought products and go to admin state for getting admin permissions
     */
    protected void setUpBoughtProductAdminState(){
        setUpBoughtProduct();
        String adminName=data.getSubscribe(Data.ADMIN).getName();
        currUser.setState(users.get(adminName));
    }


    /**--------------------------------set-ups-------------------------------------------------------------------*/

    /**
     * test: use case 1.1 - Init System
     * checking for exception due to false connection from the payment external system
     */
    @Test
    public void testFailPaymentSystem() {
        PaymentSystem stubPayment = new PaymentSystemStub();
        ProxySupply proxySupply = new ProxySupply();
        assertFalse(stubPayment.connect());
        assertTrue(proxySupply.connect());
        data=new TestData();
        users=new ConcurrentHashMap<>();
        stores=new ConcurrentHashMap<>();
        connectedUsers =new ConcurrentHashMap<>();
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        try {
            LogicManager test = new LogicManager(subscribe.getName(), subscribe.getPassword(), users, stores,
                    connectedUsers,paymentSystem,supplySystem);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * test: use case 1.1 - Init System
     * checking for exception due to false connection output from the supply external system
     */
    @Test
    public void testFailSupplySystem() {
        ProxyPayment proxyPayment = new ProxyPayment();
        SupplySystem stubSupply = new SupplySystemStub();
        assertTrue(proxyPayment.connect());
        assertFalse(stubSupply.connect());
        data=new TestData();
        users=new ConcurrentHashMap<>();
        stores=new ConcurrentHashMap<>();
        connectedUsers =new ConcurrentHashMap<>();
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        try {
            LogicManager test = new LogicManager(subscribe.getName(), subscribe.getPassword(), users, stores,
                    connectedUsers,paymentSystem,supplySystem);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    /**
     * test connection to the system
     */
    @Test
    public void testConnectToSystem() {
        for (int id = 0; id < 10; id++) {
            assertEquals(id, logicManager.connectToSystem());
            assertTrue(this.connectedUsers.containsKey(id));
        }
    }

    /**
     * part of test use case 2.2 - Register
     */
    @Test
    public void testRegisterSuccess() {
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertTrue(logicManager.register(subscribe.getName(),subscribe.getPassword()));
    }

    /**
     * part of test use case 2.2 - Register
     */
    @Test
    public void testRegisterFailWrongName() {
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.WRONG_NAME);
        assertFalse(logicManager.register(subscribe.getName(),subscribe.getPassword()));
        assertFalse(users.containsKey(subscribe.getName()));
    }

    /**
     * part of test use case 2.2 - Register
     */
    @Test
    public void testRegisterFailWrongPassword() {
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.WRONG_PASSWORD);
        assertFalse(logicManager.register(subscribe.getName(), subscribe.getPassword()));
        assertFalse(users.containsKey(subscribe.getName()));
    }

    /**
     * part of test use case 2.2 - Register
     */
    @Test
    public void testRegisterFailNull() {
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.NULL);
        assertFalse(logicManager.register(subscribe.getName(), subscribe.getName()));
    }

    /**
     * test a case that trying to register user twice
     */
    @Test
    public void testRegisterFailAlreadyRegistered(){
        setUpConnect();
        setUpRegisteredUser();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertFalse(logicManager.register(subscribe.getName(),subscribe.getPassword()));
    }

    /**
     * test use case 2.3 - Login
     */
    @Test
    public void testLogin() {
        setUpRegisteredUser();
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
        assertFalse(logicManager.login(data.getId(Data.VALID), subscribe.getName(), subscribe.getPassword()));
    }

    /**
     * part of use case 2.3 - Login
     */
    private void testLoginFailWrongName() {
        Subscribe subscribe = data.getSubscribe(Data.WRONG_NAME);
        assertFalse(logicManager.login(data.getId(Data.VALID), subscribe.getName(), subscribe.getPassword()));
    }

    /**
     * part of use case 2.3 - Login
     */
    private void testLoginFailWrongPassword() {
        Subscribe subscribe = data.getSubscribe(Data.WRONG_PASSWORD);
        assertFalse(logicManager.login(data.getId(Data.VALID), subscribe.getName(), subscribe.getPassword()));
    }

    /**
     * part of use case 2.3 - Login
     */
    protected void testLoginSuccess() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertTrue(logicManager.login(data.getId(Data.VALID), subscribe.getName(),subscribe.getPassword()));
    }


    /**
     * use case 2.4.1 - view all stores details
     */
    @Test
    public void testViewDataStores() {
        setUpOpenedStore();
        List<StoreData> expected = new LinkedList<>();
        expected.add(data.getStore(Data.VALID));
        assertEquals(expected, logicManager.viewStores());
        assertNotEquals(null, logicManager.viewStores());
    }

    /**
     * use case 2.4.2 - view the products in some store with valid data test
     */
    @Test
    public void testViewProductsInStore() {
        setUpProductAdded();
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        assertEquals(expected, logicManager.viewProductsInStore(storeName));
    }

    /**
     * use case 2.4.2 - view the products in some store with valid data test
     */
    @Test
    public void testViewProductInStoreNotExists() {
        setUpProductAdded();
        String storeName = data.getStore(Data.WRONG_STORE).getName();
        assertNull(logicManager.viewProductsInStore(storeName));
    }

    /**
     * use case 2.4.2 - view the products in some store with valid data test
     */
    @Test
    public void testViewProductInStoreNull() {
        setUpProductAdded();
        String storeName = data.getStore(Data.NULL_STORE).getName();
        assertNull(logicManager.viewProductsInStore(storeName));
    }

    /**
     * use case 2.4.2 - view the products in some store with null category test
     */
    @Test
    public void testViewProductsInStoreNullCategory() {
        setUpProductAdded();
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        expected.add(data.getProductData(Data.NULL_CATEGORY));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.remove(data.getProductData(Data.NULL_CATEGORY));
    }

    /**
     * use case 2.4.2 - view the products in some store with null name test
     */
    @Test
    public void testViewProductsInStoreNullCategoryName() {
        setUpProductAdded();
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        expected.add(data.getProductData((Data.NULL_NAME)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.remove(data.getProductData((Data.NULL_NAME)));
    }

    /**
     * use case 2.4.2 - view the products in some store with null discount test
     */
    @Test
    public void testViewProductsInStoreNullDiscount() {
        setUpProductAdded();
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        expected.add(data.getProductData((Data.NULL_DISCOUNT)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.remove(data.getProductData((Data.NULL_DISCOUNT)));
    }

    /**
     * use case 2.4.2 - view the products in some store with null purchase test
     */
    @Test
    public void testViewProductsInStoreNullPurchase() {
        setUpProductAdded();
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        expected.add(data.getProductData((Data.NULL_PURCHASE)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.remove(data.getProductData((Data.NULL_PURCHASE)));
    }

    /**
     * use case 2.5 - view specific product
     */
    @Test
    public void testViewSpecificProduct() {
        setUpProductAdded();
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
    //TODO split tests
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
    //TODO split tests
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
     * use case 2.7.1 fails tests
     */
    @Test
    public void testWatchCartDetails() {
        setUpProductAddedToCart();
        testWatchCartDetailsNull();
        testWatchCartDetailsNullStore();
    }



    /**
     * use case 2.7.1 fail when the product is null
     */
    private void testWatchCartDetailsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        CartData cartData = logicManager.watchCartDetails(data.getId(Data.VALID));
        assertFalse(cartData.getProducts().contains(productData));
    }

    /**
     * use case 2.7.1 fail when the basket is null
     */
    private void testWatchCartDetailsNullStore() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        CartData cartData = logicManager.watchCartDetails(data.getId(Data.VALID));
        assertFalse(cartData.getProducts().contains(productData));
    }


    /**
     * use case 2.7.2 delete product from cart
     * fails tests
     */
    @Test
    public void testDeleteProductFromCart() {
        setUpProductAddedToCart();
        testDeleteProductFromCartBasketIsNull();
        testDeleteProductFromCartProductIsNull();
    }

    /**
     * use case 2.7.2 delete product from cart
     * fails test - product is null
     */
    private void testDeleteProductFromCartProductIsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        assertFalse(logicManager.deleteFromCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName()));
    }

    /**
     * use case 2.7.2 delete product from cart
     * fails test - basket is null
     */
    private void testDeleteProductFromCartBasketIsNull() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.deleteFromCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName()));
    }

    /**
     * use case 2.7.3 fail tests
     */
    @Test
    public void testEditProductsInCart() {
        setUpProductAddedToCart();
        testEditProductsInCartBasketIsNull();
        testEditProductsInCartNegativeAmount();
        testEditProductsInCartProductIsNull();
    }

    /**
     * use case 2.7.3 fail test when the product is null
     */
    private void testEditProductsInCartProductIsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        assertFalse(logicManager.editProductInCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName(),productData.getAmount()));
    }

    /**
     * use case 2.7.3 fail test when the amount is negative
     */
    private void testEditProductsInCartNegativeAmount() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.editProductInCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName(),productData.getAmount()));
    }

    /**
     * use case 2.7.3 fail test when the basket is null
     */
    private void testEditProductsInCartBasketIsNull() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.editProductInCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName(),productData.getAmount() + 1));
    }

    /**
     *  use case 2.7.4 - add product to cart
     */
    @Test
    public void testAddProductToCart() {
        setUpProductAdded();
        testAddProductToCartInvalidStore();
    }

    /**
     * part of use case 2.7.4 - add product to cart
     */
    private void testAddProductToCartInvalidStore() {
        ProductData product = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.addProductToCart(data.getId(Data.VALID),product.getProductName(),product.getStoreName(),product.getAmount()));
    }

    /**
     * use case 2.8 - test buy Products
     */
    //TODO change because change implementation
    @Test
    public void testBuyProducts() {
        setUpProductAddedToCart();
        testFailBuyProducts();
        testSuccessBuyProducts();
    }

    /**
     * use case 2.8 - test buy Products
     * success tests
     */
    //TODO change because change implementation
    private void testSuccessBuyProducts() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        assertTrue(logicManager.purchaseCart(data.getId(Data.VALID), paymentData, address));
    }

    /**
     * use case 2.8 - test buy Products
     * fails tests
     */
    //TODO change because change implementation
    //TODO split tests
    private void testFailBuyProducts() {
        // null data payment
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), null, null));
        // null address in payment
        PaymentData paymentData = data.getPaymentData(Data.NULL_ADDRESS);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), paymentData, address));
        // empty address in payment
        paymentData = data.getPaymentData(Data.EMPTY_ADDRESS);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), paymentData, address));
        // null payment
        paymentData = data.getPaymentData(Data.NULL_PAYMENT);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), paymentData, address));
        // empty payment
        paymentData = data.getPaymentData(Data.EMPTY_PAYMENT);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), paymentData, address));
        // null name in payment
        paymentData = data.getPaymentData(Data.NULL_NAME);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), paymentData, address));
        // empty name in payment
        paymentData = data.getPaymentData(Data.EMPTY_NAME);
        address = data.getDeliveryData(Data.VALID).getAddress();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), paymentData, address));
        // null address
        paymentData = data.getPaymentData(Data.VALID);
        address = data.getDeliveryData(Data.NULL_ADDRESS).getAddress();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), paymentData, address));
        // empty address
        paymentData = data.getPaymentData(Data.VALID);
        address = data.getDeliveryData(Data.EMPTY_ADDRESS).getAddress();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), paymentData, address));
    }


    /**
     * test: use case 3.1 - Logout
     */
    @Test
    public void testLogout() {
        setUpLogedInUser();
        assertTrue(currUser.logout());
        assertTrue(currUser.getState() instanceof Guest);
    }

    /**
     * test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStore() {
        setUpLogedInUser();
        testOpenStoreSucces();
    }
    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreNull() {
        setUpLogedInUser();
        assertFalse(logicManager.openStore(data.getId(Data.VALID), data.getStore(Data.NULL)));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreNullName() {
        setUpLogedInUser();
        assertFalse(logicManager.openStore(data.getId(Data.VALID), data.getStore(Data.NULL_NAME)));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreNullPurchase() {
        setUpLogedInUser();
        assertFalse(logicManager.openStore(data.getId(Data.VALID), data.getStore(Data.NULL_PURCHASE)));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreNullDiscount() {
        setUpLogedInUser();
        assertFalse(logicManager.openStore(data.getId(Data.VALID), data.getStore(Data.NULL_DISCOUNT)));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    protected void testOpenStoreSucces(){
        StoreData storeData = data.getStore(Data.VALID);
        assertTrue(logicManager.openStore(data.getId(Data.VALID), storeData));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    public void testOpenStoreReopen() {
        setUpLogedInUser();
        testOpenStoreSucces();
        StoreData storeData = data.getStore(Data.VALID);
        assertFalse(logicManager.openStore(data.getId(Data.VALID), storeData));
    }

    /**
     * use case 3.3 - write review
     */
    @Test
    public void testWriteReview() {
        setUpBoughtProduct();
        testWriteReviewInvalid();
        testWriteReviewValid();
    }

    /**
     * part of use case 3.3 - write review
     */
    //TODO split tests
    private void testWriteReviewInvalid() {
        Review review = data.getReview(Data.NULL_STORE);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()));

        review = data.getReview(Data.NULL_PRODUCT);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()));

        review = data.getReview(Data.NULL_CONTENT);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()));

        review = data.getReview(Data.EMPTY_CONTENT);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()));

        review = data.getReview(Data.WRONG_STORE);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()));

        review = data.getReview(Data.NULL_PRODUCT);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()));

    }

    /**
     * part of use case 3.3 - write review
     */
    protected void testWriteReviewValid() {
        Review review = data.getReview(Data.VALID);
        assertTrue(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()));
    }

    /**
     * use case 3.5 -add request
     * in this level we test the:
     * 1. enter null content
     * 2. enter request to invalid store
     */
    @Test
    public void testAddRequest(){
        setUpOpenedStore();
        testAddRequestSuccess();
    }

     /**
     * part of use case 3.5 -add request
     */
     private void testAddRequestSuccess() {
        Request request = data.getRequest(Data.VALID);
        assertTrue(logicManager.addRequest(data.getId(Data.VALID),request.getStoreName(),request.getContent()));
    }

    /**
     * part of use case 3.5 -add request
     */
    @Test
    public void testAddRequestWrongName() {
        setUpOpenedStore();
        Request request1 = data.getRequest(Data.WRONG_STORE);
        assertFalse(logicManager.addRequest(data.getId(Data.VALID),request1.getStoreName(), request1.getContent()));
    }

    /**
     * part of use case 3.5 -add request
     */
    @Test
    public void testAddRequestNullName() {
        setUpOpenedStore();
        Request request2 = data.getRequest(Data.NULL_NAME);
        assertFalse(logicManager.addRequest(data.getId(Data.VALID),request2.getStoreName(), request2.getContent()));
    }

    /**
     * part of use case 3.5 -add request
     */
    @Test
    public void testAddRequestContentNull() {
        setUpOpenedStore();
        Request request2 = data.getRequest(Data.NULL_CONTENT);
        assertFalse(logicManager.addRequest(data.getId(Data.VALID),request2.getStoreName(), request2.getContent()));
    }

    /**
     * use case 3.7 - watch purchase history
     */
    @Test
    public void testWatchPurchaseHistory() {
        setUpBoughtProduct();
        List<Purchase> purchases = logicManager.watchMyPurchaseHistory(data.getId(Data.VALID));
        assertNotNull(purchases);
        assertTrue(purchases.isEmpty());
    }

    /**
     * use case 4.1.1 - add product
     */
    @Test
    public void testAddProductSuccess() {
        setUpOpenedStore();
        assertTrue(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.VALID)));
    }

    @Test
    public void testAddProductFail(){
        setUpOpenedStore();
        testAddProductFailNullProduct();
        testAddProductFailNullProductName();
        testAddProductFailNullCategory();
        testAddProductNullStoreName();
        testAddProductNullDiscount();
        testAddProductNegativeAmount();
        testAddProductNegativePrice();
        testAddProductNullPurchasePolicy();
        testAddProductOver100DiscountPercentage();
        testAddProductDiscountListWithNullValue();
        testAddProductNegativeDiscountPercentage();
    }

    private void testAddProductNegativeDiscountPercentage() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NEGATIVE_PERCENTAGE)));
    }

    private void testAddProductDiscountListWithNullValue() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.WRONG_DISCOUNT)));
    }

    private void testAddProductOver100DiscountPercentage() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.OVER_100_PERCENTAGE)));
    }

    private void testAddProductNullPurchasePolicy() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NULL_PURCHASE)));
    }

    private void testAddProductNegativePrice() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NEGATIVE_PRICE)));
    }

    private void testAddProductNegativeAmount() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NEGATIVE_AMOUNT)));
    }

    private void testAddProductNullDiscount() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NULL_DISCOUNT)));
    }

    private void testAddProductFailNullCategory() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NULL_CATEGORY)));
    }

    private void testAddProductFailNullProductName() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NULL_NAME)));
    }

    private void testAddProductNullStoreName(){
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.WRONG_STORE)));
    }

    protected void testAddProductFailNullProduct(){
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),null));
    }

    /**
     * use case 4.1.2 -delete product
     */
    @Test
    public void testRemoveProductFromStore(){
        setUpProductAdded();
        testRemoveProductSuccess();
        testRemoveProductTwiceFail();
    }

    /**
     * test remove with no exist store
     */
    private void testRemoveProductTwiceFail() {
        assertFalse(logicManager.removeProductFromStore(data.getId(Data.VALID),data.getSubscribe(Data.VALID).getName()
                ,data.getProductData(Data.VALID).getProductName()));
    }

    protected void testRemoveProductSuccess() {
        ProductData p=data.getProductData(Data.VALID);
        assertTrue(logicManager.removeProductFromStore(data.getId(Data.VALID),p.getStoreName(),p.getProductName()));
    }

    /**
     * test use case 4.1.3 - edit product in store
     */
    @Test
    public void testEditProductSuccess() {
        setUpProductAdded();
        assertTrue(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.EDIT)));
    }

    @Test
    public void testEditProductFail(){
        setUpOpenedStore();
        testEditProductFailNullProduct();
        testEditProductFailNullProductName();
        testEditProductFailNullCategory();
        testEditProductNullStoreName();
        testEditProductNullDiscount();
        testEditProductNegativeAmount();
        testEditProductNegativePrice();
        testEditProductNullPurchasePolicy();
        testEditProductOver100DiscountPercentage();
        testEditProductDiscountListWithNullValue();
        testEditProductNegativeDiscountPercentage();
    }

    private void testEditProductNegativeDiscountPercentage() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NEGATIVE_PERCENTAGE)));
    }

    private void testEditProductDiscountListWithNullValue() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.WRONG_DISCOUNT)));
    }

    private void testEditProductOver100DiscountPercentage() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.OVER_100_PERCENTAGE)));
    }

    private void testEditProductNullPurchasePolicy() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NULL_PURCHASE)));
    }

    private void testEditProductNegativePrice() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NEGATIVE_PRICE)));
    }

    private void testEditProductNegativeAmount() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NEGATIVE_AMOUNT)));
    }

    private void testEditProductNullDiscount() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NULL_DISCOUNT)));
    }

    private void testEditProductFailNullCategory() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NULL_CATEGORY)));
    }

    private void testEditProductFailNullProductName() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NULL_NAME)));
    }

    private void testEditProductNullStoreName(){
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.WRONG_STORE)));
    }

    protected void testEditProductFailNullProduct(){
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),null));
    }

    /**
     * test : use case 4.3 add owner
     */
    @Test
    public void testManageOwner(){
        setUpOpenedStore();
        testManageOwnerFail();
        testManageOwnerFailAgain();
        testManageOwnerSuccess();
    }

    private void testManageOwnerFailAgain() {
        assertFalse(logicManager.manageOwner(data.getId(Data.VALID),data.getSubscribe(Data.VALID).getName(),
                data.getSubscribe(Data.VALID2).getName()));
    }

    protected void testManageOwnerSuccess() {
        assertTrue(logicManager.manageOwner(data.getId(Data.VALID),data.getStore(Data.VALID).getName(),
                data.getSubscribe(Data.VALID2).getName()));
    }

    protected void testManageOwnerFail() {
        assertFalse(logicManager.manageOwner(data.getId(Data.VALID),data.getStore(Data.VALID).getName()
                ,data.getStore(Data.VALID).getName()));
    }

    /**
     * use case 4.5 add manager
     */
    @Test
    public void testAddManagerToStore(){
        setUpOpenedStore();
        testAddManagerToStoreFailStore();
        testAddManagerToStoreFailUser();
        testAddManagerStoreSuccess();
    }

    protected void testAddManagerStoreSuccess() {
        assertTrue(logicManager.addManager(data.getId(Data.VALID),data.getSubscribe(Data.ADMIN).getName(),data.getStore(Data.VALID).getName()));
    }

    /**
     * test not existing store
     */
    private void testAddManagerToStoreFailStore() {
        String userName=data.getSubscribe(Data.ADMIN).getName();
        String storeName=data.getStore(Data.VALID).getName();
        //invalid storeName
        assertFalse(logicManager.addManager(data.getId(Data.VALID),userName,userName));
        assertFalse(stores.get(storeName).getPermissions().containsKey(userName));
    }

    /**
     * test not existing user
     */
    private void testAddManagerToStoreFailUser(){
        String storeName=data.getStore(Data.VALID).getName();
        assertFalse(logicManager.addManager(data.getId(Data.VALID),storeName,storeName));
        assertFalse(stores.get(storeName).getPermissions().containsKey(storeName));
    }

    /**
     * test use case 4.6.1- add permission
     */
    @Test
    public void testAddPermission(){
        setUpManagerAdded();
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
       testAddPermissionFailWrongUserName();
       testAddPermissionFailWrongStoreName();
       testAddPermissionFailWrongNullPermissions();
       testAddPermissionFailWrongPermissionListWithNull();
    }

    private void testAddPermissionFailWrongUserName(){
        String store=data.getStore(Data.VALID).getName();
        List<PermissionType> types=data.getPermissionTypeList();
        assertFalse(logicManager.addPermissions(data.getId(Data.VALID),types,store,store));
    }

    private void testAddPermissionFailWrongStoreName(){
        String user=data.getSubscribe(Data.ADMIN).getName();
        List<PermissionType> types=data.getPermissionTypeList();
        assertFalse(logicManager.addPermissions(data.getId(Data.VALID),types,user,user));
    }

    private void testAddPermissionFailWrongNullPermissions(){
        String user=data.getSubscribe(Data.ADMIN).getName();
        String store=data.getStore(Data.VALID).getName();
        assertFalse(logicManager.addPermissions(data.getId(Data.VALID),null,store,user));
    }

    private void testAddPermissionFailWrongPermissionListWithNull(){
        String user=data.getSubscribe(Data.ADMIN).getName();
        String store=data.getStore(Data.VALID).getName();
        List<PermissionType> types=data.getPermissionTypeList();
        types.add(null);
        assertFalse(logicManager.addPermissions(data.getId(Data.VALID),types,store,user));
        types.remove(null);
    }

    protected void testAddPermissionSuccess() {
        assertTrue(currUser.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()));
    }

    /**
     * test use case 4.6.2 - remove permissions
     */
    @Test
    public void testRemovePermission(){
        setUpPermissionsAdded();
        testRemovePermissionFail();
        testRemovePermissionSuccess();
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
        assertFalse(logicManager.removePermissions(data.getId(Data.VALID),types,store,store));
        assertFalse(logicManager.removePermissions(data.getId(Data.VALID),types,user,user));
        assertFalse(logicManager.removePermissions(data.getId(Data.VALID),null,store,user));
        types.add(null);
        assertFalse(logicManager.removePermissions(data.getId(Data.VALID),types,store,user));
        types.remove(null);
    }

    protected void testRemovePermissionSuccess() {
        assertTrue(currUser.removePermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()));
    }

    /**
     * test use case 4.7 - remove manager
     */
    @Test
    public void testRemoveManager(){
        setUpManagerAddedSubManagerAdded();
        testRemoveManagerFailStore();
        testRemoveManagerFailUser();
        testRemoveManagerSuccess();
    }

    protected void testRemoveManagerSuccess() {
        assertTrue(logicManager.removeManager(data.getId(Data.VALID),data.getSubscribe(Data.ADMIN).getName(),data.getStore(Data.VALID).getName()));
    }

    /**
     * test not existing user
     */
    private void testRemoveManagerFailStore() {
        String storeName=data.getStore(Data.VALID).getName();
        //invalid username
        assertFalse(logicManager.removeManager(data.getId(Data.VALID),storeName,storeName));
    }

    /**
     * test not existing store
     */
    private void testRemoveManagerFailUser() {
        String userName=data.getSubscribe(Data.ADMIN).getName();
        //invalid storeName
        assertFalse(logicManager.removeManager(data.getId(Data.VALID),userName,userName));
    }

    /**
     * use case 4.9.1 -view request
     */
    @Test
    public void testStoreViewRequest(){
        setUpRequestAdded();
        testStoreViewRequestSuccess();
        testStoreViewRequestFailNullName();
        testStoreViewRequestFailWrongStore();

    }

    private void testStoreViewRequestSuccess() {
        Request request = data.getRequest(Data.VALID);
        assertFalse(currUser.viewRequest(request.getStoreName()).isEmpty());
    }

    private void testStoreViewRequestFailNullName() {
        Request request1 = data.getRequest(Data.NULL_NAME);
        assertTrue(logicManager.viewStoreRequest(data.getId(Data.VALID), request1.getStoreName()).isEmpty());
    }

    private void testStoreViewRequestFailWrongStore() {
        Request request2 = data.getRequest(Data.WRONG_STORE);
        assertTrue(logicManager.viewStoreRequest(data.getId(Data.VALID), request2.getStoreName()).isEmpty());
    }

        /**
         * use case 4.9.2 -replay request
         */
    @Test
    public void testReplayRequest(){
        setUpRequestAdded();
        testReplayRequestSuccess();
        testReplayRequestFailWrongStore();
    }

    private void testReplayRequestSuccess() {
        Request request = data.getRequest(Data.VALID);
        assertNotNull(currUser.replayToRequest(request.getStoreName(),request.getId(), request.getContent()));
    }

    private void testReplayRequestFailWrongStore() {
        Request request1 = data.getRequest(Data.WRONG_STORE);
        assertNull(logicManager.replayRequest(data.getId(Data.VALID), request1.getStoreName(), request1.getId(), request1.getContent()));
    }

    /**
     * use case 6.4.1 - watch User History
     */
    @Test
    public void testWatchUserHistory(){
        setUpBoughtProductAdminState();
        testWatchUserHistoryUserNotExist();
        testWatchUserHistorySuccess();
    }

    /**
     * test user that not exist on users map
     */
    private void testWatchUserHistoryUserNotExist() {
        assertNull(logicManager.watchUserPurchasesHistory(data.getId(Data.VALID), data.getStore(Data.VALID).getName()));
    }

    /**
     * test success
     */
    protected void testWatchUserHistorySuccess() {
        assertNotNull(logicManager.watchUserPurchasesHistory(data.getId(Data.ADMIN),data.getSubscribe(Data.VALID).getName()));
    }

    /**
     * use case 6.4.2 , 4.10 - watch store history
     */
    @Test
    public void testWatchStoreHistory(){
        setUpBoughtProductAdminState();
        testWatchStoreHistoryStoreNotExist();
        testWatchStoreHistorySuccess();
    }

    /**
     * test store that not exist on users map
     */
    private void testWatchStoreHistoryStoreNotExist() {
        assertNull(logicManager.watchStorePurchasesHistory(data.getId(Data.VALID), data.getSubscribe(Data.VALID).getName()));
    }

    /**
     * test success
     */
    protected void testWatchStoreHistorySuccess() {
        assertNotNull(logicManager.watchStorePurchasesHistory(data.getId(Data.VALID), data.getStore(Data.VALID).getName()));
    }

}