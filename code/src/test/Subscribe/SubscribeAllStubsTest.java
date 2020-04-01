package Subscribe;

import Data.Data;
import Data.TestData;
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
    protected StoreData storeData;
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
        storeData = new StoreData("Shirazi's", new PurchesPolicy(), new DiscountPolicy());
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
        addProductToStoreTest();
        logoutTest();
    }

    /**
     * part of test use case 2.3 - Login
     * test login where all fields are stubs
     */
    protected void loginTest() {
        assertFalse(sub.login(new User(),new Subscribe("avraham","calev")));
    }


    /**
     * test use case 3.2 - Open Store
     * store: Niv shiraze store added.
     */
    private void openStoreTest() {
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

    /**
     * test: use case 3.1 - Logout
     */
    protected  void logoutTest(){
        assertTrue(sub.logout(new User()));
    }

    /**
     * test 4.9.1 use case 4.9.1 - add product
     */
    protected void addProductToStoreTest(){
        addProductToStoreTestFail();
        addProductToStoreTestSuccess();
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
        sub.getPermissions().put("Store",permission);
    }

    private void addProductToStoreTestSuccess(){
        assertTrue(sub.addProductToStore(data.getProduct(Data.VALID)));
    }



    private class PermissionStub extends Permission{

        public PermissionStub(Subscribe owner, Store store) {
            super(owner, store);
        }


    }
}