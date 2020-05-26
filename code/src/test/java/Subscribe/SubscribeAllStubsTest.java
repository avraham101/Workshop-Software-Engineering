package Subscribe;

import Data.Data;
import Data.TestData;
import DataAPI.*;
import Domain.*;
import Domain.Discount.Discount;
import Drivers.LogicManagerDriver;
import Persitent.Cache;
import Persitent.DaoHolders.DaoHolder;
import Persitent.StoreDao;
import Persitent.SubscribeDao;
import Stubs.CartStub;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class SubscribeAllStubsTest {

    protected Subscribe sub;
    protected Cart cart;
    protected PaymentSystem paymentSystem;
    protected SupplySystem supplySystem;
    protected TestData data;
    protected DaoHolder daoHolder;
    protected Cache cache;
    protected LogicManagerDriver logicManagerDriver;
    protected Subscribe subscribe;

    @Before
    public void setUp(){
        data = new TestData();
        daoHolder = new DaoHolder();
        cache = new Cache();
        try {
            logicManagerDriver = new LogicManagerDriver();
        } catch (Exception e){
            fail();
        }
    }

    private void setUpSubscribe() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        Response<Boolean> response = logicManagerDriver.register(subscribe.getName(),subscribe.getPassword());
        if(!response.getValue())
            fail();
        this.subscribe = cache.findSubscribe(subscribe.getName());
    }

    public void setUpLoginSubscribe() {
        setUpSubscribe();
        int id = logicManagerDriver.connectToSystem();
        Subscribe sub = data.getSubscribe(Data.VALID);
        Response<Boolean> response = this.logicManagerDriver.login(id,sub.getName(), sub.getPassword());
        if(!response.getValue())
            fail();
        this.subscribe = cache.findSubscribe(subscribe.getName());
    }

    /**
     * set up to open a store
     */
    protected void setUpStoreOpened(){
        setUpLoginSubscribe();
        StoreData storeData = data.getStore(Data.VALID);
        this.logicManagerDriver.openStore(this.subscribe.getSessionNumber(),storeData);
    }

    protected void tearDownStore() {
        tearDown();
        StoreData storeData = data.getStore(Data.VALID);
        StoreDao storeDao = daoHolder.getStoreDao();
        storeDao.removeStore(storeData.getName());
    }

    /**
     * set up manager added to a store
     */
    protected void setUpManagerAdded(){
        setUpStoreOpened();
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        StoreData storeData = data.getStore(Data.VALID);
        Response<Boolean> response = logicManagerDriver.addManager(0,admin.getName(),storeData.getName());
        if(!response.getValue())
            fail();
    }

    /**
     * set up a manager with permissions
     */
    private void setUpAddedPermissions(){
        setUpManagerAdded();
        List<PermissionType> list = data.getPermissionTypeList();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        logicManagerDriver.addPermissions(0, list, storeData.getName(), admin.getName());
    }

    /**
     * set up valid product in the store of sub
     */
    private void setUpProductAdded(){
        setUpStoreOpened();
        subscribe.addProductToStore(data.getProductData(Data.VALID));
    }

    /**
     * set up discount in the store
     */
    private void setUpDiscountAdded() {
        setUpProductAdded();
        StoreData storeData = data.getStore(Data.VALID);
        List <Discount> discounts = data.getDiscounts(Data.VALID);
        Response<Boolean> response = this.subscribe.addDiscountToStore(storeData.getName(), discounts.get(0));
        if(!response.getValue())
            fail();
    }

    /**
     * set up a valid cart with a valid product
     */
    public void setUpReserved(){
        setUpProductAdded();
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        this.subscribe.addProductToCart(store,product,product.getAmount());
    }

    /**
     * set up for Buy and Cancel
     */
    protected void setUpBuy() {
        setUpReserved();
        this.subscribe.reserveCart();
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
        sub.addRequest(excepted.getStoreName(), excepted.getContent());
    }

    /**
     * part of test use case 2.3 - Login.
     * test login where all fields are stubs
     */
    @Test
    public void loginTest() {
        setUpSubscribe();
        assertFalse(subscribe.login(new User(), data.getSubscribe(Data.VALID)));
        assertEquals(subscribe.getName(), data.getSubscribe(Data.VALID).getName());
    }

    /**
     * use case 2.7 add to cart
     */
    @Test
    public void testAddProductToCart() {
        setUpProductAdded();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        ProductData productData = data.getProductData(Data.VALID);
        Product product = store.getProduct(productData.getProductName());
        assertTrue(subscribe.addProductToCart(store,product,product.getAmount()));

        Basket basket = subscribe.getCart().getBasket(storeData.getName());
        assertNotNull(basket);
        ProductInCart productInCart = basket.getProducts().get(productData.getProductName());
        assertNotNull(productInCart);
        assertEquals(productData.getAmount(), productInCart.getAmount());

        tearDownStore();
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Test
    public void testReservedCart() {
        setUpReserved();
        assertTrue(subscribe.reserveCart());

        StoreData storeData = data.getStore(Data.VALID);
        ProductData productData = data.getProductData(Data.VALID);
        Basket basket = subscribe.getCart().getBasket(storeData.getName());
        assertNotNull(basket);
        ProductInCart productInCart = basket.getProducts().get(productData.getProductName());
        assertNotNull(productInCart);
        assertEquals(productData.getAmount(), productInCart.getAmount());

        Store store = daoHolder.getStoreDao().find(storeData.getName());
        Product product = store.getProduct(productData.getProductName());
        assertEquals(0, product.getAmount());

        tearDownStore();
    }

    /**
     * use case - 2.8 buy cart
     */
    @Test
    public void testBuyCart() {
        setUpBuy();
        int size = 0;
        double sum =0;
        Cart cart = this.subscribe.getCart();
        for(Basket b:cart.getBaskets().values()) {
            Map<String, ProductInCart> products = b.getProducts();
            for(ProductInCart p:products.values()) {
                int amount = p.getAmount();
                sum += amount * p.getPrice();
                size++;
            }
        }
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertTrue(this.subscribe.buyCart(paymentData,deliveryData));
        assertEquals(sum,paymentData.getTotalPrice(),0.001);
        assertEquals(size,deliveryData.getProducts().size());
        tearDownStore();
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
        setUpLoginSubscribe();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = this.subscribe.openStore(storeData);
        assertNotNull(store);
        assertEquals(storeData.getName(), store.getName());
        tearDownStore();
    }

    /**
     * use case 3.3 - write review
     */
    @Test
    public void testWriteReviewSubscribe() {
        setUpStoreOpened();
        Review review = data.getReview(Data.VALID);
        assertTrue(subscribe.addReview(review));
        List<Review> reviewList = subscribe.getReviews();
        assertEquals(1, reviewList.size());
        assertEquals(review, reviewList.get(0));
        tearDownStore();
    }

    /**
     * use case 3.5 - add request
     */
    @Test
    public void testAddRequest(){
        setUpStoreOpened();
        Request excepted = data.getRequest(Data.VALID);
        Request actual = subscribe.addRequest(excepted.getStoreName(), excepted.getContent());
        assertEquals(excepted.getSenderName(),actual.getSenderName());
        assertEquals(excepted.getStoreName(), actual.getStoreName());
        assertEquals(excepted.getContent(), actual.getContent());
        assertEquals(excepted.getComment(), actual.getComment());
        tearDownStore();
    }

    /**
     * test use case 3.7 - watch purchases
     */
    @Test
    public void testWatchPurchasesEmpty() {
        setUpLoginSubscribe();
        List<Purchase> list = subscribe.watchMyPurchaseHistory().getValue();
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    /**
     * test use case 4.1.1 - add product
     */
    @Test
    public void addProductToStoreTestSuccess(){
        setUpStoreOpened();
        ProductData productData = data.getProductData(Data.VALID);
        assertTrue(subscribe.addProductToStore(productData).getValue());

        Store store = daoHolder.getStoreDao().find(productData.getStoreName());
        Product product = store.getProduct(productData.getProductName());
        assertNotNull(product);
        assertEquals(productData.getProductName(), product.getName());
        assertEquals(productData.getAmount(), product.getAmount());
        tearDownStore();
    }

    /**
     * test use case 4.1.1 - add product
     */
    @Test
    public void testAddProductDontHavePermission() {
        setUpStoreOpened();
        String validStoreName=data.getProductData(Data.VALID).getStoreName();
        Permission permission=sub.getPermissions().get(validStoreName);
        Store store=permission.getStore();
        permission.removeType(PermissionType.OWNER);
        assertFalse(sub.addProductToStore(data.getProductData(Data.VALID)).getValue());
        permission.addType(PermissionType.OWNER);
        assertFalse(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
        tearDownStore();
    }

    /**
     * test use case 4.1.1 - add product
     */
    @Test
    public void testAddProductNotManagerOfStore() {
        setUpStoreOpened();
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());
        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());
        ProductData productData = data.getProductData(Data.VALID);
        assertFalse(sub.addProductToStore(productData).getValue());
        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * part of test 4.1.2 - remove product
     */
    @Test
    public void checkRemoveProductSuccess() {
        setUpProductAdded();
        String storeName=data.getProductData(Data.VALID).getStoreName();
        String productName=data.getProductData(Data.VALID).getProductName();
        assertTrue(this.subscribe.removeProductFromStore(storeName, productName).getValue());
        tearDownStore();
    }


    /**
     * part of test 4.1.2 - remove product
     */
    @Test
    public void checkRemoveProductNotManager() {
        setUpProductAdded();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());
        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());
        ProductData productData = data.getProductData(Data.VALID);
        assertFalse(sub.removeProductFromStore(storeData.getName(),productData.getProductName()).getValue());
        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * part of test 4.1.2 - remove product
     */
    @Test
    public void checkRemoveProductHasNoPermission() {
        setUpProductAdded();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());

        logicManagerDriver.addManager(0,sub.getName(),storeData.getName());

        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());
        ProductData productData = data.getProductData(Data.VALID);
        assertFalse(sub.removeProductFromStore(storeData.getName(),productData.getProductName()).getValue());
        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * test use case 4.1.3 - edit product
     */
    @Test
    public void checkEditProductNotManager() {
        setUpProductAdded();
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());
        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());
        ProductData pData=data.getProductData(Data.EDIT);
        assertFalse(sub.editProductFromStore(pData).getValue());
        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * test use case 4.1.3 - edit product
     */
    @Test
    public void checkEditProductHasNoPermission() {
        setUpProductAdded();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());

        logicManagerDriver.addManager(0,sub.getName(),storeData.getName());

        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());
        ProductData pData=data.getProductData(Data.EDIT);
        assertFalse(sub.editProductFromStore(pData).getValue());
        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * part of test use case 4.1.3 - edit product
     */
    @Test
    public void testSuccessEditProduct(){
        setUpProductAdded();
        ProductData productData = data.getProductData(Data.EDIT);
        assertTrue(this.subscribe.editProductFromStore(productData).getValue());
        StoreData storeData = data.getStore(Data.VALID);
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        Product product = store.getProduct(productData.getProductName());
        assertNotNull(product);
        assertEquals(productData.getPrice(), product.getPrice(),0.01);
        assertEquals(productData.getAmount(), product.getAmount());
        tearDownStore();
    }

    /**
     * use case 4.2.1.1 -add product to store
     */
    @Test
    public void testAddDiscountToStoreSuccess(){
        setUpProductAdded();
        StoreData storeData = data.getStore(Data.VALID);
        List <Discount> discounts = data.getDiscounts(Data.VALID);
        assertTrue(this.subscribe.addDiscountToStore(storeData.getName(), discounts.get(0)).getValue());
        tearDownStore();
    }

    /**
     * use case 4.2.1.1 -add product to store
     */
    @Test
    public void checkAddDiscountToStoreNotManager() {
        setUpProductAdded();
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());
        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());

        StoreData storeData = data.getStore(Data.VALID);
        List <Discount> discounts = data.getDiscounts(Data.VALID);
        assertFalse(sub.addDiscountToStore(storeData.getName(), discounts.get(0)).getValue());

        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * use case 4.2.1.1 -add product to store
     */
    @Test
    public void checkAddDiscountToStoreHasNoPermission() {
        setUpProductAdded();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());

        logicManagerDriver.addManager(0,sub.getName(),storeData.getName());

        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());
        List <Discount> discounts = data.getDiscounts(Data.VALID);
        assertFalse(sub.addDiscountToStore(storeData.getName(), discounts.get(0)).getValue());

        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * use case 4.2.1.2 -remove product from store
     */
    @Test
    public void testRemoveDiscountFromStoreSuccess(){
        setUpDiscountAdded();
        StoreData storeData = data.getStore(Data.VALID);
        List <Discount> discounts = data.getDiscounts(Data.VALID);
        Discount discount = discounts.get(0);
        assertTrue(this.subscribe.deleteDiscountFromStore(discount.getId(), storeData.getName()).getValue());
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertTrue(store.getDiscount().values().isEmpty());
        tearDownStore();
    }

    /**
     * use case 4.2.1.2 -remove product from store
     */
    @Test
    public void checkRemoveDiscountFromStoreNotManager() {
        setUpDiscountAdded();
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());
        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());

        StoreData storeData = data.getStore(Data.VALID);
        List <Discount> discounts = data.getDiscounts(Data.VALID);
        Discount discount = discounts.get(0);
        assertFalse(sub.deleteDiscountFromStore(discount.getId(), storeData.getName()).getValue());
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertFalse(store.getDiscount().values().isEmpty());

        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * use case 4.2.1.2 -remove product from store
     */
    @Test
    public void checkRemoveDiscountFromStoreHasNoPermission() {
        setUpDiscountAdded();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());

        logicManagerDriver.addManager(0,sub.getName(),storeData.getName());

        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());

        List <Discount> discounts = data.getDiscounts(Data.VALID);
        Discount discount = discounts.get(0);
        assertFalse(sub.deleteDiscountFromStore(discount.getId(), storeData.getName()).getValue());
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertFalse(store.getDiscount().values().isEmpty());

        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * use case 4.5 add manager
     * test we cant add manager twice
     */
    @Test
    public void testAlreadyManager() {
        setUpStoreOpened();
        assertFalse(sub.addManager(data.getSubscribe(Data.ADMIN),data.getStore(Data.VALID).getName()).getValue());
        tearDownStore();
    }

    /**
     * use case 4.5 add manager
     */
    @Test
    public void testAddManagerStoreSuccess() {
        setUpStoreOpened();
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        StoreData storeData = data.getStore(Data.VALID);
        assertTrue(subscribe.addManager(admin,storeData.getName()).getValue());
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertEquals(2,store.getPermissions().values().size());
        tearDownStore();
    }

    /**
     * use case 4.5 add manager
     * check cant add manager without being owner
     */
    @Test
    public void checkAddManagerNotOwner() {
        setUpDiscountAdded();
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());
        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());

        Subscribe admin = data.getSubscribe(Data.ADMIN);
        StoreData storeData = data.getStore(Data.VALID);
        assertFalse(sub.addManager(admin,storeData.getName()).getValue());
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertEquals(1,store.getPermissions().values().size());

        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * use case 4.5 add manager
     * check cant add manager without permission
     */
    @Test
    public void checkAddManagerHasNoPermission() {
        setUpDiscountAdded();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());

        logicManagerDriver.addManager(0,sub.getName(),storeData.getName());

        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());

        Subscribe admin = data.getSubscribe(Data.ADMIN);
        assertFalse(sub.addManager(admin,storeData.getName()).getValue());
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertEquals(2,store.getPermissions().values().size());

        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * test use case 4.6.1 - add permissions to manager
     */
    @Test
    public void testAddPermissionDontHavePermission() {
        setUpDiscountAdded();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());

        logicManagerDriver.addManager(0,sub.getName(),storeData.getName());

        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());

        Subscribe admin = data.getSubscribe(Data.ADMIN);
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        List<PermissionType> permissionList = data.getPermissionTypeList();
        assertFalse(sub.addPermissions(permissionList, admin.getName(),validStoreName).getValue());
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertEquals(2,store.getPermissions().values().size());

        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * test use case 4.6.1 - add permissions to manager
     */
    @Test
    public void testAddPermissionNotManager() {
        setUpDiscountAdded();
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());
        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());

        StoreData storeData = data.getStore(Data.VALID);
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        List<PermissionType> permissionList = data.getPermissionTypeList();
        assertFalse(sub.addPermissions(permissionList, admin.getName(),validStoreName).getValue());
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertEquals(1,store.getPermissions().values().size());

        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * test use case 4.6.1 - add permissions to manager
     */
    @Test
    public void testAddPermissionSuccess() {
        setUpManagerAdded();
        List<PermissionType> list = data.getPermissionTypeList();
        StoreData storeData= data.getStore(Data.VALID);
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        assertTrue(this.subscribe.addPermissions(list, storeData.getName(),admin.getName()).getValue());
        tearDownStore();
    }

    /**
     * use case 4.6.2 - remove permission
     */
    @Test
    public void testRemovePermissionNotManager() {
        setUpAddedPermissions();
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());
        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());

        StoreData storeData = data.getStore(Data.VALID);
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        List<PermissionType> permissionList = data.getPermissionTypeList();

        assertFalse(sub.removePermissions(permissionList, admin.getName(),validStoreName).getValue());
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertEquals(2,store.getPermissions().values().size());

        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * use case 4.6.2 - remove permission
     */
    @Test
    public void testRemovePermissionDontHavePermission() {
        setUpAddedPermissions();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());

        logicManagerDriver.addManager(0,sub.getName(),storeData.getName());

        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());

        Subscribe admin = data.getSubscribe(Data.ADMIN);
        String validStoreName = data.getProductData(Data.VALID).getStoreName();
        List<PermissionType> permissionList = data.getPermissionTypeList();

        assertFalse(sub.removePermissions(permissionList, admin.getName(),validStoreName).getValue());
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertEquals(3,store.getPermissions().values().size());

        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
    }

    /**
     * use case 4.6.2 - remove permission
     */
    @Test
    public void testRemovePermissionSuccess() {
        setUpAddedPermissions();
        List<PermissionType> list = data.getPermissionTypeList();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        assertTrue(this.subscribe.removePermissions(list, storeData.getName(),admin.getName()).getValue());
        tearDownStore();
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
        assertTrue(sub.removeManager(data.getSubscribe(Data.ADMIN),data.getStore(Data.VALID).getName()).getValue());
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
        assertFalse(sub.removeManager(data.getSubscribe(Data.ADMIN),validStoreName).getValue());
        sub.getPermissions().put(validStoreName,permission);
    }

    /**
     * use case 4.9.1 - view request
     */
    @Test
    public void testViewRequest(){
        setUpRequestAdded();
        //testViewRequestWrongStore();
        //testViewRequestNullName();
    }

    //TODO - fix this
