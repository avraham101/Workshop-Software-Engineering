package AcceptanceTests.AcceptanceTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LogoutTest extends AcceptanceTests {
    private String username;

    @Before
    public void setUp(){
        super.setUp();
        username = superUser.getUsername();
        String password = superUser.getPassword();
        bridge.register(username, password);
        bridge.login(username, password);
    }

    @Test
    public void logoutTestSuccess(){
        boolean isLoggedOut = bridge.logout(username);
        assertTrue(isLoggedOut);
        String currentUser = bridge.getCurrentLoggedInUser();
        assertNull(currentUser);
    }

    @Test
    public void logoutTestFailAlreadyLoggedOut(){
        boolean isLoggedOut = bridge.logout(username);
        assertTrue(isLoggedOut);
        isLoggedOut = bridge.logout(username);
        assertFalse(isLoggedOut);
    }

    @After
    public void tearDown(){
        deleteUser(username);
    }
}
