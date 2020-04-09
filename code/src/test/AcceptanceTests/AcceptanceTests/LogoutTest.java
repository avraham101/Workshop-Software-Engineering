package AcceptanceTests.AcceptanceTests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class LogoutTest extends AcceptanceTests {

    @Before
    public void setUp(){
        registerAndLogin(superUser);
    }

    @Test
    public void logoutTestSuccess(){
        boolean isLoggedOut = bridge.logout();
        assertTrue(isLoggedOut);
        String currentUser = bridge.getCurrentLoggedInUser();
        assertNull(currentUser);
    }

    @Test
    public void logoutTestFailAlreadyLoggedOut(){
        boolean isLoggedOut = bridge.logout();
        assertTrue(isLoggedOut);
        isLoggedOut = bridge.logout();
        assertFalse(isLoggedOut);
    }
}
