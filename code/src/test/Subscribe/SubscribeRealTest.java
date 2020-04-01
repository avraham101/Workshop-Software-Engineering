package Subscribe;

import Data.Data;
import DataAPI.StoreData;
import Domain.Permission;
import Domain.PermissionType;
import Domain.Store;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SubscribeRealTest extends SubscribeAllStubsTest {

    @Override
    protected void openStoreTest() {
        StoreData storeData=data.getStore(Data.VALID);
        Store store = sub.openStore(storeData,paymentSystem,supplySystem);
        assertEquals(storeData.getName(), store.getName());
        assertEquals(storeData.getDiscountPolicy(), store.getDiscout());
        assertEquals(storeData.getPurchesPolicy(), store.getPurchesPolicy());
        //test Owner permissions
        HashMap<String, Permission> permissions = sub.getPermissions();
        assertTrue(permissions.containsKey(store.getName()));
        Permission permission = permissions.get(store.getName());
        assertTrue(permission.getPermissionType().contains(PermissionType.OWNER));
    }

    @Override
    protected void addProductToStoreTest() {
        addProductToStoreTestFail();
        super.addProductToStoreTest();
    }

    private void addProductToStoreTestFail() {
        testAddProductNotManagerOfStore();
        testAddProductDontHavePermission();
    }

    private void testAddProductDontHavePermission() {
        String validStoreName=data.getProduct(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addProductToStore(data.getProduct(Data.VALID)));
        permission.addType(PermissionType.OWNER);
    }

    private void testAddProductNotManagerOfStore() {
        String validStoreName=data.getProduct(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.addProductToStore(data.getProduct(Data.VALID)));
        sub.getPermissions().put(validStoreName,permission);
    }

}
