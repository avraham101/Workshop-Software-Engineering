package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * use case 4.3 - appoint owner
 */

public class AppointAnotherOwnerToStoreTest extends AcceptanceTests {
    private UserTestData owner;
    private UserTestData newOwner;
    private StoreTestData shareOwnershipStore;

    @Before
    public void setUp(){
        addStores(stores);
        addProducts(products);
        owner = superUser;
        newOwner = users.get(1);
        shareOwnershipStore = stores.get(0);
        bridge.register(owner.getUsername(),owner.getPassword());
        bridge.register(newOwner.getUsername(),newOwner.getPassword());
        bridge.login(owner.getId(),owner.getUsername(),owner.getPassword());
        bridge.login(admin.getId(),admin.getUsername(),admin.getPassword());
        bridge.login(newOwner.getId(),newOwner.getUsername(),newOwner.getPassword());

    }

    @Test
    public void appointAnotherOwnerToStoreTestSuccess(){
        boolean isOwner = bridge.appointOwnerToStore(owner.getId(),
                shareOwnershipStore.getStoreName(),newOwner.getUsername());
        boolean approval = bridge.approveManageOwner(owner.getId(),shareOwnershipStore.getStoreName(),newOwner.getUsername()) &&
                bridge.approveManageOwner(admin.getId(), shareOwnershipStore.getStoreName(),newOwner.getUsername());
        assertTrue(isOwner && approval);

    }

    @Test
    public void appointAnotherOwnerToStoreTestFailAlreadyOwner(){
        appointAnotherOwnerToStoreTestSuccess();
        boolean isOwner = bridge.appointOwnerToStore(owner.getId(),
                shareOwnershipStore.getStoreName(),newOwner.getUsername());
        boolean approval = bridge.approveManageOwner(owner.getId(),shareOwnershipStore.getStoreName(),newOwner.getUsername()) &&
                bridge.approveManageOwner(admin.getId(), shareOwnershipStore.getStoreName(),newOwner.getUsername());
        assertFalse(isOwner && approval);

    }

    @Test
    public void appointAnotherOwnerToStoreTestFailWrongUsername(){
        String wrongUsername = newOwner.getUsername() + newOwner.getUsername();
        boolean isOwner = bridge.appointOwnerToStore(owner.getId(),
                shareOwnershipStore.getStoreName(),wrongUsername);
        assertFalse(isOwner);

    }

    @Test
    public void appointAnotherOwnerToStoreTestFailWrongStoreName(){
        String wrongStoreName = shareOwnershipStore.getStoreName() + shareOwnershipStore.getStoreName();
        boolean isOwner = bridge.appointOwnerToStore(owner.getId(),
                wrongStoreName,newOwner.getUsername());
        assertFalse(isOwner);

    }
    @After
    public void tearDown(){
        removeUsers(new ArrayList<>(Arrays.asList(newOwner.getUsername(),owner.getUsername(),admin.getUsername())));
        removeStores(stores);
    }
}
