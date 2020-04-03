package UserTests;

import Data.*;
import DataAPI.ProductData;
import Domain.*;
import org.junit.Before;

import static org.junit.Assert.*;

public class UserRealTest extends UserAllStubsTest{
    @Before
    public void setUp(){
        data =new TestData();
        user=new User();
    }

    //don't need init cause the state is changing in the login method
    @Override
    protected void initSubscribe(){
        return;
    }

    /**
     * test use case 2.3 - Login
     * user: Yuval Sabag
     */
    protected void testLoginGuest(){
        super.testLoginGuest();
        assertEquals(user.getPassword(), data.getSubscribe(Data.VALID).getPassword());
        assertEquals(user.getUserName(), data.getSubscribe(Data.VALID).getName());
    }


    /**
     * test 4.1.1 use case -add product
     */
    @Override
    protected void testAddProductToStoreSubscribe() {
        super.testAddProductToStoreSubscribe();
        Product product=((Subscribe) user.getState()).getPermissions().get(data.getStore(Data.VALID).getName()).
                getStore().getProducts().get(data.getProductData(Data.VALID).getProductName());
        assertTrue(product.equal(data.getProductData(Data.VALID)));
    }

    /**
     * test 4.1.2 use case -
     */
    @Override
    protected void testRemoveProductFromStoreSubscribe() {
        super.testRemoveProductFromStoreSubscribe();
        Subscribe sub=(Subscribe)user.getState();
        assertFalse(sub.getPermissions().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.3 -edit product
     */
    @Override
    protected void testEditProductFromStoreSubscribe() {
        super.testEditProductFromStoreSubscribe();
        ProductData product= data.getProductData(Data.EDIT);
        Subscribe sub=(Subscribe) user.getState();
        assertTrue(sub.getPermissions().get(product.getStoreName()).getStore()
                .getProducts().get(product.getProductName()).equal(product));
    }

    /**
     * use case 4.5 - add manager
     */
    @Override
    protected void testAddManagerSubscribe() {
        super.testAddManagerSubscribe();
        Subscribe sub=(Subscribe) user.getState();
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
     * 4.6.1 - add permission
     */
    @Override
    protected void testAddPermissionsSubscribe() {
        super.testAddPermissionsSubscribe();
        Subscribe sub=(Subscribe) user.getState();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType()
                .containsAll(data.getPermissionTypeList()));
    }

    /**
     * check use case 4.6.2 - remove permissions
     */
    @Override
    protected void testRemovePermissionsSubscibe() {
        super.testRemovePermissionsSubscibe();
        Subscribe sub=(Subscribe) user.getState();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType().
                isEmpty());
    }
}
