package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;

public class ApplicationToStoreTest extends AcceptanceTests {
    UserTestData user;
    @Before
    public void setUp(){
        super.setUp();
        addStores(stores);
        user = users.get(1);
    }

    @Test
    public void applicationToStoreTestSuccess(){
        bridge.login(user.getUsername(),user.getPassword());
        boolean approval= bridge.sendApplicationToStore(stores.get(0).getStoreName(),"message");
        assertTrue(approval);

    }

    @Test
    public void applicationToStoreFailInvalidStoreName(){
        bridge.login(user.getUsername(),user.getPassword());
        boolean approval= bridge.sendApplicationToStore("invalid","message");
        assertFalse(approval);
    }

    @Test
    public void applicationToStoreFailNotSubscribe(){
        boolean approval= bridge.sendApplicationToStore(stores.get(0).getStoreName(),"message");
        assertFalse(approval);

    }

    @Before
    public void tearDown(){
        deleteStores(stores);
    }
}
