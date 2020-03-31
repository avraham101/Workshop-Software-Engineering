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

    @Test
    public void test(){
        loginTest();
    }

    protected void loginTest() {
        assertFalse(sub.login(new User(),new Subscribe("avraham","calev")));
    }

}