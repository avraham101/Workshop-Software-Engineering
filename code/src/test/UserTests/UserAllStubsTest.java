package UserTests;
import Domain.Guest;
import Domain.Subscribe;
import Domain.User;
import Domain.UserState;
import org.junit.Before;
import org.junit.Test;
//class for Unit test all stubs
import static org.junit.Assert.*;
//class for Unit test all stubs


public class UserAllStubsTest {

    protected User user;
    protected UserState userState;


    @Before
    public void setUp() {
        initGuest();
    }

    protected void initGuest() {
        userState = new GuestStub();
        user = new User(userState);
    }

    protected void initSubscribe() {
        String userName = null;
        String password = null;
        userState = new SubscribeStub(userName, password);
        user = new User(userState);
    }

    @Test
    public void testLogin() {
        testGuest();
        testSubscribe();
    }

    private void testSubscribe() {
        initSubscribe();
        testLoginSubscribe();
    }

    /**
     * login is last test of these sequence
     */
    protected void testGuest() {
        //last guest test
        testLoginGuest();
    }

    protected void testLoginGuest() {
        assertTrue(user.login(new Subscribe("niv","shirazi")));
    }

    protected void testLoginSubscribe() {
        assertFalse(user.login(new Subscribe("niv","shirazi")));
    }

    protected class GuestStub extends Guest {

        @Override
        public boolean login(User user, Subscribe subscribe) {
            return true;
        }
    }

    protected class SubscribeStub extends Subscribe {

        public SubscribeStub(String userName, String password) {
            super(userName, password);
        }

        @Override
        public boolean login(User user, Subscribe subscribe) {
            return false;
        }
    }
}
