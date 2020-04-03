package UserTests;

import Data.*;
import DataAPI.ProductData;
import Domain.*;
import org.junit.Before;

import static org.junit.Assert.*;

public class UserRealTest extends UserAllStubsTest{
    @Before
    public void setUp(){
        testData=new TestData();
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
        assertEquals(user.getPassword(),testData.getSubscribe(Data.VALID).getPassword());
        assertEquals(user.getUserName(),testData.getSubscribe(Data.VALID).getName());
    }


    /**
     * test 4.1.1 use case -add product
     */
    @Override
    protected void testAddProductToStoreSubscribe() {
        super.testAddProductToStoreSubscribe();
        Product product=((Subscribe) user.getState()).getPermissions().get(testData.getStore(Data.VALID).getName()).
                getStore().getProducts().get(testData.getProductData(Data.VALID).getProductName());
        assertTrue(product.equal(testData.getProductData(Data.VALID)));
    }

    /**
     * test 4.1.2 use case -
     */
    @Override
    protected void testRemoveProductFromStoreSubscribe() {
        super.testRemoveProductFromStoreSubscribe();
        Subscribe sub=(Subscribe)user.getState();
        assertFalse(sub.getPermissions().containsKey(testData.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.3 -edit product
     */
    @Override
    protected void testEditProductFromStoreSubscribe() {
        super.testEditProductFromStoreSubscribe();
        ProductData product=testData.getProductData(Data.EDIT);
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
                .containsKey(testData.getSubscribe(Data.ADMIN).getName()));
    }
}
