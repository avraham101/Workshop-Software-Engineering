package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * use case 3.2 - Open Store
 */

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
       StoreTestData store = bridge.openStore(user.getId(),newStoreName);
       assertNotNull(store);

    }

    @Test
    public void openStoreFailNameAlreadyExist(){
        StoreTestData store1= bridge.openStore(user.getId(),newStoreName);
        StoreTestData store2= bridge.openStore(user.getId(),newStoreName);
        assertNull(store2);
    }

    @Test
    public void openStoreFailUserNotLoggedIn(){
        bridge.logout(user.getId());
        StoreTestData store= bridge.openStore(user.getId(),newStoreName);
        assertNull(store);
    }



}