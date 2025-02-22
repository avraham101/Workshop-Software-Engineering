package AcceptanceTests.AcceptanceTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * use case 2.2 - Register
 */

public class RegisterTest extends AcceptanceTests {

    private String username;
    private String password;

    @Before
    public void setUp(){
        username = users.get(2).getUsername();
        password = users.get(2).getPassword();
        removeUser(username);
    }

    @Test
    public void testRegisterSuccess(){
       boolean isRegistered = register(username, password);
       assertTrue(isRegistered);
    }

    @Test
    public void testRegisterFailAlreadyRegistered(){
        testRegisterSuccess();
        boolean isRegistered = register(username, password);
        assertFalse(isRegistered);
    }

    @After
    public void tearDown(){
        removeUser(username);
    }
}
