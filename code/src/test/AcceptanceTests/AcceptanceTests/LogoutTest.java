package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LogoutTest extends AcceptanceTests {
    private UserTestData user0;
    @Before
    public void setUp(){
        super.setUp();
        user0 = users.get(0);
        bridge.register(user0.getUsername(),user0.getPassword());
        bridge.login(user0.getUsername(),user0.getPassword());
    }

    @Test
    public void logoutTestSuccess(){
        boolean isLoggedOut = bridge.logout(user0.getUsername());
        assertTrue(isLoggedOut);
        String currentUser = bridge.getCurrentLoggedInUser();
        assertNull(currentUser);
    }

    @Test
    public void logoutTestFail(){
        boolean isLoggedOut = bridge.logout(user0.getUsername());
        assertTrue(isLoggedOut);
        isLoggedOut = bridge.logout(user0.getUsername());
        assertFalse(isLoggedOut);
    }

    @After
    public void tearDown(){
        bridge.deleteUser(user0.getUsername());
    }
}
