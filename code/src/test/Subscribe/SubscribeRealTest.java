package Subscribe;

import Data.Data;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.Permission;
import Domain.PermissionType;
import Domain.Store;
import Domain.Subscribe;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SubscribeRealTest extends SubscribeAllStubsTest {

    @Override
    protected void openStoreTest() {
        StoreData storeData=data.getStore(Data.VALID);
        Store store = sub.openStore(storeData,paymentSystem,supplySystem);
        assertEquals(storeData.getName(), store.getName());
        assertEquals(storeData.getDiscountPolicy(), store.getDiscount());
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
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addProductToStore(data.getProductData(Data.VALID)));
        permission.addType(PermissionType.OWNER);
    }

    private void testAddProductNotManagerOfStore() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.addProductToStore(data.getProductData(Data.VALID)));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * use case 4.1.3
     */
    @Override
    protected void testSuccessEditProduct() {
        super.testSuccessEditProduct();
        ProductData product=data.getProductData(Data.EDIT);
        assertTrue(sub.getPermissions().get(product.getStoreName()).getStore()
                .getProducts().get(product.getProductName()).equal(product));
    }

    /**
     * use case 4.5 - add manager
     * check also if the permission was added
     */

    @Override
    protected void testAddManagerStoreSuccess() {
        super.testAddManagerStoreSuccess();
        assertTrue(sub.getGivenByMePermissions().get(0).getStore().getPermissions()
                .containsKey(data.getSubscribe(Data.ADMIN).getName()));
        Store store=sub.getGivenByMePermissions().get(0).getStore();
        Subscribe newManager=data.getSubscribe(Data.ADMIN);
        Permission p=store.getPermissions().get(newManager.getName());
        assertNotNull(p);
        assertEquals(p.getStore().getName(),store.getName());
        newManager=p.getOwner();
        assertNotNull(newManager);
        assertTrue(newManager.getPermissions().containsKey(store.getName()));
    }

    /**
     * check use case 4.6.1 - add permission
     */

    @Override
    protected void testAddPermissions() {
        super.testAddPermissions();
        testAddPermissionTwiceFail();
    }

    private void testAddPermissionTwiceFail() {
        assertFalse(sub.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.VALID).getName()));
    }

    //test the permission was really added
    @Override
    protected void testAddPermissionSuccess() {
        super.testAddPermissionSuccess();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType().
                containsAll(data.getPermissionTypeList()));
    }

    /**
     * check use case 4.6.2 - remove permissions
     */
    @Override
    protected void testRemovePermissionSuccess() {
        super.testRemovePermissionSuccess();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType().
                isEmpty());
    }
}
