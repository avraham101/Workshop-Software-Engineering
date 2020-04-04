package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppointManagerTest extends AcceptanceTests  {
    @Before
    public void setUp(){
        super.setUp();
        bridge.login(superUser.getUsername(),superUser.getPassword());
    }

    @Test
    public void appointManagerSuccess(){
        StoreTestData store = stores.get(0);
        UserTestData newManager = users.get(2);
        boolean approval =bridge.appointManager(store.getStoreName(),newManager.getUsername());
        assertTrue(approval);
        StoreTestData newStore= bridge.getStoreInfoByName(store.getStoreName());
        assertTrue(newStore.isManager(newManager.getUsername()));

        //TODO should i delete the manager

    }

    @Test
    public void appointMangerFailManagerAlreadyExist(){
        StoreTestData store = stores.get(0);
        UserTestData newManager = users.get(2);
        bridge.appointManager(store.getStoreName(),newManager.getUsername());
        boolean approval =bridge.appointManager(store.getStoreName(),newManager.getUsername());
        assertFalse(approval);
    }

    @Test
    public void appointManagerFailInvalidUserName(){
        StoreTestData store = stores.get(0);
        boolean approval =bridge.appointManager(store.getStoreName(),"guest");
        assertFalse(approval);
    }

    @Test
    public void appointManagerFailNotMyStore(){
        StoreTestData store = stores.get(2);
        UserTestData newManager = users.get(2);
        boolean approval =bridge.appointManager(store.getStoreName(),newManager.getUsername());
        assertFalse(approval);
    }

    @After
    public void tearDown(){
        bridge.logout(superUser.getUsername());
    }
}
