package AcceptanceTests.AcceptanceTests;

import AcceptanceTests.AcceptanceTestDataObjects.PermissionsTypeTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ProductTestData;
import AcceptanceTests.AcceptanceTestDataObjects.ReviewTestData;
import AcceptanceTests.AcceptanceTestDataObjects.UserTestData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class EditPermissionTest extends AcceptanceTests {
    UserTestData userToCheck;
    ProductTestData productToAdd;

    @Before
    public void setUp(){
        userToCheck = users.get(1);
        bridge.register(userToCheck.getUsername(),userToCheck.getPassword());
        addUserStoresAndProducts(superUser);
        bridge.appointManager(superUser.getId(),stores.get(0).getStoreName(),userToCheck.getUsername());

        productToAdd = new ProductTestData("newProductTest",
                stores.get(0).getStoreName(),
                100,
                4,
                "Dairy",
                new ArrayList<ReviewTestData>()
        );
    }

    /**
     * 4.6.1 -  Add-Permission
     */

    @Test
    public void addPermissionSuccess(){
        boolean approval = bridge.addPermissionToManager(superUser.getId(),
                                                        stores.get(0).getStoreName(),
                                                        userToCheck.getUsername(),
                                                        PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertTrue(approval);
        logoutAndLogin(userToCheck);
        boolean isAdded = bridge.addProduct(userToCheck.getId(),productToAdd);
        assertTrue(isAdded);
    }

    @Test
    public void addPermissionFailStoreNotExist(){
        boolean approval = bridge.addPermissionToManager(superUser.getId(),
                                                    "InvalidStore",
                                                        userToCheck.getUsername(),
                                                        PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    @Test
    public void addPermissionFailNotMyStore(){
        boolean approval = bridge.addPermissionToManager(superUser.getId(),
                                                stores.get(2).getStoreName(),
                                                        userToCheck.getUsername(),
                                                        PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    @Test
    public void addPermissionFailNotManager(){
        boolean approval = bridge.addPermissionToManager(superUser.getId(),
                                                    stores.get(0).getStoreName(),
                                                        users.get(2).getUsername(),
                                                        PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    @Test
    public void addPermissionFailAlreadyHasPermission(){
        bridge.addPermissionToManager(superUser.getId(),
                                stores.get(0).getStoreName(),
                                    userToCheck.getUsername(),
                                    PermissionsTypeTestData.PRODUCTS_INVENTORY);
        boolean approval = bridge.addPermissionToManager(superUser.getId(),
                                                stores.get(0).getStoreName(),
                                                        userToCheck.getUsername(),
                                                        PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }
    /**
     * 4.6.2 - delete permission
     */
    private void addPermission(){
        bridge.addPermissionToManager(superUser.getId(),
                stores.get(0).getStoreName(),
                userToCheck.getUsername(),
                PermissionsTypeTestData.PRODUCTS_INVENTORY);
    }

    @Test
    public void deletePermissionSuccess(){
        addPermission();
        boolean approval= bridge.deletePermission(superUser.getId(),stores.get(0).getStoreName(),
                 userToCheck.getUsername(),
                    PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertTrue(approval);
        logoutAndLogin(userToCheck);
        boolean isAdded = bridge.addProduct(userToCheck.getId(),productToAdd);
        assertFalse(isAdded);

    }
    @Test
    public void deletePermissionFailInvalidStore(){
        boolean approval=bridge.deletePermission(superUser.getId(),"InvalidStore",
                                userToCheck.getUsername(),
                                PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }
    @Test
    public void deletePermissionFailNotMyStore(){
        boolean approval=bridge.deletePermission(superUser.getId(),
                stores.get(2).getStoreName(),userToCheck.getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    @Test
    public void deletePermissionFailNotManager(){
        boolean approval=bridge.deletePermission(superUser.getId(),
                stores.get(0).getStoreName(),users.get(2).getUsername(), PermissionsTypeTestData.PRODUCTS_INVENTORY);
        assertFalse(approval);
    }

    @Test
    public void deletePermissionFailPermissionNotExist(){
        boolean approval=bridge.deletePermission(superUser.getId(),
                stores.get(0).getStoreName(),userToCheck.getUsername(), PermissionsTypeTestData.OWNER);
        assertFalse(approval);
    }

    @After
    public void tearDown(){
        removeUserStoresAndProducts(userToCheck);
    }

}
