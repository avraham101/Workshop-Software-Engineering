package UserTests;

import Data.*;
import DataAPI.ProductData;
import Domain.*;
import org.junit.Before;

import java.util.List;

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
     * use case 2.8 - purchase cart
     */
    @Override
    protected void testPurchaseSubscribe() {
        super.testPurchaseSubscribe();
        List<Purchase> purchases = user.getState().watchMyPurchaseHistory();
        assertEquals(1, purchases.size());
    }

    /**
     * use case 3.3 - write review
     */
    @Override
    protected void testWriteReviewSubscribe() {
        Review review = data.getReview(Data.VALID);
        assertTrue(user.addReview(review));
        List<Review> reviewList = user.getState().getReviews();
        assertEquals(1, reviewList.size());
        assertEquals(review, reviewList.get(0));

        review = data.getReview(Data.WRONG_PRODUCT);
        assertFalse(user.addReview(review));
    }

    /**
     * test use case 3.7 - watch purchases
     */
    @Override
    public void testWatchPurchasesSubscribe() {
        List<Purchase> list = user.watchMyPurchaseHistory();
        assertNotNull(list);
        assertEquals(1, list.size());
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

    /**
     * test use case 4.7 - remove manager
     * make user admin manage user niv(VALID2)
     * remove Admin from being manager and check that niv was removed from being a manager recursively
     */
    @Override
    protected void testRemoveManagerSubscribe() {
        Subscribe sub=(Subscribe) user.getState();
        Permission p=sub.getGivenByMePermissions().get(0);
        Subscribe niv=data.getSubscribe(Data.VALID2);
        String storeName=p.getStore().getName();
        //add another manager
        p.getOwner().addManager(niv,storeName);
        super.testRemoveManagerSubscribe();
        assertFalse(niv.getPermissions().containsKey(storeName));
        assertFalse(p.getOwner().getPermissions().containsKey(storeName));
    }
}
