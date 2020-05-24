package LogicManagerTests;

import Data.Data;
import Domain.Admin;
import Domain.Subscribe;
import Stubs.UserStub;
import Systems.HashSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LogicManagerUserAndStoresStubs extends LogicManagerAllStubsTest {

    /**
     * Adding Stores must be in type StoreStub
     * example: stores.put(Key,new StoreStub(...))
     */
    @Before
    public void setUp() {
        currUser=new UserStub();
        supplySystem=new ProxySupply();
        paymentSystem=new ProxyPayment();
        init();
    }

    /**
     * part of test: use case 2.2 - Register
     */
    @Test
    public void testAdminRegister() {
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        assertTrue(daos.getSubscribeDao().find(subscribe.getName()) instanceof Admin);
    }

    /**
     * part of test: use case 2.2 - Register
     */
    @Test
    public void testRegisterSuccess() {
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        assertEquals(daos.getSubscribeDao().find(subscribe.getName()).getName(), subscribe.getName());
        try {
            HashSystem hashSystem = new HashSystem();
            String password = hashSystem.encrypt(subscribe.getPassword());
            assertEquals(daos.getSubscribeDao().find(subscribe.getName()).getPassword(), password);
        } catch (Exception e) {
            fail();
        }
    }

}
