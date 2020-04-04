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
        super.setUp();
        String currUsername = bridge.getCurrentLoggedInUser();
        bridge.logout(currUsername);
        username = superUser.getUsername();
        password = superUser.getPassword();
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
    public void testLoginFailWrongPassword(){
        boolean isLoggedInWrongPassword = bridge.login(username,password+"a");
        String currUsername = bridge.getCurrentLoggedInUser();
        assertFalse(isLoggedInWrongPassword);
        assertNull(currUsername);

    }
    @Test
    public void testLoginFailNotExistUser(){
        boolean isLoggedInNotExist = bridge.login(notExistUserName,notExistPassword);
        String currUsername = bridge.getCurrentLoggedInUser();
        assertFalse(isLoggedInNotExist);
        assertNull(currUsername);
    }

    @Test
    public void testLoginFailAlreadyLoggedIn(){
        testLoginSuccess();
        boolean isLoggedIn = bridge.login(username,password);
        assertFalse(isLoggedIn);
    }

    @After
    public void tearDown(){
        deleteUser(username);
    }
}
