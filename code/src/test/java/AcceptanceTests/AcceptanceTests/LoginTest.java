package AcceptanceTests.AcceptanceTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Login Tests
 * Use Case 2.3
 */
public class LoginTest extends AcceptanceTests {

    private int id;
    private String username;
    private String password;
    private int notExistId;
    private String notExistUserName;
    private String notExistPassword;

    @Before
    public void setUp(){
        id = users.get(2).getId();
        username = users.get(2).getUsername();
        password = users.get(2).getPassword();
        register(username,password);
        notExistId = id*id;
        notExistUserName = username + username;
        notExistPassword = password + password;
    }

    @Test
    public void testLoginSuccess() {
        boolean loggedIn = bridge.login(id,username, password);
        assertTrue(loggedIn);
    }

    @Test
    public void testLoginFailWrongPassword(){
        boolean isLoggedInWrongPassword = bridge.login(id,username,notExistPassword);
        assertFalse(isLoggedInWrongPassword);

    }
    @Test
    public void testLoginFailNotExistUser(){
        boolean isLoggedInNotExist = bridge.login(id,notExistUserName,notExistPassword);
        assertFalse(isLoggedInNotExist);
    }

    @Test
    public void testLoginFailWrongId(){
        boolean isLoggedIn = bridge.login(id,username,password);
        assertTrue(isLoggedIn);
        isLoggedIn = bridge.login(notExistId,username,password);
        assertFalse(isLoggedIn);
    }

    @Test
    public void testLoginFailAlreadyLoggedIn(){
        testLoginSuccess();
        boolean isLoggedIn = bridge.login(id,username,password);
        assertFalse(isLoggedIn);
    }

    @After
    public void tearDown(){
        removeUser(username);
    }

}
