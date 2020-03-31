package UserTests;
import Domain.Guest;
import Domain.Subscribe;
import Domain.User;
import Domain.UserState;
import org.junit.Before;
import org.junit.Test;
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
        testLoginGuest();
        testLoginSubscribe();
    }

    protected void testLoginGuest() {
    }

    protected void testLoginSubscribe() {
        initSubscribe();
    }

    protected class GuestStub extends Guest {

        @Override
        public boolean login(User user, Subscribe subscribe) {
            return false;
        }
    }

    protected class SubscribeStub extends Subscribe {

        public SubscribeStub(String userName, String password) {
            super(userName, password);
        }
    }
}
