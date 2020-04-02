package Subscribe;

import Data.Data;
import Data.TestData;
import DataAPI.ProductData;
import Data.Data;
import DataAPI.StoreData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class SubscribeAllStubsTest {

    protected Subscribe sub;
    protected PaymentSystem paymentSystem;
    protected SupplySystem supplySystem;
    protected TestData data;

    @Before
    public void setUp(){
        sub=new Subscribe("Yuval","Sabag");
        data=new TestData();
        initStore();
    }

    private void initStore() {
        paymentSystem = new ProxyPayment();
        supplySystem = new ProxySupply();
    }

    /**
     * test use case 2.3 - Login
     * main test function for subscribe
     */
    @Test
    public void test(){
        loginTest();
        openStoreTest();
        testAddManagerToStore();
        addProductToStoreTest();
        testEditProduct();
        removeProductFromStoreTest();
        logoutTest();
    }

    /**
     * part of test use case 2.3 - Login.
     * test login where all fields are stubs
     */
    protected void loginTest() {
        assertFalse(sub.login(new User(),new Subscribe("avraham","calev")));
    }


    /**
     * test use case 3.2 - Open Store.
     * store: Niv shiraze store added.
     */
    protected void openStoreTest() {
        Store store = sub.openStore(data.getStore(Data.VALID),paymentSystem,supplySystem);
        assertNotNull(store);

    }

    /**
     * test: use case 3.1 - Logout
     */
    protected void logoutTest(){
        assertTrue(sub.logout(new User()));
    }

    /**
     * test 4.9.1 use case 4.9.1 - add product
     */
    protected void addProductToStoreTest(){
        addProductToStoreTestSuccess();
    }

    private void addProductToStoreTestSuccess(){
        assertTrue(sub.addProductToStore(data.getProduct(Data.VALID)));
    }

    /**
     * test 4.9.2 - remove product
     */

    protected  void removeProductFromStoreTest(){
        checkRemoveProductFail();
        checkRemoveProductSuccess();
    }

    private void checkRemoveProductSuccess() {
        String storeName=data.getProduct(Data.VALID).getStoreName();
        String productName=data.getProduct(Data.VALID).getProductName();
        assertTrue(sub.removeProductFromStore(storeName, productName));
        assertFalse(sub.getPermissions().get(storeName).getStore().getProducts().containsKey(productName));
    }

    private void checkRemoveProductFail() {
        checkRemoveProductHasNoPermission();
        checkRemoveProductNotManager();
    }

    private void checkRemoveProductNotManager() {
        String validStoreName=data.getProduct(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.removeProductFromStore(data.getProduct(Data.VALID).getProductName(),validStoreName));
        sub.getPermissions().put(validStoreName,permission);
    }

    private void checkRemoveProductHasNoPermission() {
        String validStoreName=data.getProduct(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.removeProductFromStore(data.getProduct(Data.VALID).getProductName(),validStoreName));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * test use case 4.1.3 - edit product
     */
    protected void testEditProduct(){
        testFailEditProduct();
        testSuccessEditProduct();
    }

    protected void testFailEditProduct() {
        checkEditProductHasNoPermission();
        checkEditProductNotManager();
    }

    private void checkEditProductNotManager() {
        String validStoreName=data.getProduct(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.editProductFromStore(data.getProduct(Data.VALID)));
        sub.getPermissions().put(validStoreName,permission);
    }

    private void checkEditProductHasNoPermission() {
        String validStoreName=data.getProduct(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.editProductFromStore(data.getProduct(Data.VALID)));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * success case
     */

    protected void testSuccessEditProduct(){
        assertTrue(sub.editProductFromStore(data.getProduct(Data.EDIT)));
    }


    /**
     * use case 4.5 add manager
     */
    protected void testAddManagerToStore(){
        testAddManagerToStoreFail();
        testAddManagerStoreSuccess();
        testAlreadyManager();
    }

    /**
     * test we cant add manager twice
     */
    private void testAlreadyManager() {
        assertFalse(sub.addManager(data.getSubscribe(Data.ADMIN),data.getStore(Data.VALID).getName()));
    }

    protected void testAddManagerStoreSuccess() {
        assertTrue(sub.addManager(data.getSubscribe(Data.ADMIN),data.getStore(Data.VALID).getName()));
    }


    private void testAddManagerToStoreFail() {
        checkAddManagerHasNoPermission();
        checkAddManagerNotOwner();
    }

    /**
     * check cant add manager without being owner
     */

    private void checkAddManagerNotOwner() {
        String validStoreName=data.getProduct(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.addManager(data.getSubscribe(Data.ADMIN),validStoreName));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * check cant add manager without permission
     */

    private void checkAddManagerHasNoPermission() {
        String validStoreName = data.getProduct(Data.VALID).getStoreName();
        Permission permission = sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addManager(data.getSubscribe(Data.ADMIN),validStoreName));
        permission.addType(PermissionType.OWNER);
    }


}