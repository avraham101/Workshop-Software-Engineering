package Permission;

import Data.Data;
import Data.TestData;
import Domain.Permission;
import DataAPI.PermissionType;
import Domain.Subscribe;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

//class for Unit test all stubs

public class PermissionTest {
    private TestData data;
    private Subscribe sub;
    private HashSet<PermissionType> permissionTypes;
    private Permission permission;

    @Before
    public void setUp() {
        data=new TestData();
        sub=data.getSubscribe(Data.VALID);
        permissionTypes=new HashSet<>();
        permission=new Permission(sub,permissionTypes);
    }

    /**
     * test add type
     */
    @Test
    public void testAddType() {
        testAddTypeSuccess();
        testAddTypeAgainFail();
        testAddOwner();
        testAddTypeWhenOwnerFail();
    }

    /**
     * test add type basic success case
     */
    public void testAddTypeSuccess() {
        assertTrue(permission.addType(PermissionType.PRODUCTS_INVENTORY));
    }

    /**
     * test add owner
     */
    public void testAddOwner() {
        assertTrue(permission.addType(PermissionType.OWNER));
        assertEquals(1, permissionTypes.size());
        assertTrue(permissionTypes.contains(PermissionType.OWNER));
    }

    /**
     * test add the same type twice fail
     */
    private void testAddTypeAgainFail() {
        assertFalse(permission.addType(PermissionType.PRODUCTS_INVENTORY));
    }

    /**
     * test add type when the permission is owner, fails
     */
    private void testAddTypeWhenOwnerFail(){
        assertFalse(permission.addType(PermissionType.ADD_MANAGER));
        assertFalse(permission.getPermissionType().contains(PermissionType.ADD_MANAGER));
    }


}
