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
        init();
    }


    /**
     * test use case 2.3 - Login
     */
    @Override
    public void testLogin(){
        super.testLogin();
        testLoginFailAlreadyUserLogged();
    }

    public void testLoginFailAlreadyUserLogged() {
        assertFalse(logicManager.login("Yuval","Sabag"));
    }

    /**
     * test: use case 3.1 - Logout
     */
    @Override
    public void testLogout(){
        super.testLogout();
        //test while in Guest Mode
        assertFalse(currUser.logout());
    }

    /**
     * part of test use case 2.3 - Login
     */
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

