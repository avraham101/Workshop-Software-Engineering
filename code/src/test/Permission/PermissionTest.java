package Permission;

import Data.Data;
import Data.TestData;
import Domain.Permission;
import Domain.PermissionType;
import Domain.Subscribe;
import org.junit.Before;
import org.junit.Test;
//class for Unit test all stubs
import java.util.HashSet;

import static org.junit.Assert.*;

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

    @Test
    public void test(){
        testAddType();
    }

    /**
     * test add type
     */

    private void testAddType() {
        testAddTypeSuccess();
        testAddTypeAgainFail();
        testAddOwner();
        testAddTypeWhenOwnerFail();
    }

    private void testAddOwner() {
        assertTrue(permission.addType(PermissionType.OWNER));
        assertEquals(1, permissionTypes.size());
        assertTrue(permissionTypes.contains(PermissionType.OWNER));
    }

    private void testAddTypeAgainFail() {
        assertFalse(permission.addType(PermissionType.PRODUCTS_INVENTORY));
    }

    private void testAddTypeWhenOwnerFail(){
        assertFalse(permission.addType(PermissionType.REMOVE_MANAGER));
    }

    private void testAddTypeSuccess() {
        assertTrue(permission.addType(PermissionType.PRODUCTS_INVENTORY));
    }
}
