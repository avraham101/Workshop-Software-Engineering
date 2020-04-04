package AcceptanceTests.AcceptanceTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginTest extends AcceptanceTests {

    private String username;
    private String password;
    private String notExistUserName;
    private String notExistPassword;

    @Before
    public void setUp(){
        String currUsername = bridge.getCurrentLoggedInUser();
        bridge.logout(currUsername);
        username = users.get(0).getUsername();
        password = users.get(0).getPassword();
        bridge.register(username,password);
        notExistUserName = username + username;
        notExistPassword = password + password;

    }

    @Test
    public void testLoginSuccess() {
        boolean loggedIn = bridge.login(username, password);
        assertTrue(loggedIn);
        String currUsername = bridge.getCurrentLoggedInUser();
        assertEquals(username, currUsername);
    }

    @Test
    public void testLoginFail(){
        boolean isLoggedInWrongPassword = bridge.login(username,password+"a");
        String currUsername = bridge.getCurrentLoggedInUser();
        assertFalse(isLoggedInWrongPassword);
        assertNull(currUsername);
        boolean isLoggedInNotExist = bridge.login(notExistUserName,notExistPassword);
        currUsername = bridge.getCurrentLoggedInUser();
        assertFalse(isLoggedInNotExist);
        assertNull(currUsername);
    }


    @After
    public void tearDown(){
        bridge.logout(username);
    }
}
