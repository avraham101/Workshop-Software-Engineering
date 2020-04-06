package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InitialStartTest extends AcceptanceTests {

    private UserTestData user1 = users.get(1);

//    @Before
//    public void setUp(){
//        super.setUp();
//        user1 = users.get(1);
//    }

    public void runInitialStartAllTests(){
        testInitialStartSuccess();
        testInitialStartFail();
    }

    private void testInitialStartSuccess(){
        String username = admin.getUsername();
        String password = admin.getPassword();
        boolean initialStart = bridge.initialStart(username, password);
        assertTrue(initialStart);
        boolean isAdmin = bridge.getAdminUsername().equals(username);
        assertTrue(isAdmin);
        deleteUser(admin.getUsername());
    }

    private void testInitialStartFail(){
        String username0 = admin.getUsername();
        String password0 = admin.getPassword();
        String username1 = user1.getUsername();
        String password1 = user1.getPassword();
        boolean initialStart0 = bridge.initialStart(username0, password0);
        boolean initialStart1 = bridge.initialStart(username1, password1);
        assertTrue(initialStart0);
        assertFalse(initialStart1);
        assertFalse(bridge.getAdminUsername().equals(username1));
    }
//    @After
//    public void tearDown(){
//        // allowed because current user is admin
//        bridge.resetSystem();
//    }
}
