package Permission;

import Data.Data;
import Data.TestData;
import DataAPI.StoreData;
import Domain.Permission;
import DataAPI.PermissionType;
import Domain.Store;
import Domain.Subscribe;
import Persitent.DaoHolders.DaoHolder;
import Persitent.StoreDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

//class for Unit test all stubs

public class PermissionTest {
    private TestData data;
    private Subscribe sub;
    private Store store;
    private Permission permission;

    @Before
    public void setUp() {
        data=new TestData();
        sub=data.getSubscribe(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        permission=new Permission(sub);
        store= new Store(storeData.getName(), permission, storeData.getDescription());
        permission.setStore(store);
    }

    /**
     * test add type basic success case
     */
    @Test
    public void testAddTypeSuccess() {
        assertTrue(permission.addType(PermissionType.PRODUCTS_INVENTORY));
    }

    /**
     * test add owner
     */
    @Test
    public void testAddOwner() {
        assertTrue(permission.addType(PermissionType.OWNER));
        assertEquals(1, permission.getPermissionType().size());
        assertTrue(permission.getPermissionType().contains(PermissionType.OWNER));
    }

    /**
     * test add the same type twice fail
     */
    @Test
    public void testAddTypeAgainFail() {
        assertFalse(permission.addType(PermissionType.PRODUCTS_INVENTORY));
    }

    /**
     * test add type when the permission is owner, fails
     */
    @Test
    public void testAddTypeWhenOwnerFail(){
        assertFalse(permission.addType(PermissionType.ADD_MANAGER));
        assertFalse(permission.getPermissionType().contains(PermissionType.ADD_MANAGER));
    }

    @After
    public void tearDown() {
        data=new TestData();
    }

}
