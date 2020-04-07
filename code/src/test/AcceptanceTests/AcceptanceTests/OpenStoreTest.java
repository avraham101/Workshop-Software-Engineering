package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.*;
import static org.junit.Assert.*;


public class OpenStoreTest extends AcceptanceTests {
    private UserTestData user;
    private String newStoreName;

    @Before
    public void setUp(){
        newStoreName = "store-name";
        user = users.get(1);
        registerAndLogin(user);
    }

    @Test
    public void openStoreSuccess(){
       StoreTestData store = bridge.openStore(newStoreName);
       assertNotNull(store);
       String actualStoreOwner = bridge.getStoreInfoByName(newStoreName).getStoreOwner().getUsername();
       assertEquals(user.getUsername(),actualStoreOwner);
    }

    @Test
    public void openStoreFailNameAlreadyExist(){
        StoreTestData store1= bridge.openStore(newStoreName);
        StoreTestData store2= bridge.openStore(newStoreName);
        assertNull(store2);
    }

    @After
    public void tearDown(){
        bridge.deleteStore(newStoreName);
        bridge.logout();
        deleteUser(user.getUsername());
    }
}
