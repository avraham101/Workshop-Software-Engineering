package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InitialStartTest extends AcceptanceTests {

    private UserTestData user0;
    private UserTestData user1;

    @Before
    public void setUp(){
        super.setUp();
        user0 = users.get(0);
        user1 = users.get(1);
    }

    @Test
    public void testInitialStartSuccess(){
        String username = user0.getUsername();
        String password = user0.getPassword();
        boolean initialStart = bridge.initialStart(username, password);
        assertTrue(initialStart);
        boolean isAdmin = bridge.getAdminUsername().equals(username);
        assertTrue(isAdmin);
    }

    @Test
    public void testInitialStartFail(){
        String username0 = user0.getUsername();
        String password0 = user0.getPassword();
        String username1 = user1.getUsername();
        String password1 = user1.getPassword();
        boolean initialStart0 = bridge.initialStart(username0, password0);
        boolean initialStart1 = bridge.initialStart(username1, password1);
        assertTrue(initialStart0);
        assertFalse(initialStart1);
        assertFalse(bridge.getAdminUsername().equals(username1));
    }
    @After
    public void tearDown(){
        // allowed because current user is admin
        bridge.resetSystem();
    }
}
