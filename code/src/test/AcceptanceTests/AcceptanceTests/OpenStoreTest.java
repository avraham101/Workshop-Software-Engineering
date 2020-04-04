package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OpenStoreTest extends AcceptanceTests {
    private UserTestData user;

    @Before
    public void setUp(){
        super.setUp();
        user = users.get(1);
        bridge.login(user.getUsername(),user.getPassword());
    }

    @Test
    public void openStoreSuccess(){
       StoreTestData store= bridge.openStore("store-name");
       assertNotNull(store);
       assertEquals(user,store.getStoreManager());
       //TODO delete store??
    }

    @Test
    public void openStoreFailNameAlreadyExist(){
        StoreTestData store1= bridge.openStore("store-name");
        StoreTestData store2= bridge.openStore("store-name");
        assertNull(store2);
    }
    //TODO should add not subscribe??


    @After
    public void tearDown(){
        bridge.logout(user.getUsername());
    }
}
