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
        loginTest();
    }

    private void loginTest() {
        assertTrue(guest.login(new User(),new Subscribe("yuval","sabag")));
    }
}