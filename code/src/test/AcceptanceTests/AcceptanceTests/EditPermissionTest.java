package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PermissionTestData;
import AcceptanceTests.AcceptanceTestDataObjects.PermissionsTypeTestData;
import AcceptanceTests.AcceptanceTestDataObjects.StoreTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.Before;
import org.junit.Test;

public class EditPermissionTest extends AcceptanceTests {
    UserTestData userToCheck;
    @Before
    public void setUp(){
        super.setUp();
        addStores(stores);
        //TODO add stores need to set owners
        userToCheck =users.get(1);
        bridge.login(superUser.getUsername(),superUser.getPassword());
        bridge.appointManager(stores.get(0).getStoreName(),userToCheck.getUsername());

    }

    /*****************Add-Permission -4.6.1***********************************/

    @Test
    public void addPermissionSuccess(){
        boolean approval=bridge.addPermissionToManager(stores.get(0).getStoreName(),userToCheck.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertTrue(approval);
        StoreTestData store= bridge.getStoreInfoByName(stores.get(0).getStoreName());
        PermissionTestData permission= store.getPermissions().get(userToCheck.getUsername());
        assertTrue(permission.containsPermission( PermissionsTypeTestData.PRODUCTS_INVENTORY));
    }

    @Test
    public void addPermissionFailStoreNotExist(){
        boolean approval=bridge.addPermissionToManager("InvalidStore",userToCheck.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    @Test
    public void addPermissionFailNotMyStore(){
        boolean approval=bridge.addPermissionToManager(stores.get(2).getStoreName(),userToCheck.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    @Test
    public void addPermissionNotManager(){
        boolean approval=bridge.addPermissionToManager(stores.get(0).getStoreName(),users.get(2).getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    @Test
    public void addPermissionAlreadyHasPermission(){
        bridge.addPermissionToManager(stores.get(0).getStoreName(),userToCheck.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        boolean approval=bridge.addPermissionToManager(stores.get(0).getStoreName(),userToCheck.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    /***************DELETE-PERMISSION-4.6.2************************************/
    @Test
    public void deletePermissionSuccess(){
        //TODO
    }
    @Test
    public void deletePermissionFailInvalidStore(){
        boolean approval=bridge.deletePermission("InvalidStore",userToCheck.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }
    @Test
    public void deletePermissionFailNotMyStore(){
        boolean approval=bridge.deletePermission(stores.get(2).getStoreName(),userToCheck.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    @Test
    public void deletePermissionFailNotManager(){
        boolean approval=bridge.deletePermission(stores.get(0).getStoreName(),users.get(2).getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    @Test
    public void deletePermissionFailPermissionNotExist(){
        boolean approval=bridge.deletePermission(stores.get(0).getStoreName(),userToCheck.getUsername(), PermissionsTypeTestData.OWNER);
        assertFalse(approval);
    }

    @Before
    public void tearDown(){
        deleteStores(stores);
        bridge.logout(superUser.getUsername());
        bridge.deleteManager(stores.get(0).getStoreName(),users.get(1).getUsername());

    }

}
