package LogicManagerTests;

import Data.*;
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
        testAddRequest();
        testAddProduct();
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
     * part of use case 2.3 - Login
     */
    protected void testLoginSuccess() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertTrue(logicManager.login(subscribe.getName(),subscribe.getPassword()));
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
     * use case 3.5 -add product
     */
    public void testAddRequest(){
        testAddRequestSuccess();
        testAddRequestFail();
    }

    private void testAddRequestSuccess() {
        assertTrue(logicManager.addRequest(data.getStore(Data.VALID).getName(), "The store has not milk"));
    }

    private void testAddRequestFail() {
        assertFalse(logicManager.addRequest(data.getStore(Data.WRONG_NAME).getName(), "The store has not milk"));
        assertFalse(logicManager.addRequest(data.getStore(Data.VALID).getName(), null));
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
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.OVER_100_PERCENTAGE)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.WRONG_DISCOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.NEGATIVE_PERCENTAGE)));
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