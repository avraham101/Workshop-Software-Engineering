package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ApplicationToStoreTest extends AcceptanceTests {
    UserTestData user;
    @Before
    public void setUp(){
        addStores(stores);
        user = users.get(2);
        registerAndLogin(user);
    }

    @Test
    public void applicationToStoreTestSuccess(){
        boolean approval= bridge.sendApplicationToStore(stores.get(0).getStoreName(),"message");
        assertTrue(approval);
    }

    @Test
    public void applicationToStoreFailInvalidStoreName(){
        boolean approval= bridge.sendApplicationToStore("invalid","message");
        assertFalse(approval);
    }

    @Test
    public void applicationToStoreFailNotSubscribed(){
        boolean approval= bridge.sendApplicationToStore(stores.get(0).getStoreName(),"message");
        assertFalse(approval);
    }


}
