package Permission;

import Data.Data;
import Data.TestData;
import DataAPI.ProductData;
import DataAPI.Response;
import DataAPI.StoreData;
import Domain.*;
import DataAPI.PermissionType;
import Drivers.LogicManagerDriver;
import Persitent.DaoHolders.DaoHolder;
import Persitent.DaoInterfaces.ICartDao;
import Persitent.DaoInterfaces.IStoreDao;
import Persitent.DaoInterfaces.ISubscribeDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

//class for Unit test all stubs

public class PermissionTest {
    private TestData data;
    private HashSet<PermissionType> permissionTypes;
    private Permission permission;
    private DaoHolder daoHolder;
    private LogicManagerDriver logicDriver;

    @Before
    public void setUp(){
        Utils.Utils.TestMode();
        data = new TestData();
        daoHolder = new DaoHolder();
        try {
            logicDriver = new LogicManagerDriver();
        } catch ( Exception e){
            fail();
        }
        setUpStore();
        daoHolder.getSubscribeDao().addSubscribe(data.getSubscribe(Data.VALID2));
        Subscribe subToAdd = daoHolder.getSubscribeDao().find(data.getSubscribe(Data.VALID2).getName());
        Store store = daoHolder.getStoreDao().find(data.getRealStore(Data.VALID).getName());
        permissionTypes=new HashSet<>();
        permission = new Permission(subToAdd, store);
        daoHolder.getPermissionDao().addPermission(permission);
        permission = daoHolder.getPermissionDao().findPermission(permission);
    }

    @After
    public void tearDown() {
        tearDownStore();
        daoHolder.getPermissionDao().removePermissionFromSubscribe(permission);
        daoHolder.getSubscribeDao().remove(data.getSubscribe(Data.VALID2).getName());
    }

    /**
     * set up for a store
     */
    private void setUpStore() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        ProductData productData = data.getProductData(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        int id = logicDriver.connectToSystem();
        Response<Boolean> response = logicDriver.register(subscribe.getName(), subscribe.getPassword());
        if(response.getValue()) {
            response = logicDriver.login(id, subscribe.getName(), subscribe.getPassword());
            if(response.getValue()) {
                response = logicDriver.openStore(id, storeData);
                if(response.getValue()) {
                    logicDriver.addProductToStore(id, productData);
                }
                else
                    fail();
            }
            else
                fail();
        }
        else
            fail();
    }

    /**
     * tear down for a store
     */
    private void tearDownStore() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        ISubscribeDao subscribeDao = daoHolder.getSubscribeDao();
        subscribeDao.remove(subscribe.getName());
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        subscribeDao.remove(admin.getName());
        StoreData storeData = data.getStore(Data.VALID);
        IStoreDao storeDao = daoHolder.getStoreDao();
        storeDao.removeStore(storeData.getName());
        ICartDao cartDao = daoHolder.getCartDao();
        Cart cart = cartDao.find(subscribe.getName());
        if(cart!=null)
            cartDao.remove(cart);
    }


    /**
     * test add type basic success case
     */
    @Test
    public void testAddTypeSuccess() {
        assertTrue(permission.addType(PermissionType.CRUD_POLICY_DISCOUNT));
    }

    /**
     * test add owner
     */
    @Test
    public void testAddOwner() {
        assertTrue(permission.addType(PermissionType.OWNER));
        permissionTypes = daoHolder.getPermissionDao().findPermission(permission).getPermissionType();
        assertEquals(1, permissionTypes.size());
        assertTrue(permissionTypes.contains(PermissionType.OWNER));
    }

    /**
     * test add the same type twice fail
     */
    @Test
    public void testAddTypeAgainFail() {
        assertTrue(permission.addType(PermissionType.CRUD_POLICY_DISCOUNT));
        assertFalse(permission.addType(PermissionType.CRUD_POLICY_DISCOUNT));
    }

    /**
     * test add type when the permission is owner, fails
     */
    @Test
    public void testAddTypeWhenOwnerFail() {
        assertTrue(permission.addType(PermissionType.OWNER));
        assertFalse(permission.addType(PermissionType.ADD_MANAGER));
        assertFalse(permission.getPermissionType().contains(PermissionType.ADD_MANAGER));
    }


}