//    /**
//     * part of use case 4.9.1 - view request
//     */
//    private void testViewRequestWrongStore() {
//        Request request1 = data.getRequest(Data.WRONG_STORE);
//        assertTrue(sub.viewRequest(request1.getStoreName()).isEmpty());
//    }

    //TODO - fix this

//    /**
//     * part of use case 4.9.1 - view request
//     */
//    private void testViewRequestNullName() {
//        Request request2 = data.getRequest(Data.NULL_NAME);
//        assertTrue(sub.viewRequest(request2.getStoreName()).isEmpty());
//    }

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
        assertNull(sub.replayToRequest(request1.getStoreName(), request1.getId(), "comment").getValue());
    }

    /**
     * part of test use case 4.9.2 - reply request
     */
    private void testReplayRequestWrongStore() {
        Request request2 = data.getRequest(Data.WRONG_STORE);
        assertNull(sub.replayToRequest(request2.getStoreName(), request2.getId(), "comment").getValue());
    }

    /**
     * part of test use case 4.9.2 - reply request
     */
    private void testReplayRequestWrongID() {
        Request request3 = data.getRequest(Data.WRONG_ID);
        assertNull(sub.replayToRequest(request3.getStoreName(), request3.getId(), "comment").getValue());
    }

    /**
     * part of test use case 4.9.2 - reply request
     */
    private void testReplayRequestNullRequest() {
        Request request4 = data.getRequest(Data.VALID);
        assertNull(sub.replayToRequest(request4.getStoreName(), request4.getId(), null).getValue());
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

    @After
    public void tearDown(){
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        try {
            daoHolder.getSubscribeDao().remove(subscribe.getName());
        }catch (Exception e) {}
        subscribe = data.getSubscribe(Data.ADMIN);
        try {
            daoHolder.getSubscribeDao().remove(subscribe.getName());
        }catch (Exception e){}
    }

}