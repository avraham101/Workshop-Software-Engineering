package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.*;

public class OpenStoreTest extends AcceptanceTests {
    private UserTestData user;
    private String newStoreName;

    @BeforeClass
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
    //TODO should add not subscribe??

    @AfterClass
    public void tearDown(){
        bridge.closeStore(newStoreName);
        bridge.logout();
        deleteUser(user.getUsername());
    }
}
