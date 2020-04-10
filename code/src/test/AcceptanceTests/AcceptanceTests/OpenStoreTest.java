package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.*;
import static org.junit.Assert.*;

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
       StoreTestData store = bridge.openStore(newStoreName);
       assertNotNull(store);
       products.get(0).setStoreName(newStoreName);
       boolean isOwner = bridge.addProduct(products.get(0));
       assertTrue(isOwner);
    }

    @Test
    public void openStoreFailNameAlreadyExist(){
        StoreTestData store2= bridge.openStore(newStoreName);
        assertNull(store2);
    }


}