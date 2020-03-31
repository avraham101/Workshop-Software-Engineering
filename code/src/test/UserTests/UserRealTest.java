package UserTests;

import Domain.Guest;
import Domain.Subscribe;
import Domain.User;
import org.junit.Before;

import static org.junit.Assert.*;

public class UserRealTest extends UserAllStubsTest{
    @Before
    public void setUp(){
        user=new User();
    }

    //don't need init cause the state is changing in login method
    @Override
    protected void initSubscribe(){
        return;
    }

    /**
     * test use case 2.3 - Login
     * user: niv shirazi
     */
    protected void testLoginGuest(){
        super.testLoginGuest();
        assertEquals(user.getPassword(),"shirazi");
        assertEquals(user.getUserName(),"niv");
    }

    @Override
    protected void testLoginSubscribe(){
        super.testLoginSubscribe();
    }
}
