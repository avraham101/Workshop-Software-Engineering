package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PermissionsTypeTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StorePermissionsTypeTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Set;

public class UserAdministrationInfo extends AcceptanceTests {

    private UserTestData user;
    private StoreTestData store;

    @Before
    public void setUp() {
        addStores(stores);
        addProducts(products);
        setUpManagerPermissionsTestManagers();
    }

    /**
     * set a user permission and stores he manage
     */
    private void setUpManagerPermissionsTestManagers(){
        store = stores.get(0);
        user = users.get(2);
        registerAndLogin(superUser);
        registerAndLogin(user);
        bridge.appointManager(superUser.getId(),store.getStoreName(), user.getUsername());
        bridge.addPermissionToManager(superUser.getId(),store.getStoreName(),user.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        logoutAndLogin(user);
        logoutAndLogin(superUser);
    }

    @Test
    public void testGetStoresManagedByUserSuccess() {
        List<StoreTestData> storesList = bridge.getStoresManagedByUser(user.getId());
        assertNotNull(storesList);
    }

    @Test
    public void testGetStoresManagedByUserSuccessGetStoreName() {
        List<StoreTestData> storesList = bridge.getStoresManagedByUser(user.getId());
        assertEquals(storesList.get(0).getStoreName(), store.getStoreName());
    }

    @Test
    public void testGetStoresManagedByUserFail() {
        user = users.get(3);
        List<StoreTestData> storesList = bridge.getStoresManagedByUser(user.getId());
        assertTrue(storesList.isEmpty());
    }

    @Test
    public void testGetStoresManagedByUserFailWrongStore() {
        store = stores.get(1);
        List<StoreTestData> storesList = bridge.getStoresManagedByUser(user.getId());
        assertNotEquals(storesList.get(0).getStoreName(), store.getStoreName());
    }

    @Test
    public void testUserPermissionsByStoreNotNull() {
        int id = user.getId();
        String storeName = store.getStoreName();
        Set<StorePermissionsTypeTestData> storePermission = bridge.getPermissionsForStore(id, storeName);
        assertNotNull(storePermission);
    }

    @Test
    public void testUserPermissionsByStoreNotEmpty() {
        int id = user.getId();
        String storeName = store.getStoreName();
        Set<StorePermissionsTypeTestData> storePermission = bridge.getPermissionsForStore(id, storeName);
        assertFalse(storePermission.isEmpty());
    }

    @Test
    public void testUserPermissionsByStoreRightPermissions() {
        int id = user.getId();
        String storeName = store.getStoreName();
        Set<StorePermissionsTypeTestData> storePermission = bridge.getPermissionsForStore(id, storeName);
        assertTrue(storePermission.contains(StorePermissionsTypeTestData.PRODUCTS_INVENTORY));
    }

        @Test
    public void testUserPermissionsByStoreWrongPermissions() {
        int id = user.getId();
        String storeName = store.getStoreName();
        Set<StorePermissionsTypeTestData> storePermission = bridge.getPermissionsForStore(id, storeName);
        assertFalse(storePermission.contains(StorePermissionsTypeTestData.ADD_MANAGER));
    }



}
