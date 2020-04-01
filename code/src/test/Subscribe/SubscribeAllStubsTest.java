package Subscribe;

import Data.Data;
import Data.TestData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SubscribeAllStubsTest {

    protected Subscribe sub;
    protected PaymentSystem paymentSystem;
    protected SupplySystem supplySystem;
    protected TestData data;

    @Before
    public void setUp(){
        sub=new Subscribe("Yuval","Sabag");
        data=new TestData();
        initStore();
    }

    private void initStore() {
        paymentSystem = new ProxyPayment();
        supplySystem = new ProxySupply();
    }

    /**
     * test use case 2.3 - Login
     * main test function for subscribe
     */
    @Test
    public void test(){
        loginTest();
        openStoreTest();
        addProductToStoreTest();
        logoutTest();
    }

    /**
     * part of test use case 2.3 - Login
     * test login where all fields are stubs
     */
    protected void loginTest() {
        assertFalse(sub.login(new User(),new Subscribe("avraham","calev")));
    }


    /**
     * test use case 3.2 - Open Store
     * store: Niv shiraze store added.
     */
    protected void openStoreTest() {
        Store store = sub.openStore(data.getStore(Data.VALID),paymentSystem,supplySystem);
        assertNotNull(store);

    }

    /**
     * test: use case 3.1 - Logout
     */
    protected  void logoutTest(){
        assertTrue(sub.logout(new User()));
    }

    /**
     * test 4.9.1 use case 4.9.1 - add product
     */
    protected void addProductToStoreTest(){
        addProductToStoreTestSuccess();
    }

    private void addProductToStoreTestSuccess(){
        assertTrue(sub.addProductToStore(data.getProduct(Data.VALID)));
    }


}