package Subscribe;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.*;
import Stubs.CartStub;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
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

    /**--------------------------------set-ups-------------------------------------------------------------------*/

    protected void initStore() {
        paymentSystem = new ProxyPayment();
        supplySystem = new ProxySupply();
    }

    /**
     * set up to open a store
     */
    protected void setUpStoreOpened(){
        sub.openStore(data.getStore(Data.VALID));
    }

    /**
     * set up manager added to a store
     */
    protected void setUpManagerAdded(){
        setUpStoreOpened();
        sub.addManager(data.getSubscribe(Data.ADMIN),data.getStore(Data.VALID).getName());
    }

    /**
     * set up a manager with permissions
     */
    private void setUpAddedPermissions(){
        setUpManagerAdded();
        sub.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName());
    }

    /**
     * set up valid product in the store of sub
     */
    private void setUpProductAdded(){
        setUpStoreOpened();
        sub.addProductToStore(data.getProductData(Data.VALID));
    }

    /**
     * set up a valid cart with a valid product
     */
    private void setUpProductAddedToCart(){
        setUpProductAdded();
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        sub.addProductToCart(store,product,product.getAmount());
    }

    /**
     * set up for Reserved
     */
    protected void setUpReserved() {
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        sub.addProductToCart(store,product,product.getAmount());
    }

    /**
     * set up for Buy and Cancel
     */
    protected void setUpBuy() {
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        sub.addProductToCart(store,product,product.getAmount());
        sub.reserveCart();
    }

    /**
     * set up for the save
     */
    protected void setUpSave() {
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        sub.addProductToCart(store,product,product.getAmount());
        sub.reserveCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        sub.buyCart(paymentData, deliveryData);
    }

    /**
     * set up a valid purchase history
     */
    protected void setUpProductBought(){
        setUpSave();
        sub.savePurchase(sub.getName());
    }

    /**
     * set up request to the valid store of sub
     */
    private void setUpRequestAdded(){
        setUpStoreOpened();
        Request excepted = data.getRequest(Data.VALID);
        sub.addRequest(excepted.getId(), excepted.getStoreName(), excepted.getContent());
    }

    /**--------------------------------set-ups-------------------------------------------------------------------*/

    /**
     * part of test use case 2.3 - Login.
     * test login where all fields are stubs
     */
    @Test
    public void loginTest() {
        assertFalse(sub.login(new User(),data.getSubscribe(Data.ADMIN)));
        assertNotEquals(sub.getName(),data.getSubscribe(Data.ADMIN).getName());
    }

    /**
     * use case 2.7 add to cart
     */
    @Test
    public void testAddProductToCart() {
        setUpProductAdded();
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        assertTrue(sub.addProductToCart(store,product,product.getAmount()));
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Test
    public void testReservedCart() {
        setUpReserved();
        assertFalse(sub.reserveCart());
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Test
    public void testBuyCart() {
        setUpBuy();
        int size = 0;
        double sum =0;
        for(Basket b:cart.getBaskets().values()) {
            HashMap<Product,Integer> products = b.getProducts();
            for(Product p:products.keySet()) {
                int amount = products.get(p);
                sum += amount * p.getPrice();
                size++;
            }
        }
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        sub.buyCart(paymentData,deliveryData);
        assertEquals(sum,paymentData.getTotalPrice(),0.001);
        assertEquals(size,deliveryData.getProducts().size());
    }


    /**
     * test: use case 3.1 - Logout
     */
    @Test
    public void logoutTest(){
        assertTrue(sub.logout(new User()));
    }

    /**
     * test use case 3.2 - Open Store
     */
    @Test
    public void openStoreTest() {
        Store store = sub.openStore(data.getStore(Data.VALID));
        assertNotNull(store);
    }

    /**
     * use case 3.3 - write review
     */
    @Test
    public void testWriteReviewSubscribe() {
        Review review = data.getReview(Data.VALID);
        assertTrue(sub.addReview(review));
        List<Review> reviewList = sub.getReviews();
        assertEquals(1, reviewList.size());
        assertEquals(review, reviewList.get(0));
    }

    /**
     * use case 3.5 - add request
     */
    @Test
    public void testAddRequest(){
        setUpStoreOpened();
        Request excepted = data.getRequest(Data.VALID);
        Request actual = sub.addRequest(excepted.getId(), excepted.getStoreName(), excepted.getContent());
        assertEquals(excepted.getId(), actual.getId());
        assertEquals(excepted.getSenderName(),actual.getSenderName());
        assertEquals(excepted.getStoreName(), actual.getStoreName());
        assertEquals(excepted.getContent(), actual.getContent());
        assertEquals(excepted.getComment(), actual.getComment());
    }

    /**
     * test use case 3.7 - watch purchases
     */
    @Test
    public void testWatchPurchasesEmpty() {
        List<Purchase> list = sub.watchMyPurchaseHistory();
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    /**
     * test use case 4.1.1 - add product
     */
    @Test
    public void addProductToStoreTestSuccess(){
        setUpStoreOpened();
        assertTrue(sub.addProductToStore(data.getProductData(Data.VALID)).getValue());
    }

    /**
     * test use case 4.1.1 - add product
     */
    @Test
    public void addProductToStoreTestFail() {
        setUpStoreOpened();
        testAddProductNotManagerOfStore();
        testAddProductDontHavePermission();
    }

    /**
     * part of test use case 4.1.1 - add product
     */
    private void testAddProductDontHavePermission() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addProductToStore(data.getProductData(Data.VALID)).getValue());
        permission.addType(PermissionType.OWNER);
        assertFalse(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * part of test use case 4.1.1 - add product
     */
    private void testAddProductNotManagerOfStore() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        sub.getPermissions().clear();
        assertFalse(sub.addProductToStore(data.getProductData(Data.VALID)).getValue());
        sub.getPermissions().put(validStoreName,permission);
        assertFalse(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * test 4.1.2 - remove product
     */
    @Test
    public void removeProductFromStoreTest(){
        setUpProductAdded();
        checkRemoveProductFail();
        checkRemoveProductSuccess();
    }

    /**
     * part of test 4.1.2 - remove product
     */
    protected void checkRemoveProductSuccess() {
        String storeName=data.getProductData(Data.VALID).getStoreName();
        String productName=data.getProductData(Data.VALID).getProductName();
        assertTrue(sub.removeProductFromStore(storeName, productName).getValue());
        assertFalse(sub.getPermissions().get(storeName).getStore().getProducts().containsKey(productName));
    }

    /**
     * part of test 4.1.2 - remove product
     */
    private void checkRemoveProductFail() {
        checkRemoveProductHasNoPermission();
        checkRemoveProductNotManager();
    }

    /**
     * part of test 4.1.2 - remove product
     */
    private void checkRemoveProductNotManager() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        sub.getPermissions().clear();
        assertFalse(sub.removeProductFromStore(data.getProductData(Data.VALID).getProductName(),validStoreName).getValue());
        sub.getPermissions().put(validStoreName,permission);
        assertTrue(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * part of test 4.1.2 - remove product
     */
    private void checkRemoveProductHasNoPermission() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.removeProductFromStore(data.getProductData(Data.VALID).getProductName(),validStoreName).getValue());
        permission.addType(PermissionType.OWNER);
        assertTrue(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * test use case 4.1.3 - edit product
     */
    @Test
    public void testEditProduct(){
        setUpProductAdded();
        testFailEditProduct();
        testSuccessEditProduct();
    }

    /**
     * part of test use case 4.1.3 - edit product
     */
    private void testFailEditProduct() {
        checkEditProductHasNoPermission();
        checkEditProductNotManager();
    }

    /**
     * part of test use case 4.1.3 - edit product
     */
    private void checkEditProductNotManager() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        ProductData pData=data.getProductData(Data.EDIT);
        Store store=permission.getStore();
        sub.getPermissions().clear();
        assertFalse(sub.editProductFromStore(pData).getValue());
        assertFalse(store.getProducts().get(pData.getProductName()).equal(pData));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * part of test use case 4.1.3 - edit product
     */
    private void checkEditProductHasNoPermission() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        ProductData pData=data.getProductData(Data.EDIT);
        Store store=permission.getStore();
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.editProductFromStore(data.getProductData(Data.VALID)).getValue());
        assertFalse(store.getProducts().get(pData.getProductName()).equal(pData));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * part of test use case 4.1.3 - edit product
     */
    protected void testSuccessEditProduct(){
        assertTrue(sub.editProductFromStore(data.getProductData(Data.EDIT)).getValue());
    }


    /**
     * use case 4.5 add manager
     */
    @Test
    public void testAddManagerToStore(){
        setUpStoreOpened();
        testAddManagerToStoreFail();
        testAddManagerStoreSuccess();
        testAlreadyManager();
    }

    /**
     * part of use case 4.5 add manager
     * test we cant add manager twice
     */
    private void testAlreadyManager() {
        assertFalse(sub.addManager(data.getSubscribe(Data.ADMIN),data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * part of use case 4.5 add manager
     */
    protected void testAddManagerStoreSuccess() {
        assertTrue(sub.addManager(data.getSubscribe(Data.ADMIN),data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * part of use case 4.5 add manager
     */
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
        Store store=permission.getStore();
        sub.getPermissions().clear();
        assertFalse(sub.addManager(data.getSubscribe(Data.ADMIN),validStoreName).getValue());
        assertFalse(store.getPermissions().containsKey(data.getSubscribe(Data.ADMIN).getName()));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * check cant add manager without permission
     */
    private void checkAddManagerHasNoPermission() {
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Permission permission = sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addManager(data.getSubscribe(Data.ADMIN),validStoreName).getValue());
        assertFalse(store.getPermissions().containsKey(data.getSubscribe(Data.ADMIN).getName()));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * test use case 4.6.1 - add permissions to manager
     */
    @Test
    public void testAddPermissions(){
        setUpManagerAdded();
        testAddPermissionNotManager();
        testAddPermissionDontHavePermission();
        testAddPermissionSuccess();
    }

    /**
     * part of test use case 4.6.1 - add permissions to manager
     */
    private void testAddPermissionDontHavePermission() {
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Permission permission = sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addPermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(),validStoreName).getValue());
        assertFalse(store.getPermissions().get(data.getSubscribe(Data.ADMIN).getName()).getPermissionType().
                containsAll(data.getPermissionTypeList()));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * part of test use case 4.6.1 - add permissions to manager
     */
    private void testAddPermissionNotManager() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        sub.getPermissions().clear();
        assertFalse(sub.addPermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(),validStoreName).getValue());
        assertFalse(store.getPermissions().get(data.getSubscribe(Data.ADMIN).getName()).getPermissionType().
                containsAll(data.getPermissionTypeList()));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * part of test use case 4.6.1 - add permissions to manager
     */
    protected void testAddPermissionSuccess() {
        assertTrue(sub.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()).getValue());
    }

    /**
     * test use case 4.6.2 - remove permission
     */
    @Test
    public void testRemovePermission(){
        setUpAddedPermissions();
        testRemovePermissionNotManager();
        testRemovePermissionDontHavePermission();
        testRemovePermissionSuccess();
    }

    /**
     * part of test use case 4.6.2 - remove permission
     */
    private void testRemovePermissionNotManager() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        sub.getPermissions().clear();
        assertFalse(sub.removePermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(),validStoreName));
        assertTrue(store.getPermissions().get(data.getSubscribe(Data.ADMIN).getName()).getPermissionType().
                containsAll(data.getPermissionTypeList()));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * part of test use case 4.6.2 - remove permission
     */
    private void testRemovePermissionDontHavePermission() {
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        Permission permission = sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addPermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(),validStoreName).getValue());
        assertTrue(store.getPermissions().get(data.getSubscribe(Data.ADMIN).getName()).getPermissionType().
                containsAll(data.getPermissionTypeList()));
        permission.addType(PermissionType.OWNER);
    }

    /**
     * part of test use case 4.6.2 - remove permission
     */
    protected void testRemovePermissionSuccess() {
        assertTrue(sub.removePermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()));
    }

    /**
     * test use case 4.7 - remove manager
     */
    @Test
    public void testRemoveManagerFromStore(){
        setUpManagerAdded();
        testRemoveManagerFromStoreFail();
        testRemoveManagerStoreSuccess();
    }

    /**
     * part of test use case 4.7 - remove manager
     */
    protected void testRemoveManagerStoreSuccess() {
        assertTrue(sub.removeManager(data.getSubscribe(Data.ADMIN).getName(),data.getStore(Data.VALID).getName()));
    }

    /**
     * part of test use case 4.7 - remove manager
     */
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

    /**
     * use case 4.9.1 - view request
     */
    @Test
    public void testViewRequest(){
        setUpRequestAdded();
        testViewRequestWrongStore();
        testViewRequestNullName();
    }

    /**
     * part of use case 4.9.1 - view request
     */
    private void testViewRequestWrongStore() {
        Request request1 = data.getRequest(Data.WRONG_STORE);
        assertTrue(sub.viewRequest(request1.getStoreName()).isEmpty());
    }

    /**
     * part of use case 4.9.1 - view request
     */
    private void testViewRequestNullName() {
        Request request2 = data.getRequest(Data.NULL_NAME);
        assertTrue(sub.viewRequest(request2.getStoreName()).isEmpty());
    }

    /**
     * test use case 4.9.2 - reply request
     */
    @Test
    public void testReplayRequest(){
        setUpRequestAdded();
        testReplayRequestNullName();
        testReplayRequestWrongStore();
        testReplayRequestWrongID();
        testReplayRequestNullRequest();
    }

    /**
     * part of test use case 4.9.2 - reply request
     */
    private void testReplayRequestNullName() {
        Request request1 = data.getRequest(Data.NULL_NAME);
        assertNull(sub.replayToRequest(request1.getStoreName(), request1.getId(), "comment"));
    }

    /**
     * part of test use case 4.9.2 - reply request
     */
    private void testReplayRequestWrongStore() {
        Request request2 = data.getRequest(Data.WRONG_STORE);
        assertNull(sub.replayToRequest(request2.getStoreName(), request2.getId(), "comment"));
    }

    /**
     * part of test use case 4.9.2 - reply request
     */
    private void testReplayRequestWrongID() {
        Request request3 = data.getRequest(Data.WRONG_ID);
        assertNull(sub.replayToRequest(request3.getStoreName(), request3.getId(), "comment"));
    }

    /**
     * part of test use case 4.9.2 - reply request
     */
    private void testReplayRequestNullRequest() {
        Request request4 = data.getRequest(Data.VALID);
        assertNull(sub.replayToRequest(request4.getStoreName(), request4.getId(), null));
    }

    /**
     * test use case 6.4.1 - watch user history
     */
    @Test
    public void testCanWatchUserHistory(){
        assertFalse(sub.canWatchUserHistory());
    }

    /**
     * test use case 6.4.2 and 4.10 - watch store history
     */
    @Test
    public void testCanWatchStoreHistory(){
        setUpStoreOpened();
        testWatchStoreHistorySuccess();
        testWatchStoreHistoryNotManger();
    }

    /**
     * part of test use case 6.4.2 and 4.10 - watch store history
     * test that cannot watch store history when not manager
     */
    private void testWatchStoreHistoryNotManger() {
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        sub.getPermissions().clear();
        assertFalse(sub.canWatchStoreHistory(validStoreName));
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * part of test use case 6.4.2 and 4.10 - watch store history
     */
    private void testWatchStoreHistorySuccess() {
        assertTrue(sub.canWatchStoreHistory(data.getStore(Data.VALID).getName()));
    }


}