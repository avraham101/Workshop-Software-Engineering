package LogicManagerTests;

import Data.Data;
import Domain.Admin;
import Domain.LogicManager;
import Domain.Subscribe;
import Stubs.UserStub;
import Systems.HashSystem;
import org.junit.Before;

import java.util.HashMap;

import static org.junit.Assert.*;

public class LogicManagerUserAndStoresStubs extends LogicManagerAllStubsTest {

    /**
     * Adding Stores must be in type StoreStub
     * example: stores.put(Key,new StoreStub(...))
     */

    @Before
    public void setUp() {
        currUser=new UserStub();
        init();
    }

    /**
     * test: use case 2.2 - Register
     */
    @Override
    public void testRegister() {
        testAdmin();
        super.testRegister();
    }

    /**
     * part of test: use case 2.2 - Register
     */
    public void testAdmin() {
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        assertTrue(users.get(subscribe.getName()) instanceof Admin);
    }

    /**
     * part of test: use case 2.2 - Register
     */
    @Override
    public void testRegisterSuccess() {
        super.testRegisterSuccess();
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        assertEquals(users.get(subscribe.getName()).getName(), subscribe.getName());
        try {
            HashSystem hashSystem = new HashSystem();
            String password = hashSystem.encrypt(subscribe.getPassword());
            assertEquals(users.get(subscribe.getName()).getPassword(), password);
        } catch (Exception e) {
            fail();
        }
    }
}
