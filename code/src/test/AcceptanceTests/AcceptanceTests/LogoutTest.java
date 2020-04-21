package AcceptanceTests.AcceptanceTests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * use case 3.1 - Logout
 */

public class LogoutTest extends AcceptanceTests {

    //TODO yessssssssssssssssssssssssssssssssssss
    @Before
    public void setUp(){
        registerAndLogin(superUser);
    }

    @Test
    public void logoutTestSuccess(){
        boolean isLoggedOut = bridge.logout(superUser.getId());
        assertTrue(isLoggedOut);
    }

    @Test
    public void logoutTestFailAlreadyLoggedOut(){
        boolean isLoggedOut = bridge.logout(superUser.getId());
        assertTrue(isLoggedOut);
        isLoggedOut = bridge.logout(superUser.getId());
        assertFalse(isLoggedOut);
    }

    @Test(expected = NullPointerException.class)
    public void logoutTestFailInvalidId(){
        bridge.logout(-1);
    }
}
