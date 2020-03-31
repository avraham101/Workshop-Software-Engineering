package LogicManagerTests;

import Domain.LogicManager;
import Domain.Store;
import Domain.Subscribe;
import Domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

//class for Unit test all stubs
import static org.junit.Assert.*;

public class LogicManagerAllStubsTest {

    protected static LogicManager logicManager;
    protected User currUser;
    protected HashMap<String,Subscribe> users;
    protected HashMap<String,Store> stores;

    @Before
    public void setUp() {
        currUser=new UserStub();
        users=new HashMap<>();
        stores=new HashMap<>();
        logicManager = new LogicManager(users,stores,currUser);
        logicManager.register("Admin","Admin");
    }

    @Test
    public void test() {
        testRegister();
        testLogin();
    }

    public void testRegister() {
        testRegisterSuccess();
        testRegisterFailWrongName();
    }


    //TODO MOVE THIS TO CLASS
    public void testRegisterSuccess() {
        assertTrue(logicManager.register("Yuval","Sabag"));
    }

    //TODO MOVE THIS TO CLASS
    public void testRegisterFailWrongName() {
        assertFalse(logicManager.register("Admin","Admin2"));
    }

    public void testLogin() {
        testLoginFailWrongName();
        testLoginFailWrongPassword();
        testLoginSuccess();
    }

    private void testLoginFailWrongName() {
        assertFalse(logicManager.login("Shlomi", "BAD TEACHER"));
    }

    private void testLoginFailWrongPassword() {
        assertFalse(logicManager.login("Yuval", "BAD TEACHER"));
    }

    private void testLoginSuccess() {
        assertTrue(logicManager.login("Yuval","Sabag"));
    }


    class UserStub extends User {
        @Override
        public boolean login(Subscribe subscribe) {
            return true;
        }
    }


}