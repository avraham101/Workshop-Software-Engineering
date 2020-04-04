package AcceptanceTests.AcceptanceTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegisterTest extends AcceptanceTests {

    private String username;
    private String password;

    @Before
    public void setUp(){
        super.setUp();
        username = superUser.getUsername();
        password = superUser.getPassword();
    }

    @Test
    public void testRegisterSuccess(){
       boolean isRegistered = bridge.register(username, password);
       assertTrue(isRegistered);
       boolean isAdmin = bridge.getAdminUsername().equals(username);
       assertTrue(isAdmin);
    }

    @Test
    public void testRegisterFailAlreadyRegistered(){
        bridge.register(username, password);
        boolean isRegistered = bridge.register(username, password);
        assertFalse(isRegistered);
    }
    @After
    public void tearDown(){
        bridge.deleteUser(username);
    }
}
