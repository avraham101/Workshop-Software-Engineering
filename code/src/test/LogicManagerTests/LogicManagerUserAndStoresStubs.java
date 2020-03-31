package LogicManagerTests;

import Domain.Admin;
import Domain.LogicManager;
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

    @Override
    public void testRegister() {
        testAdmin();
        super.testRegister();
    }

    public void testAdmin() {
        assertTrue(users.get("Admin") instanceof Admin);
    }

    @Override
    public void testRegisterSuccess() {
        super.testRegisterSuccess();
        assertEquals(users.get("Yuval").getName(), "Yuval");
        try {
            HashSystem hashSystem = new HashSystem();
            String password = hashSystem.encrypt("Sabag");
            assertEquals(users.get("Yuval").getPassword(), password);
        } catch (Exception e) {
            fail();
        }
    }
}
