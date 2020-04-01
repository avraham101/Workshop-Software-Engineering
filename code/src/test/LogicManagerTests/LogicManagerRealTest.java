package LogicManagerTests;

import DataAPI.StoreData;
import Domain.*;
import Systems.HashSystem;
import org.junit.Before;

import static org.junit.Assert.*;

//no stubs full integration
public class LogicManagerRealTest extends LogicManagerUserStubTest {

    @Before
    public void setUp() {
        currUser=new User();
        init();
    }


    /**
     * test use case 2.3 - Login
     */
    @Override
    public void testLogin(){
        super.testLogin();
        testLoginFailAlreadyUserLogged();
    }

    public void testLoginFailAlreadyUserLogged() {
        assertFalse(logicManager.login("Yuval","Sabag"));
    }

    /**
     * part of test use case 2.3 - Login
     */
    @Override
    protected void testLoginSuccess() {
        super.testLoginSuccess();
        assertEquals(currUser.getUserName(),"Yuval");
        try {
            HashSystem hashSystem = new HashSystem();
            String password = hashSystem.encrypt("Sabag");
            assertEquals(password, currUser.getPassword());
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * test use case 3.2 - Open Store
     */
    @Override
    protected void testOpenStore() {
        super.testOpenStore();
        testOpenStorePurchesAndDiscontPolicy();
        testOpenStoreUserPermissions();
        testOpenStoreStorePermissions();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    private void testOpenStorePurchesAndDiscontPolicy() {
        StoreData storeData = data.getStore(Data.VALID);
        Store store = stores.get(storeData.getName());
        assertEquals(storeData.getPurchesPolicy(), store.getPurchesPolicy());
        assertEquals(storeData.getDiscountPolicy(), store.getDiscout());
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    private void testOpenStoreUserPermissions() {
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe subscribe = (Subscribe)currUser.getState();
        Permission permission = subscribe.getPermissions().get(storeData.getName());
        assertTrue(permission.getPermissionType().contains(PermissionType.OWNER));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    private void testOpenStoreStorePermissions() {
        StoreData storeData = data.getStore(Data.VALID);
        Store store = stores.get(storeData.getName());
        assertTrue(store.getPermissions().containsKey(currUser.getUserName()));
        Permission p = store.getPermissions().get(currUser.getUserName());
        assertTrue(p.getPermissionType().contains(PermissionType.OWNER));
    }


    /**
     * test: use case 3.1 - Logout
     */
    @Override
    public void testLogout(){
        super.testLogout();
        //test while in Guest Mode
        assertFalse(currUser.logout());
    }


    /**
     * test use case 4.1.1 -add product to store
     */

    @Override
    protected void testAddProduct(){
        super.testAddProduct();
        testAddProductWithSameName();
    }

    @Override
    protected void testAddProductFail (){
        super.testAddProductFail();
        testAddProductNotManagerOfStore();
        testAddProductDontHavePermission();
    }

    /**
     * test adding product with name that is not unique
     */

    private void testAddProductWithSameName(){
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.SAME_NAME)));
    }

    /**
     * test try adding product without being owner or manager of the store
     */
    private void testAddProductNotManagerOfStore(){
        String validStoreName=data.getProduct(Data.VALID).getStoreName();
        Subscribe sub=((Subscribe) currUser.getState());
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.VALID)));
        sub.getPermissions().put("Store",permission);
    }

    /**
     * test that user that has no CRUD permission or owner permission cant add products to store
     */
    private void testAddProductDontHavePermission(){
        String validStoreName=data.getProduct(Data.VALID).getStoreName();
        Subscribe sub=((Subscribe) currUser.getState());
        Permission permission=sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(logicManager.addProductToStore(data.getProduct(Data.VALID)));
        permission.addType(PermissionType.OWNER);
    }


}

