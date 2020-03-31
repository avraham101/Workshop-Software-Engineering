package LogicManagerTests;

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

    }

    protected class StoreStub extends Store {

        public StoreStub(String name, PurchesPolicy purchesPolicy, DiscountPolicy discount, Permission permissions, SupplySystem supplySystem, PaymentSystem paymentSystem) {
            super(name, purchesPolicy, discount, permissions, supplySystem, paymentSystem);
        }
    }

}