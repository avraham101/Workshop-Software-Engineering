package UserTests;

import Data.*;
import DataAPI.*;
import Domain.*;
import Domain.Discount.RegularDiscount;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import Persitent.DaoHolders.DaoHolder;
import Persitent.SubscribeDao;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class UserRealTest extends UserAllStubsTest{
    private DaoHolder daos;

    @Before
    public void setUp(){
        data =new TestData();
        daos=new DaoHolder();
    }

    /***************************setUp***********************************/

    @Override
    protected void setUpGuest() {
        userState = new Guest();
        user = new User(userState);
    }

    /**
     * set up to subscribe state
     */
    @Override
    protected void setUpSubscribe(){
        setUpGuest();
        daos.getSubscribeDao().addSubscribe(data.getSubscribe(Data.VALID));
        daos.getSubscribeDao().addSubscribe(data.getSubscribe(Data.ADMIN));
        daos.getSubscribeDao().addSubscribe(data.getSubscribe(Data.VALID2));
        user.login(data.getSubscribe(Data.VALID));
    }

    /**
     * set up a store for the subscribed user
     */
    protected void setUpOpenStore(){
        setUpSubscribe();
        StoreData storeData = new StoreData("Store", "description");
        user.openStore(storeData);
    }

    @Override
    protected void setUpProductAdded() {
        super.setUpProductAdded();
    }

    @Override
    protected void setUpProductAddedToCart() {
        super.setUpProductAddedToCart();
    }

    @Override
    protected void setUpReservedCart() {
        super.setUpReservedCart();
    }

    @Override
    protected void setUpBuyCart() {
        super.setUpBuyCart();
    }

    @Override
    protected void setUpBougtAndSaved() {
        super.setUpBougtAndSaved();
    }

    /**
     * set up to reserved to cart
     */
    public void setUpReserved() {
        setUpProductAdded();
        Store store = data.getRealStore(Data.VALID);
        Product p = data.getRealProduct(Data.VALID);
        user.addProductToCart(store,p,p.getAmount());
    }


    /***************************test*******************************/
    /**
     * test use case 2.3 - Login
     * user: Yuval Sabag
     */
    @Override @Test
    public void testLoginGuest(){
        super.testLoginGuest();
        assertEquals(user.getPassword(), data.getSubscribe(Data.VALID).getPassword());
        assertEquals(user.getUserName(), data.getSubscribe(Data.VALID).getName());
    }

    /**
     * use case 2.7 - add product to cart
     */
    @Override
    @Test
    public void testAddProductToCart() {
        setUpProductAdded();
        Store store = data.getRealStore(Data.VALID);
        Product p = data.getRealProduct(Data.VALID);
        assertTrue(user.addProductToCart(store,p,p.getAmount()));
        assertTrue(user.getState().getCart().getBasket(store.getName()).getProducts().containsKey(p.getName()));
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testBuyCart() {
        setUpReservedCart();
        Cart cart = user.getState().getCart();
        int size = 0;
        double sum =0;
        for(Basket b:cart.getBaskets().values()) {
            Map<String,ProductInCart> products = b.getProducts();
            for(ProductInCart p:products.values()) {
                int amount = p.getAmount();
                sum += amount * p.getPrice();
                size++;
            }
        }
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertTrue(user.buyCart(paymentData,deliveryData));
        assertEquals(sum,paymentData.getTotalPrice(),0.001);
        assertEquals(size,deliveryData.getProducts().size());
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - reserve cart
     */

    @Override
    public void testReservedCart() {
        super.testReservedCart();
        tearDownProductAddedToCart();
    }


    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testBuyCartFail(){
        setUpReservedCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        user.updateStorePolicy(data.getStore(Data.VALID).getName(),new BasketPurchasePolicy(0));
        assertFalse(user.buyCart(paymentData,deliveryData));
        assertEquals(0,paymentData.getTotalPrice(),0.001);
        assertTrue(deliveryData.getProducts().isEmpty());
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Override
    @Test
    public void testSavePurchase() {
        setUpReservedCart();
        String name = data.getSubscribe(Data.VALID).getName();
        int number =  daos.getSubscribeDao().find(name).getCart().getBaskets().keySet().size();
        user.savePurchase(name);
        Store store = daos.getStoreDao().find(data.getStore(Data.VALID).getName());
        assertEquals(number, user.watchMyPurchaseHistory().getValue().size());
        assertEquals(number, store.getPurchases().size());
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8 - purchase cart
     */
    @Test
    public void testCancel() {
        setUpProductAddedToCart();
        int expected = amountProductInStore();
        user.reservedCart();
        user.cancelCart();
        int result = amountProductInStore();
        assertEquals(expected,result);
        tearDownProductAddedToCart();
    }

    /**
     * use case 2.8
     * help function for getting the amount
     * @return
     */
    private int amountProductInStore() {
        Cart cart = daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName()).getCart();
        Store store = null;
        for(Basket b: cart.getBaskets().values()) {
            store = b.getStore();
            break;
        }
        assertNotNull(store);
        Product product = null;
        for(Product p :store.getProducts().values()) {
            product = p;
            break;
        }
        assertNotNull(product);
        return product.getAmount();
    }



    /**
     * use case 3.1 - logout
     */
    @Override @Test
    public void testLogoutSubscribe() {
        setUpSubscribe();
        Subscribe sub=(Subscribe)user.getState();
        assertTrue(user.logout());
        assertEquals(sub.getSessionNumber().intValue(),-1);
        assertTrue(user.getState() instanceof Guest);
        tearDownSubscribe();
    }

    /**
     * 3.2 open store
     */
    @Override
    public void testOpenStoreSubscribe() {
        super.testOpenStoreSubscribe();
        tearDownOpenStore();
    }

    /**
     * use case 3.3 - write review
     */
    @Override @Test
    public void testWriteReviewSubscribe() {
        setUpBougtAndSaved();
        Review review = data.getReview(Data.VALID);
        assertTrue(user.addReview(review));
        List<Review> reviewList = user.getState().getReviews();
        assertEquals(1, reviewList.size());
        assertEquals(review, reviewList.get(0));
        tearDownProductAddedToCart();
    }

    /**
     * use case 3.3 - write review
     */
    @Test
    public void testWriteWrongReviewSubscribe(){
        setUpReserved();
        Review review = data.getReview(Data.WRONG_PRODUCT);
        assertFalse(user.addReview(review));
        tearDownProductAddedToCart();
    }

    @Test
    @Override
    public void testAddRequestSubscribe() {
        super.testAddRequestSubscribe();
        tearDownOpenStore();
    }

    /**
     * test use case 3.7 - watch purchases
     */
    @Override @Test
    public void testWatchPurchasesSubscribe() {
        setUpBougtAndSaved();
        List<Purchase> list = user.watchMyPurchaseHistory().getValue();
        assertNotNull(list);
        assertEquals(1, list.size());
        tearDownProductAddedToCart();
    }

    /**
     * test 4.1.1 use case -add product
     */
    @Override @Test
    public void testAddProductToStoreSubscribe() {
        super.testAddProductToStoreSubscribe();
        Permission p=daos.getSubscribeDao().find(data.getSubscribe(Data.VALID).getName()).getPermissions().get(data.getStore(Data.VALID).getName());
        Product product=p.getStore().getProducts().get(data.getProductData(Data.VALID).getProductName());
        assertTrue(product.equal(data.getProductData(Data.VALID)));
        tearDownOpenStore();
    }

    /**
     * test 4.1.2 use case - remove product
     */
    @Override @Test
    public void testRemoveProductFromStoreSubscribe() {
        super.testRemoveProductFromStoreSubscribe();
        Subscribe sub=(Subscribe)user.getState();
        sub=daos.getSubscribeDao().find(sub.getName());
        assertFalse(sub.getPermissions().containsKey(data.getProductData(Data.VALID).getProductName()));
        tearDownOpenStore();
    }

    /**
     * use case 4.1.3 -edit product
     */
    @Override @Test
    public void testEditProductFromStoreSubscribe() {
        super.testEditProductFromStoreSubscribe();
        ProductData product= data.getProductData(Data.EDIT);
        Subscribe sub=(Subscribe) user.getState();
        sub=daos.getSubscribeDao().find(sub.getName());
        assertTrue(sub.getPermissions().get(product.getStoreName()).getStore()
                .getProducts().get(product.getProductName()).equal(product));
        tearDownOpenStore();
    }

    /**
     * use case 4.2.1.1 -add product to store
     */
    @Test @Override
    public void testAddDiscountToStoreSuccessSubscribe(){
        super.testAddDiscountToStoreSuccessSubscribe();
        Store store=daos.getStoreDao().find(data.getStore(Data.VALID).getName());
        assertTrue(store.getDiscount().values().iterator().next() instanceof RegularDiscount);
        tearDownOpenStore();
    }

    /**
     * use case 4.2.1.2 -remove product from store
     */
    @Test @Override
    public void testDeleteDiscountFromStoreSuccessSubscribe(){
        setUpDiscountAdded();
        Store store=daos.getStoreDao().find(data.getStore(Data.VALID).getName());
        int discountId=store.getDiscount().values().iterator().next().getId();
        assertTrue(user.deleteDiscountFromStore(discountId,data.getStore(Data.VALID).getName()).getValue());
        store=daos.getStoreDao().find(data.getStore(Data.VALID).getName());
        assertTrue(store.getDiscount().isEmpty());
        tearDownOpenStore();
    }

    /**
     * use case 4.5 - add manager
     */
    @Override @Test
    public void testAddManagerSubscribe() {
        super.testAddManagerSubscribe();
        Subscribe sub=(Subscribe) user.getState();
        sub=daos.getSubscribeDao().find(sub.getName());
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
        tearDownOpenStore();
    }

    /**
     * 4.6.1 - add permission
     */
    @Override @Test
    public void testAddPermissionsSubscribe() {
        super.testAddPermissionsSubscribe();
        Subscribe sub=(Subscribe) user.getState();
        sub=daos.getSubscribeDao().find(sub.getName());
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType()
                .containsAll(data.getPermissionTypeList()));
        tearDownOpenStore();
    }

    /**
     * check use case 4.6.2 - remove permissions
     */
    @Override @Test
    public void testRemovePermissionsSubscribe() {
        super.testRemovePermissionsSubscribe();
        Subscribe sub=(Subscribe) user.getState();
        sub=daos.getSubscribeDao().find(sub.getName());
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType().
                isEmpty());
        tearDownOpenStore();
    }

    /**
     * test use case 4.7 - remove manager
     * make user admin manage user niv(VALID2)
     * remove Admin from being manager and check that niv was removed from being a manager recursively
     */
    @Override @Test
    public void testRemoveManagerSubscribe() {
        setUpAddedManager();
        Subscribe sub=(Subscribe) user.getState();
        sub=daos.getSubscribeDao().find(sub.getName());
        Permission p=sub.getGivenByMePermissions().get(0);
        Subscribe niv=data.getSubscribe(Data.VALID2);
        String storeName=p.getStore().getName();
        //add another manager
        p.getOwner().addManager(niv,storeName);
        assertTrue(user.removeManager(data.getSubscribe(Data.ADMIN), data.getStore(Data.VALID).getName()).getValue());
        niv=daos.getSubscribeDao().find(niv.getName());
        assertFalse(niv.getPermissions().containsKey(storeName));
        Subscribe admin=daos.getSubscribeDao().find(data.getSubscribe(Data.ADMIN).getName());
        assertFalse(admin.getPermissions().containsKey(storeName));
        tearDownOpenStore();
    }

    /**
     * getManagersOfStoreUserManaged tests
     */
    @Test
    public void getManagersOfStoreUserManagedSuccess(){
        setUpAddedManager();
        assertTrue(user.getManagersOfStoreUserManaged(data.getStore(Data.VALID).getName()).getValue().
                contains(data.getSubscribe(Data.ADMIN).getName()));
        tearDownOpenStore();
    }

    /**
     * use case 6.4.2 - watch store
     */
    @Override
    public void testCanWatchStoreHistorySubscribe() {
        super.testCanWatchStoreHistorySubscribe();
        tearDownOpenStore();
    }

    /**
     * getManagersOfStoreUserManaged tests
     */
    @Test
    public void getManagersOfStoreUserManagedGuest(){
        setUpGuest();
        assertNull(user.getManagersOfStoreUserManaged(data.getStore(Data.VALID).getName()).getValue());
    }

    protected void tearDownSubscribe(){
        daos.getSubscribeDao().remove(data.getSubscribe(Data.VALID).getName());
        daos.getSubscribeDao().remove(data.getSubscribe(Data.ADMIN).getName());
        daos.getSubscribeDao().remove(data.getSubscribe(Data.VALID2).getName());
    }

    protected void tearDownOpenStore(){
        daos.getStoreDao().removeStore(data.getStore(Data.VALID).getName());
        tearDownSubscribe();
    }

    protected void tearDownProductAddedToCart(){
        daos.getSubscribeDao().remove(data.getSubscribe(Data.VALID).getName());
        tearDownOpenStore();
    }


}
