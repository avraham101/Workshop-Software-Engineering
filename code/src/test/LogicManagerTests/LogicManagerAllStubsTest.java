package LogicManagerTests;

import DataAPI.ProductData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
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
        testRegister();
        testLogin();
        testLogout();
    }

    protected void testLogout() {
        assertTrue(currUser.logout());
    }

    public void testRegister() {
        testRegisterSuccess();
        testRegisterFailWrongName();
    }

    public void testRegisterSuccess() {
        assertTrue(logicManager.register("Yuval","Sabag"));
    }

    public void testRegisterFailWrongName() {
        assertFalse(logicManager.register("Admin","Admin2"));
    }

    public void testLogin() {
        testLoginFailWrongName();
        testLoginFailWrongPassword();
        testLoginSuccess();
    }

    private void testAddProduct(){
        testAddProductFail();
        testProductSuccess();
    }

    private void testProductSuccess() {
        assertTrue(logicManager.addProductToStore(data.getProduct("valid")));
    }

    private void testAddProductFail(){
        assertFalse(logicManager.addProductToStore(data.getProduct("nullName")));
        assertFalse(logicManager.addProductToStore(data.getProduct("wrongStore")));
        assertFalse(logicManager.addProductToStore(data.getProduct("nullCategory")));
        assertFalse(logicManager.addProductToStore(data.getProduct("nullDiscount")));
        assertFalse(logicManager.addProductToStore(data.getProduct("negativeAmount")));
        assertFalse(logicManager.addProductToStore(data.getProduct("negativePrice")));
        assertFalse(logicManager.addProductToStore(data.getProduct("nullPurchase")));

    }

    private void testLoginFailWrongName() {
        assertFalse(logicManager.login("Shlomi", "BAD TEACHER"));
    }

    private void testLoginFailWrongPassword() {
        assertFalse(logicManager.login("Yuval", "BAD TEACHER"));
    }

    protected void testLoginSuccess() {
        assertTrue(logicManager.login("Yuval","Sabag"));
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

        public StoreStub(String name, PurchesPolicy purchesPolicy, DiscountPolicy discount, HashMap<String, Permission> permissions, SupplySystem supplySystem, PaymentSystem paymentSystem) {
            super(name, purchesPolicy, discount, permissions, supplySystem, paymentSystem);
        }

    }


}