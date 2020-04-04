package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppointAnotherOwnerToStoreTest extends AcceptanceTests {
    private UserTestData owner;
    private UserTestData newOwner;
    private StoreTestData shareOwnershipStore;

    @Before
    public void setUp(){
        owner = superUser;
        newOwner = users.get(0);
        shareOwnershipStore = stores.get(0);
        bridge.register(owner.getUsername(),owner.getPassword());
        bridge.register(newOwner.getUsername(),newOwner.getPassword());
        bridge.login(owner.getUsername(),owner.getPassword());
        addStores(stores);
        addProducts(products);
    }

    @Test
    public void appointAnotherOwnerToStoreTestSuccess(){
        boolean isOwner = bridge.appointOwnerToStore(shareOwnershipStore.getStoreName(),newOwner.getUsername());
        assertTrue(isOwner);
        StoreTestData actualStore= bridge.getStoreInfoByName(shareOwnershipStore.getStoreName());
        assertTrue(actualStore.isOwner(newOwner.getUsername()));
    }

    @Test
    public void appointAnotherOwnerToStoreTestFailAlreadyOwner(){
        appointAnotherOwnerToStoreTestSuccess();
        boolean isOwner = bridge.appointOwnerToStore(shareOwnershipStore.getStoreName(),newOwner.getUsername());
        assertFalse(isOwner);
        StoreTestData actualStore= bridge.getStoreInfoByName(shareOwnershipStore.getStoreName());
        assertFalse(actualStore.isOwner(newOwner.getUsername()));

    }

    @Test
    public void appointAnotherOwnerToStoreTestFailWrongUsername(){
        String wrongUsername = newOwner.getUsername() + newOwner.getUsername();
        boolean isOwner = bridge.appointOwnerToStore(shareOwnershipStore.getStoreName(),wrongUsername);
        assertFalse(isOwner);
        StoreTestData actualStore= bridge.getStoreInfoByName(shareOwnershipStore.getStoreName());
        assertFalse(actualStore.isOwner(newOwner.getUsername()));
    }

    @Test
    public void appointAnotherOwnerToStoreTestFailWrongStoreName(){
        String wrongStoreName = shareOwnershipStore.getStoreName() + shareOwnershipStore.getStoreName();
        boolean isOwner = bridge.appointOwnerToStore(wrongStoreName,newOwner.getUsername());
        assertFalse(isOwner);
        StoreTestData actualStore= bridge.getStoreInfoByName(wrongStoreName);
        assertNull(actualStore);
    }

    @After
    public void tearDown(){
        deleteProducts(products);
        deleteStores(stores);
        deleteUser(newOwner.getUsername());
        deleteUser(owner.getUsername());
    }
}
