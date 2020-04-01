package LogicManagerTests;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Systems.HashSystem;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
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
        Subscribe subscribe = users.get("Admin");
        users.put("Admin", new SubscribeStub(subscribe.getName(), subscribe.getPassword()));
    }

    protected void init() {
        data=new TestData();
        users=new HashMap<>();
        stores=new HashMap<>();
        logicManager = new LogicManager(users,stores,currUser);
        logicManager.register("Admin","Admin");
    }

    @Test
    public void test() {
        testExternalSystems();
        testRegister();
        testLogin();
        testOpenStore();
        testAddProduct();
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
     * test: use case 3.1 - Logout
     */
    protected void testLogout() {
        assertTrue(currUser.logout());
    }

    /**
     * test: use case 2.2 - Register
     */
    public void testRegister() {
        testRegisterSuccess();
        testRegisterFailWrongName();
    }

    /**
     * part of test use case 2.2 - Register
     */
    public void testRegisterSuccess() {
        assertTrue(logicManager.register("Yuval","Sabag"));
    }

    /**
     * part of test use case 2.2 - Register
     */
    public void testRegisterFailWrongName() {
        assertFalse(logicManager.register("Admin","Admin2"));
    }

    /**
     * test use case 2.3 - Login
     */
    public void testLogin() {
        testLoginFailWrongName();
        testLoginFailWrongPassword();
        testLoginSuccess();
    }


    /**
     * part of use case 2.3 - Login
     */
    private void testLoginFailWrongName() {
        assertFalse(logicManager.login("Shlomi", "BAD TEACHER"));
    }

    /**
     * part of use case 2.3 - Login
     */
    private void testLoginFailWrongPassword() {
        assertFalse(logicManager.login("Yuval", "BAD TEACHER"));
    }

    /**
     * part of use case 2.3 - Login
     */
    protected void testLoginSuccess() {
        assertTrue(logicManager.login("Yuval","Sabag"));
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
        assertFalse(logicManager.openStore(data.getStore(Data.NULL_NAME)));
        assertFalse(logicManager.openStore(data.getStore(Data.NULL_PURCHASE)));
        assertFalse(logicManager.openStore(data.getStore(Data.NULL_DISCOUNT)));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    private void testOpenStoreSucces(){
        assertTrue(logicManager.openStore(data.getStore(Data.VALID)));
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
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NULL_NAME)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.WRONG_STORE)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NULL_CATEGORY)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NULL_DISCOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NEGATIVE_AMOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NEGATIVE_PRICE)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NULL_PURCHASE)));
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




    protected class UserStub extends User {
        @Override
        public boolean login(Subscribe subscribe) {
            return true;
        }

        @Override
        public boolean logout(){
            return true;
        }

        @Override
        public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
            return new Store(storeDetails.getName(),new PurchesPolicy(),new DiscountPolicy(),
                    new Permission(new Subscribe(this.getUserName(),this.getPassword())),
                    supplySystem,paymentSystem);
        }

        @Override
        public boolean addProductToStore(ProductData productData){
            return true;
        }

    }

    protected class SubscribeStub extends Subscribe {

        public SubscribeStub(String userName, String password) {
            super(userName, password);
        }

        @Override
        public boolean addProductToStore(ProductData productData) {
            return true;
        }
    }

    protected class StoreStub extends Store {

        public StoreStub(String name, PurchesPolicy purchesPolicy, DiscountPolicy discount, Permission permissions, SupplySystem supplySystem, PaymentSystem paymentSystem) {
            super(name, purchesPolicy, discount, permissions, supplySystem, paymentSystem);
        }

    }


}