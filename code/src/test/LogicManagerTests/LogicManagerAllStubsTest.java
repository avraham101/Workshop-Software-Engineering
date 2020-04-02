package LogicManagerTests;

import Data.*;
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
        logicManager = new LogicManager(users,stores,currUser);
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        logicManager.register(subscribe.getName(),subscribe.getPassword());
    }

    @Test
    public void test() {
        testExternalSystems();
        testRegister();
        testLogin();
        testOpenStore();
        testAddManagerToStore();
        testAddProduct();
        testAddRequest();
        testStoreViewRequest();
        testViewSpecificProduct();
        testEditProduct();
        testRemoveProductFromStore();
        testLogout();
        testViewDataStores();
        testViewProductsInStore();
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
        assertFalse(logicManager.addRequest(data.getStore(Data.VALID).getName(), null));
        assertFalse(logicManager.addRequest(null, "good store"));
    }

    public void testStoreViewRequest(){
        assertTrue(logicManager.viewStoreRequest(data.getStore(Data.NULL_NAME).getName()).isEmpty());
        assertTrue(logicManager.viewStoreRequest(data.getStore(Data.WRONG_STORE).getName()).isEmpty());
    }

    /**
     * use case 4.1.1 -add product
     */

    //TODO: Added
    protected void testAddProduct(){
        testAddProductFail();
        testProductSuccess();
    }

    protected void testProductSuccess() {
        assertTrue(logicManager.addProductToStore(data.getProduct(Data.VALID)));
    }

    protected void testAddProductFail(){
        assertFalse(logicManager.addProductToStore(null));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NULL_NAME)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.WRONG_STORE)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NULL_CATEGORY)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NULL_DISCOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NEGATIVE_AMOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NEGATIVE_PRICE)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NULL_PURCHASE)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.OVER_100_PERCENTAGE)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.WRONG_DISCOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NEGATIVE_PERCENTAGE)));
    }

    /**
     * use case 2.4.2 - view the products in some store test
     */
    protected void testViewProductsInStore() {
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        assertEquals(expected, logicManager.viewProductsInStore(storeName));

        expected.add(data.getProduct(Data.NULL_CATEGORY));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.remove(data.getProduct(Data.NULL_CATEGORY));

        expected.add(data.getProduct((Data.NULL_NAME)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.add(data.getProduct((Data.NULL_NAME)));

        expected.add(data.getProduct((Data.NULL_DISCOUNT)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.add(data.getProduct((Data.NULL_DISCOUNT)));

        expected.add(data.getProduct((Data.NULL_PURCHASE)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.add(data.getProduct((Data.NULL_PURCHASE)));
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
    protected void testViewSpecificProductSearchAndFilter() {
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
    protected void testRemoveProductFromStore(){
        testRemoveProductSuccess();
        testRemoveProductFail();
    }

    /**
     * test remove with no exist store
     */

    private void testRemoveProductFail() {
        assertFalse(logicManager.removeProductFromStore(data.getSubscribe(Data.VALID).getName()
                ,data.getProduct(Data.VALID).getProductName()));
    }

    protected void testRemoveProductSuccess() {
        ProductData p=data.getProduct(Data.VALID);
        assertTrue(logicManager.removeProductFromStore(p.getStoreName(),p.getProductName()));
    }

    /**
     * test use case 4.1.3 - edit product in store
     */
    protected void testEditProduct(){
        testEditProductFail();
        testEditProductSuccess();
    }

    protected void testEditProductSuccess() {
        assertTrue(logicManager.editProductFromStore(data.getProduct(Data.EDIT)));
    }

    protected void testEditProductFail() {
        assertFalse(logicManager.editProductFromStore(null));
        assertFalse(logicManager.editProductFromStore(data.getProduct(Data.NULL_NAME)));
        assertFalse(logicManager.editProductFromStore(data.getProduct(Data.WRONG_STORE)));
        assertFalse(logicManager.editProductFromStore(data.getProduct(Data.NULL_CATEGORY)));
        assertFalse(logicManager.editProductFromStore(data.getProduct(Data.NULL_DISCOUNT)));
        assertFalse(logicManager.editProductFromStore(data.getProduct(Data.NEGATIVE_AMOUNT)));
        assertFalse(logicManager.editProductFromStore(data.getProduct(Data.NEGATIVE_PRICE)));
        assertFalse(logicManager.editProductFromStore(data.getProduct(Data.NULL_PURCHASE)));
        assertFalse(logicManager.editProductFromStore(data.getProduct(Data.OVER_100_PERCENTAGE)));
        assertFalse(logicManager.editProductFromStore(data.getProduct(Data.WRONG_DISCOUNT)));
        assertFalse(logicManager.editProductFromStore(data.getProduct(Data.NEGATIVE_PERCENTAGE)));
    }

    /**
     * test : use case 4.5
     */
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
    protected void testAddManagerToStoreFail() {
        String storeName=data.getStore(Data.VALID).getName();
        String userName=data.getSubscribe(Data.ADMIN).getName();
        //invalid username
        assertFalse(logicManager.addManager(storeName,storeName));
        //invalid storeName
        assertFalse(logicManager.addManager(userName,userName));
    }

}