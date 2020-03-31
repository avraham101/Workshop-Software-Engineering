package LogicManagerTests;

import Domain.*;
import org.junit.Before;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//no stubs full integration
public class LogicManagerNoStubsTest extends LogicManagerAllStubsTest {

    @Before
    public void setUp() {
        currUser=new User();
        users=new HashMap<>();
        stores=new HashMap<>();
        logicManager = new LogicManager(users,stores,currUser);
        logicManager.register("Admin","Admin");
    }


    @Override
    public void testRegister() {
        testAdmin();
        super.testRegister();
    }

    public void testLogin(){
        super.testLogin();
        testLoginFailAlreadyUserLogged();
    }

    //TODO MOVE THIS TO CLASS
    public void testAdmin() {
        assertTrue(users.get("Admin") instanceof Admin);
    }

    public void testLoginFailAlreadyUserLogged() {
        assertFalse(logicManager.login("Yuval","Sabag"));
    }


}

