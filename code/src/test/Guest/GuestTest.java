package Guest;

import Domain.Guest;
import Domain.Subscribe;
import Domain.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GuestTest {
    protected Guest guest;
    @Before
    public void setUp(){
        guest=new Guest();
    }

    /**
     * main test class for guest
     */
    @Test
    public void test(){
        logoutTest();
        loginTest();
        openStoreTest();
    }

    /**
     * test use case 2.3 - Login
     */
    private void loginTest() {
        assertTrue(guest.login(new User(),new Subscribe("yuval","sabag")));
    }

    /**
     * test use case 3.1 - Logout
     */
    private void logoutTest(){assertFalse(guest.logout(new User()));}

    /**
     * test use case 3.2 - Open Store
     */
    private void openStoreTest() {
        assertNull(guest.openStore(null, null, null));
    }
}