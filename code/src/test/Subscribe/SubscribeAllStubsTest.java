package Subscribe;

import Domain.Subscribe;
import Domain.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubscribeAllStubsTest {

    protected Subscribe sub;

    @Before
    public void setUp(){
        sub=new Subscribe("Yuval","Sabag");
    }

    /**
     * main test function for subscribe
     */
    @Test
    public void test(){
        loginTest();
        logoutTest();
    }

    /**
     * test login where all fields are stubs
     */
    protected void loginTest() {
        assertFalse(sub.login(new User(),new Subscribe("avraham","calev")));
    }

    protected  void logoutTest(){
        assertTrue(sub.logout(new User()));
    }

}