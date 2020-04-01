package AcceptanceTests.AcceptanceTests;

import org.junit.After;
import org.junit.Test;

public class InitialStartTest extends AcceptanceTests {

    @Test
    public void testInitialStartSuccess(){
        String username = users.get(0).getUsername();
        String password = users.get(0).getPassword();
        boolean initialStart = bridge.initialStart(username, password);
        assertTrue(initialStart);
        boolean isAdmin = bridge.getAdminUsername().equals(username);
        assertTrue(isAdmin);
    }

    @Test
    public void testInitialStartFail(){
        String username0 = users.get(0).getUsername();
        String password0 = users.get(0).getPassword();
        String username1 = users.get(1).getUsername();
        String password1 = users.get(1).getPassword();
        boolean initialStart0 = bridge.initialStart(username0, password0);
        boolean initialStart1 = bridge.initialStart(username1, password1);
        assertTrue(initialStart0);
        assertFalse(initialStart1);
        assertFalse(bridge.getAdminUsername().equals(username1));
    }
    @After
    public void tearDown(){
        bridge.resetSystem();
    }
}
