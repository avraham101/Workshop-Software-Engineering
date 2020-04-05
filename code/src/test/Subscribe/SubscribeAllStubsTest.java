package Subscribe;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.*;
import Stubs.CartStub;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SubscribeAllStubsTest {

    protected Subscribe sub;
    protected Cart cart;
    protected PaymentSystem paymentSystem;
    protected SupplySystem supplySystem;
    protected TestData data;

    @Before
    public void setUp(){
        data = new TestData();
        cart = new CartStub();
        sub = new Subscribe("Yuval","Sabag",cart);
        initStore();
    }

    protected void initStore() {
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
        testAddPermissions();
        testRemovePermission();
        testRemoveManagerFromStore();
        testAddRequest();
        testViewRequest();
        testReplayRequest();
        testWriteReviewSubscribe();
        testAddProductToCart();
        testbuyCart();
        testCanWatchUserHistory();
        testCanWatchStoreHistory();
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
     * use case 2.7 add to cart
     */
    public void testAddProductToCart() {
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        assertTrue(sub.addProductToCart(store,product,product.getAmount()));
    }

    /**
     * use case - 2.8 buy cart
     */
    public void testbuyCart() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(sub.buyCart(paymentData,deliveryData.getAddress()));
    }

    /**
     * test: use case 3.1 - Logout
     */
    private void logoutTest(){
        assertTrue(sub.logout(new User()));
    }

    /**
     * test use case 4.1.1 - add product
     */
    protected void addProductToStoreTest(){
        addProductToStoreTestSuccess();
    }

    private void addProductToStoreTestSuccess(){
        assertTrue(sub.addProductToStore(data.getProductData(Data.VALID)));
    }

    /**
     * test 4.1.2 - remove product
     */

    protected  void removeProductFromStoreTest(){
        checkRemoveProductFail();
        checkRemoveProductSuccess();
    }

    private void checkRemoveProductSuccess() {
        String storeName=data.getProductData(Data.VALID).getStoreName();
        String productName=data.getProductData(Data.VALID).getProductName();
        assertTrue(sub.removeProductFromStore(storeName, productName));
        assertFalse(sub.getPermissions().get(storeName).getStore().getProducts().containsKey(productName));
    }

    private void checkRemoveProductFail() {
        checkRemoveProductHasNoPermission();
        checkRemoveProductNotManager();
    }

    private void checkRemoveProductNotManager() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.removeProductFromStore(data.getProductData(Data.VALID).getProductName(),validStoreName));
        sub.getPermissions().put(validStoreName,permission);
    }

    private void checkRemoveProductHasNoPermission() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.removeProductFromStore(data.getProductData(Data.VALID).getProductName(),validStoreName));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * test use case 4.1.3 - edit product
     */
    private void testEditProduct(){
        testFailEditProduct();
        testSuccessEditProduct();
    }

    private void testFailEditProduct() {
        checkEditProductHasNoPermission();
        checkEditProductNotManager();
    }

    private void checkEditProductNotManager() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.editProductFromStore(data.getProductData(Data.VALID)));
        sub.getPermissions().put(validStoreName,permission);
    }

    private void checkEditProductHasNoPermission() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.editProductFromStore(data.getProductData(Data.VALID)));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * success case
     */

    protected void testSuccessEditProduct(){
        assertTrue(sub.editProductFromStore(data.getProductData(Data.EDIT)));
    }


    /**
     * use case 4.5 add manager
     */
    private void testAddManagerToStore(){
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
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.addManager(data.getSubscribe(Data.ADMIN),validStoreName));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * check cant add manager without permission
     */

    private void checkAddManagerHasNoPermission() {
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Permission permission = sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addManager(data.getSubscribe(Data.ADMIN),validStoreName));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * test use case 4.6.1 - add permissions to manager
     */
    protected void testAddPermissions(){
        testAddPermissionNotManager();
        testAddPermissionDontHavePermission();
        testAddPermissionSuccess();
    }

    private void testAddPermissionDontHavePermission() {
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Permission permission = sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addPermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(),validStoreName));
        permission.addType(PermissionType.OWNER);
    }

    private void testAddPermissionNotManager() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.addPermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(),validStoreName));
        sub.getPermissions().put(validStoreName,permission);
    }

    protected void testAddPermissionSuccess() {
        assertTrue(sub.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()));
    }

    /**
     * test use case 4.6.2 - remove permission
     */
    private void testRemovePermission(){
        testRemovePermissionNotManager();
        testRemovePermissionDontHavePermission();
        testRemovePermissionSuccess();
    }

    private void testRemovePermissionNotManager() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.removePermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(),validStoreName));
        sub.getPermissions().put(validStoreName,permission);
    }

    private void testRemovePermissionDontHavePermission() {
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Permission permission = sub.getPermissions().get(validStoreName);
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addPermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(),validStoreName));
        permission.addType(PermissionType.OWNER);
    }

    protected void testRemovePermissionSuccess() {
        assertTrue(sub.removePermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()));
    }

    /**
     * test use case 4.7 - remove manager
     */
    /**
     * use case 4.5 add manager
     */
    private void testRemoveManagerFromStore(){
        testRemoveManagerFromStoreFail();
        testRemoveManagerStoreSuccess();
    }


    protected void testRemoveManagerStoreSuccess() {
        assertTrue(sub.removeManager(data.getSubscribe(Data.ADMIN).getName(),data.getStore(Data.VALID).getName()));
    }


    private void testRemoveManagerFromStoreFail() {
        checkRemoveManagerNotManager();
    }

    /**
     * check cant add manager without being owner
     */

    private void checkRemoveManagerNotManager() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.removeManager(data.getSubscribe(Data.ADMIN).getName(),validStoreName));
        sub.getPermissions().put(validStoreName,permission);
    }

    private void testAddRequest(){
        Request excepted = data.getRequest(Data.VALID);
        Request actual = sub.addRequest(excepted.getStoreName(), excepted.getContent());
        assertEquals(excepted.getId(), actual.getId());
        assertEquals(excepted.getSenderName(),actual.getSenderName());
        assertEquals(excepted.getStoreName(), actual.getStoreName());
        assertEquals(excepted.getContent(), actual.getContent());
        assertEquals(excepted.getComment(), actual.getComment());
    }

    private void testViewRequest(){
        Request request1 = data.getRequest(Data.WRONG_STORE);
        Request request2 = data.getRequest(Data.NULL_NAME);
        assertTrue(sub.viewRequest(request1.getStoreName()).isEmpty());
        assertTrue(sub.viewRequest(request2.getStoreName()).isEmpty());
    }

    private void testReplayRequest(){
        Request request1 = data.getRequest(Data.NULL_NAME);
        Request request2 = data.getRequest(Data.WRONG_STORE);
        Request request3 = data.getRequest(Data.WRONG_ID);
        Request request4 = data.getRequest(Data.VALID);
        assertNull(sub.replayToRequest(request1.getStoreName(), request1.getId(), "comment"));
        assertNull(sub.replayToRequest(request2.getStoreName(), request2.getId(), "comment"));
        assertNull(sub.replayToRequest(request3.getStoreName(), request3.getId(), "comment"));
        assertNull(sub.replayToRequest(request4.getStoreName(), request4.getId(), null));
    }

    /**
     * test use case 6.4.1 - watch user history
     */
    private void testCanWatchUserHistory(){
        assertFalse(sub.canWatchUserHistory());
    }

    /**
     * test use case 6.4.2 and 4.10 - watch store history
     */
    private void testCanWatchStoreHistory(){
        testWatchStoreHistorySuccess();
        testWatchStoreHistoryNotManger();
    }

    /**
     * test that cannot watch store history when not manager
     */
    private void testWatchStoreHistoryNotManger() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.canWatchStoreHistory(validStoreName));
        sub.getPermissions().put(validStoreName,permission);
    }

    private void testWatchStoreHistorySuccess() {
        assertTrue(sub.canWatchStoreHistory(data.getStore(Data.VALID).getName()));
    }

    /**
     * use case 3.3 - write review
     */
    protected void testWriteReviewSubscribe() {
        Review review = data.getReview(Data.VALID);
        this.sub.addReview(review);
        List<Review> reviewList = sub.getReviews();
        assertEquals(1, reviewList.size());
        assertEquals(review, reviewList.get(0));

    }


}