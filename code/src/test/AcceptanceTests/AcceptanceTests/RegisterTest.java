package AcceptanceTests.AcceptanceTests;

import org.junit.Test;

public class RegisterTest extends AcceptanceTests {
    @Test
    public void testRegisterSuccess(){
       String username = users.get(0).getUsername();
       String password = users.get(0).getPassword();

       boolean isRegistered = bridge.register(username, password);
       assertTrue(isRegistered);
    }

    @Test
    public void testRegisterFail(){
        String username = users.get(0).getUsername();
        String password = users.get(0).getPassword();

        bridge.register(username, password);
        boolean isRegistered = bridge.register(username, password);
        assertFalse(isRegistered);
    }

    public void tearDown(){
        bridge.deleteUser(users.get(0).getUsername());
    }
}
