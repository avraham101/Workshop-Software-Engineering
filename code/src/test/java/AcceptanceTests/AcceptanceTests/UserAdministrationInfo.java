package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PermissionsTypeTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StorePermissionsTypeTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class UserAdministrationInfo extends AcceptanceTests {

    private UserTestData user;
    private StoreTestData store;

    @Before
    public void setUp() {
        addUserStoresAndProducts(superUser);
        setUpManagerPermissionsTestManagers();
    }

    /**
     * set a user permission and stores he managed
     */
    private void setUpManagerPermissionsTestManagers(){
        store = stores.get(0);
        user = users.get(2);
        registerAndLogin(user);
        bridge.appointManager(superUser.getId(),store.getStoreName(), user.getUsername());
        bridge.addPermissionToManager(superUser.getId(),store.getStoreName(),user.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
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
        List<StoreTestData> storesList = bridge.getStoresManagedByUser(users.get(3).getId());
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

    @Test
    public void testGetAllUsers() {
        int id = admin.getId();
        registerAndLogin(admin);
        List<String> users = bridge.getAllUsers(id);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testGetAllUsersNotAnAdmin() {
        int id = superUser.getId();
        registerAndLogin(superUser);
        List<String> users = bridge.getAllUsers(id);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testGetAllUsersNotLogin() {
        int id = superUser.getId();
        List<String> users = bridge.getAllUsers(id);
        assertTrue(users.isEmpty());
    }

    @After
    public void tearDown() {
        removeProducts(products);
        removeStores(stores);
        bridge.logout(user.getId());
        bridge.logout(superUser.getId());
        removeUsers(new ArrayList<>(Arrays.asList(user.getUsername(), superUser.getUsername())));
    }
}
