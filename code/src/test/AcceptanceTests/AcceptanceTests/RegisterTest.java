package AcceptanceTests.AcceptanceTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class RegisterTest extends AcceptanceTests {

    private String username;
    private String password;

    @Before
    public void setUp(){
        username = users.get(2).getUsername();
        password = users.get(2).getPassword();
    }

    @Test
    public void testRegisterSuccess(){
       boolean isRegistered = bridge.register(username, password);
       assertTrue(isRegistered);
    }

    @Test
    public void testRegisterFailAlreadyRegistered(){
        testRegisterSuccess();
        boolean isRegistered = bridge.register(username, password);
        assertFalse(isRegistered);
    }
}
