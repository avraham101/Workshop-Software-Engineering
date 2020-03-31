package LogicManagerTests;

import Domain.*;
import Systems.HashSystem;
import org.junit.Before;

import java.util.HashMap;

import static org.junit.Assert.*;

//no stubs full integration
public class LogicManagerRealTest extends LogicManagerUserStubTest {

    @Before
    public void setUp() {
        currUser=new User();
        users=new HashMap<>();
        stores=new HashMap<>();
        logicManager = new LogicManager(users,stores,currUser);
        logicManager.register("Admin","Admin");
    }

    @Override
    public void testLogin(){
        super.testLogin();
        testLoginFailAlreadyUserLogged();
    }

    public void testLoginFailAlreadyUserLogged() {
        assertFalse(logicManager.login("Yuval","Sabag"));
    }

    @Override
    protected void testLoginSuccess() {
        super.testLoginSuccess();
        assertEquals(currUser.getUserName(),"Yuval");
        try {
            HashSystem hashSystem = new HashSystem();
            String password = hashSystem.encrypt("Sabag");
            assertEquals(password, currUser.getPassword());
        } catch (Exception e) {
            fail();
        }
    }
}

