package LogicManagerTests;

import Data.Data;
import Data.TestData;
import DataAPI.*;
import Domain.Discount.*;
import Domain.*;
import Domain.Discount.Term.*;
import Domain.PurchasePolicy.*;
import Domain.Notification.*;
import Domain.PurchasePolicy.ComposePolicys.AndPolicy;
import Domain.PurchasePolicy.ComposePolicys.OrPolicy;
import Domain.PurchasePolicy.ComposePolicys.XorPolicy;
import Persitent.*;
import Persitent.DaoHolders.DaoHolder;
import Stubs.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import Utils.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.mockito.internal.matchers.Not;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

//class for Unit test all stubs

public class LogicManagerAllStubsTest {

    protected LogicManager logicManager;
    protected User currUser;
    protected ConcurrentHashMap<Integer,User> connectedUsers;
    protected ConcurrentHashMap<String,Subscribe> users;
    protected ConcurrentHashMap<String,Store> stores;
    protected PaymentSystem paymentSystem;
    protected SupplySystem supplySystem;
    protected TestData data;
    protected static DaoHolder daos;
    protected Cache cashe;


    @BeforeClass
    public static void beforeClass() throws Exception {
        daos=new StubDaoHolder();
    }

    /**
     * Adding Stores must be in type StoreStub
     * example: stores.put(Key,new StoreStub(...))
     * Adding Users must be in type UserStub
     * example: users.put(Key, new UserStub(...))
     */
    @Before
    public void setUp() {
        //External Systems
        supplySystem=new ProxySupply();
        paymentSystem=new ProxyPayment();
        this.cashe=new CacheStub();
        init();
    }

    @Transactional
    protected void init() {
        data=new TestData();
        users=new ConcurrentHashMap<>();
        stores=new ConcurrentHashMap<>();
        connectedUsers =new ConcurrentHashMap<>();
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        try {
            logicManager = new LogicManager(subscribe.getName(), subscribe.getPassword(),
                    paymentSystem,supplySystem,daos,cashe);
        } catch (Exception e) {
            fail();
        }
        Subscribe other=data.getSubscribe(Data.VALID2);
        logicManager.register(other.getName(),other.getPassword());
    }

    /**--------------------------------set-ups-------------------------------------------------------------------*/

    /**
     * set up connect
     */
    protected void setUpConnect(){
        logicManager.connectToSystem();
        logicManager.connectToSystem();
        logicManager.connectToSystem();
        //work with the regular user has current user
        cashe.addConnectedUser(data.getId(Data.VALID),new UserStub());
        cashe.addConnectedUser(data.getId(Data.ADMIN),new UserStub());
        cashe.addConnectedUser(data.getId(Data.VALID2),new UserStub());
        currUser=cashe.findUser(data.getId(Data.VALID));
    }

    /**
     * set up for register a user
     */
    private void setUpRegisteredUser(){
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        logicManager.register(subscribe.getName(),subscribe.getPassword());
    }

    /**
     * set up for valid user to be login and registered
     */
    protected void setUpLogedInUser(){
        setUpRegisteredUser();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        Response<Boolean> response =  logicManager.login(data.getId(Data.VALID),
                subscribe.getName(),subscribe.getPassword());
        assertTrue(response.getValue());
    }

    /**
     * set up for opening a valid store
     */
    protected void setUpOpenedStore(){
        setUpLogedInUser();
        StoreData storeData = data.getStore(Data.VALID);
        logicManager.openStore(data.getId(Data.VALID), storeData);
        String storeName = storeData.getName();
        Permission permission = new Permission(data.getSubscribe(Data.VALID));
        StoreStub storeStub = new StoreStub(storeName,permission,"description");
        permission.setStore(storeStub);
        daos.getStoreDao().update(storeStub);
    }

    /**
     * set up for adding a new manager, sub manager of the connected user
     */
    private void setUpManagerAdded(){
        setUpOpenedStore();
        logicManager.addManager(data.getId(Data.VALID),data.getSubscribe(Data.ADMIN).getName(),data.getStore(Data.VALID).getName());
    }

    /**
     * set up manager added and make the new manager adding another manager
     * set up to check use case 4.7 that the mangers will be removed and all the managers he managed
     * will be removed as well
     */
    protected void setUpManagerAddedSubManagerAdded(){
        setUpPermissionsAdded();
    }

    /**
     * set up a sub manager of current logged in user with permissions of ADD_MANAGER and ADD_OWNER
     */
    protected void setUpPermissionsAdded(){
        setUpManagerAdded();
        currUser.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName());
    }

    /**
     * set up for the store to be opened and a valid product to be added
     */
    protected void setUpProductAdded(){
        setUpOpenedStore();
        logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.VALID));
    }

    /**
     * set up discount added to the store
     */
    protected void setUpDiscountAdded() {
        setUpProductAdded();
        Discount discount=data.getDiscounts(Data.VALID).get(0);
        GsonBuilder builderDiscount = new GsonBuilder();
        builderDiscount.registerTypeAdapter(Discount.class, new InterfaceAdapter());
        Gson discountGson = builderDiscount.create();
        String discountToAdd=discountGson.toJson(discount,Discount.class);
        logicManager.addDiscount(data.getId(Data.VALID),discountToAdd,
                data.getStore(Data.VALID).getName()).getValue();
    }

    /**
     * set up the policy of the store
     */
    private void setUpPurchasePolicyAdded() {
        setUpProductAdded();
        PurchasePolicy policy = data.getPurchasePolicy(Data.VALID_BASKET_PURCHASE_POLICY);
        GsonBuilder builderPolicy = new GsonBuilder();
        builderPolicy.registerTypeAdapter(PurchasePolicy.class, new InterfaceAdapter());
        Gson policyGson = builderPolicy.create();
        String policyToAdd=policyGson.toJson(policy,PurchasePolicy.class);
        logicManager.updatePolicy(data.getId(Data.VALID),policyToAdd,
                data.getStore(Data.VALID).getName()).getValue();
    }

    /**
     * set up for a state where a valid request was added for a valid store
     */
    protected void setUpRequestAdded(){
        setUpOpenedStore();
        Request request = data.getRequest(Data.VALID);
        logicManager.addRequest(data.getId(Data.VALID),request.getStoreName(),request.getContent());
    }

    /**
     * set up a valid product in a valid cart
     * //extended by the tests of LogicManageRealTests
     */
    protected void setUpProductAddedToCart(){
        setUpProductAdded();
    }

    /**
     * set up a user and store with a valid non empty purchase history
     */
    protected void setUpBoughtProduct(){
        setUpProductAddedToCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        logicManager.purchaseCart(data.getId(Data.VALID), country, paymentData, address);
    }

    /**
     * set up bought products and go to admin state for getting admin permissions
     */
    protected void setUpBoughtProductAdminState(){
        setUpBoughtProduct();
        Subscribe admin=data.getSubscribe(Data.ADMIN);
        logicManager.login(data.getId(Data.ADMIN),admin.getName(),admin.getPassword());
        //currUser.setState(users.get(admin.getName()));
    }


    /**--------------------------------set-ups-------------------------------------------------------------------*/

    /**
     * test: use case 1.1 - Init System
     * checking for exception due to false connection from the payment external system
     */
    @Test
    @Transactional
    public void testFailPaymentSystem() {
        PaymentSystem stubPayment = new PaymentSystemStub();
        ProxySupply proxySupply = new ProxySupply();
        assertFalse(stubPayment.connect());
        assertTrue(proxySupply.connect());
        data=new TestData();
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        try {
            LogicManager test = new LogicManager(subscribe.getName(), subscribe.getPassword(),
                    stubPayment, supplySystem, daos,cashe);
            fail();
        } catch (Exception e) {
            //There is already one admin because of init
            List<Admin> admins = daos.getSubscribeDao().getAllAdmins();
            assertEquals(1, admins.size());
        }
    }

    /**
     * test: use case 1.1 - init System
     * the system check success connect
     */
    @Test
    @Transactional
    public void testInitSuccess() {
        //the call for logic manger is in the @Before
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        List<Admin> admins = daos.getSubscribeDao().getAllAdmins();
        assertEquals(1, admins.size());
        assertEquals(subscribe.getName(), admins.get(0).getName());
    }

    /**
     * test: use case 1.1 - Init System Fail Supply System
     * checking for exception due to false connection output from the supply external system
     */
    @Test
    @Transactional
    public void testFailSupplySystem() {
        ProxyPayment proxyPayment = new ProxyPayment();
        SupplySystem stubSupply = new SupplySystemStub();
        assertTrue(proxyPayment.connect());
        assertFalse(stubSupply.connect());
        data=new TestData();
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        try {
            LogicManager test = new LogicManager(subscribe.getName(), subscribe.getPassword(),
                    proxyPayment,stubSupply,daos,cashe);
            fail();
        } catch (Exception e) {
            //There is already one admin because of init
            List<Admin> admins = daos.getSubscribeDao().getAllAdmins();
            assertEquals(1, admins.size());
        }
    }

    /**
     * test connection to the system
     */
    @Test
    public void testConnectToSystem() {
        for (int id = 0; id < 10; id++) {
            assertEquals(id, logicManager.connectToSystem());
            assertTrue(this.connectedUsers.containsKey(id));
        }
        this.connectedUsers = new ConcurrentHashMap<>();
    }

    /**
     * part of test use case 2.2 - Register
     */
    @Test
    @Transactional
    public void testRegisterSuccess() {
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertTrue(logicManager.register(subscribe.getName(),subscribe.getPassword()).getValue());
        daos.getSubscribeDao().remove(subscribe.getName());
        tearDownConnect();
    }

    /**
     * part of test use case 2.2 - Register
     * fail test - try to register with wrong user name
     */
    @Test
    @Transactional
    public void testRegisterFailWrongName() {
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.WRONG_NAME);
        assertFalse(logicManager.register(subscribe.getName(),subscribe.getPassword()).getValue());
        assertNull(daos.getSubscribeDao().find(subscribe.getName()));
        tearDownConnect();
    }

    /**
     * part of test use case 2.2 - Register
     * fail test - try to register with wrong password
     */
    @Test
    @Transactional
    public void testRegisterFailWrongPassword() {
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.WRONG_PASSWORD);
        assertFalse(logicManager.register(subscribe.getName(), subscribe.getPassword()).getValue());
        assertNull(daos.getSubscribeDao().find(subscribe.getName()));
        tearDownConnect();
    }

    /**
     * part of test use case 2.2 - Register
     */
    @Test
    @Transactional
    public void testRegisterFailNull() {
        setUpConnect();
        Subscribe subscribe = data.getSubscribe(Data.NULL);
        assertFalse(logicManager.register(subscribe.getName(), subscribe.getName()).getValue());
        tearDownConnect();
    }

    /**
     * test a case that trying to register user twice
     */
    @Test
    @Transactional
    public void testRegisterFailAlreadyRegistered(){
        setUpConnect();
        setUpRegisteredUser();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertFalse(logicManager.register(subscribe.getName(),subscribe.getPassword()).getValue());
        tearDownRegisteredUser();
        tearDownConnect();
    }

    /**
     * part of use case 2.3 - Login
     */
    @Test
    @Transactional
    public void testLoginFailNull() {
        setUpRegisteredUser();
        Subscribe subscribe = data.getSubscribe(Data.NULL);
        assertFalse((logicManager.login(data.getId(Data.VALID),
                subscribe.getName(),
                subscribe.getPassword())).getValue());
        tearDownRegisteredUser();
    }

    /**
     * part of use case 2.3 - Login
     */
    @Test
    @Transactional
    public void testLoginFailWrongName() {
        setUpRegisteredUser();
        Subscribe subscribe = data.getSubscribe(Data.WRONG_NAME);
        assertFalse((logicManager.login(data.getId(Data.VALID), subscribe.getName(), subscribe.getPassword())).getValue());
        tearDownRegisteredUser();
    }

    /**
     * part of use case 2.3 - Login
     */
    @Test
    @Transactional
    public void testLoginFailWrongPassword() {
        setUpRegisteredUser();
        Subscribe subscribe = data.getSubscribe(Data.WRONG_PASSWORD);
        assertFalse((logicManager.login(data.getId(Data.VALID), subscribe.getName(), subscribe.getPassword())).getValue());
        tearDownRegisteredUser();
    }

    /**
     * part of use case 2.3 - Login
     */
    @Test
    @Transactional
    public void testLoginSuccess() {
        setUpRegisteredUser();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        Response<Boolean> response = logicManager.login(data.getId(Data.VALID), subscribe.getName(),
                subscribe.getPassword());
        assertTrue(response.getValue());
        logicManager.logout(data.getId(Data.VALID));
        tearDownRegisteredUser();
    }


    /**
     * use case 2.4.1 - view all stores details
     */
    @Test
    @Transactional
    public void testViewDataStores() {
        setUpOpenedStore();
        List<StoreData> expected = new LinkedList<>();
        expected.add(data.getStore(Data.VALID));
        assertEquals(expected, logicManager.viewStores().getValue());
        assertNotEquals(null, logicManager.viewStores().getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.4.2 - view the products in some store with valid data test
     */
    @Test
    @Transactional
    public void testViewProductsInStore() {
        setUpProductAdded();
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        assertEquals(expected, logicManager.viewProductsInStore(storeName).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.4.2 - view the products in some store with valid data test
     */
    @Test
    @Transactional
    public void testViewProductInStoreNotExists() {
        setUpProductAdded();
        String storeName = data.getStore(Data.WRONG_STORE).getName();
        assertNull(logicManager.viewProductsInStore(storeName).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.4.2 - view the products in some store with valid data test
     */
    @Test
    @Transactional
    public void testViewProductInStoreNull() {
        setUpProductAdded();
        String storeName = data.getStore(Data.NULL_STORE).getName();
        assertNull(logicManager.viewProductsInStore(storeName).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.4.2 - view the products in some store with null category test
     */
    @Test
    @Transactional
    public void testViewProductsInStoreNullCategory() {
        setUpProductAdded();
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        expected.add(data.getProductData(Data.NULL_CATEGORY));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName).getValue());
        expected.remove(data.getProductData(Data.NULL_CATEGORY));
        tearDownOpenStore();
    }

    /**
     * use case 2.4.2 - view the products in some store with null name test
     */
    @Test
    @Transactional
    public void testViewProductsInStoreNullCategoryName() {
        setUpProductAdded();
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        expected.add(data.getProductData((Data.NULL_NAME)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName).getValue());
        expected.remove(data.getProductData((Data.NULL_NAME)));
        tearDownOpenStore();
    }

    /**
     * use case 2.4.2 - view the products in some store with null discount test
     */
    @Test
    @Transactional
    public void testViewProductsInStoreNullDiscount() {
        setUpProductAdded();
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        expected.add(data.getProductData((Data.NULL_DISCOUNT)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName).getValue());
        expected.remove(data.getProductData((Data.NULL_DISCOUNT)));
        tearDownOpenStore();
    }

    /**
     * use case 2.4.2 - view the products in some store with null purchase test
     */
    @Test
    @Transactional
    public void testViewProductsInStoreNullPurchase() {
        setUpProductAdded();
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        expected.add(data.getProductData((Data.NULL_PURCHASE)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName).getValue());
        expected.remove(data.getProductData((Data.NULL_PURCHASE)));
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view specific product
     */
    @Test
    @Transactional
    public void testViewSpecificProductWrongSearch() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.NULL_SEARCH);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertTrue(products.isEmpty());
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Transactional
    public void testViewSpecificProductWrongFilterNullValue() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.NULL_VALUE);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertTrue(products.isEmpty());
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Transactional
    public void testViewSpecificProductWrongFilterNegativeMin() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.NEGATIVE_MIN);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertTrue(products.isEmpty());
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Transactional
    public void testViewSpecificProductWrongFilterNegativeMax() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.NEGATIVE_MAX);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertTrue(products.isEmpty());
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Transactional
    public void testViewSpecificProductWrongFilterNullCategory() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.NULL_CATEGORY);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertTrue(products.isEmpty());
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Transactional
    public void testViewSpecificProductWrongFilterNull() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.NULL);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertTrue(products.isEmpty());
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Transactional
    public void testViewSpecificProductFilterNone() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.VALID);
        filter.setSearch(Search.NONE);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertNotNull(products);
        assertTrue(products.isEmpty());
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Transactional
    public void testViewSpecificProductFilterProductName() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.VALID);
        filter.setSearch(Search.PRODUCT_NAME);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertNotNull(products);
        assertTrue(products.isEmpty());
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Transactional
    public void testViewSpecificProductFilterKeyWord() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.VALID);
        filter.setSearch(Search.KEY_WORD);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertNotNull(products);
        assertTrue(products.isEmpty());
        tearDownOpenStore();
    }

    /**
     * part of use case 2.5 - view spesific products
     */
    @Test
    @Transactional
    public void testViewSpecificProductFilterCategory() {
        setUpProductAdded();
        Filter filter = data.getFilter(Data.VALID);
        filter.setSearch(Search.CATEGORY);
        List<ProductData> products = logicManager.viewSpecificProducts(filter).getValue();
        assertNotNull(products);
        assertTrue(products.isEmpty());
        tearDownOpenStore();
    }

    /**
     * use case 2.7.1 fails tests
     */
    @Test
    @Transactional
    public void testWatchCartDetails() {
        setUpProductAddedToCart();
        testWatchCartDetailsNull();
        testWatchCartDetailsNullStore();
        tearDownOpenStore();
    }

    /**
     * use case 2.7.1 fail when the product is null
     */
    private void testWatchCartDetailsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        CartData cartData = logicManager.watchCartDetails(data.getId(Data.VALID)).getValue();
        assertFalse(cartData.getProducts().contains(productData));
    }

    /**
     * use case 2.7.1 fail when the basket is null
     */
    private void testWatchCartDetailsNullStore() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        CartData cartData = logicManager.watchCartDetails(data.getId(Data.VALID)).getValue();
        assertFalse(cartData.getProducts().contains(productData));
    }

    /**
     * use case 2.7.2 delete product from cart
     * fails tests
     */
    @Test
    @Transactional
    public void testDeleteProductFromCart() {
        setUpProductAddedToCart();
        testDeleteProductFromCartBasketIsNull();
        testDeleteProductFromCartProductIsNull();
        tearDownOpenStore();
    }

    /**
     * use case 2.7.2 delete product from cart
     * fails test - product is null
     */
    private void testDeleteProductFromCartProductIsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        assertFalse(logicManager.deleteFromCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName()).getValue());
    }

    /**
     * use case 2.7.2 delete product from cart
     * fails test - basket is null
     */
    private void testDeleteProductFromCartBasketIsNull() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.deleteFromCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName()).getValue());
    }

    /**
     * use case 2.7.3 fail tests
     */
    @Test
    @Transactional
    public void testEditProductsInCart() {
        setUpProductAddedToCart();
        testEditProductsInCartBasketIsNull();
        testEditProductsInCartNegativeAmount();
        testEditProductsInCartProductIsNull();
        tearDownOpenStore();
    }

    /**
     * use case 2.7.3 fail test when the product is null
     */
    private void testEditProductsInCartProductIsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        assertFalse(logicManager.editProductInCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName(),productData.getAmount()).getValue());
    }

    /**
     * use case 2.7.3 fail test when the amount is negative
     */
    private void testEditProductsInCartNegativeAmount() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.editProductInCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName(),productData.getAmount()).getValue());
    }

    /**
     * use case 2.7.3 fail test when the basket is null
     */
    private void testEditProductsInCartBasketIsNull() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.editProductInCart(data.getId(Data.VALID),productData.getProductName(),productData.getStoreName(),productData.getAmount() + 1).getValue());
    }

    /**
     *  use case 2.7.4 - add product to cart
     */
    @Test
    @Transactional
    public void testAddProductToCart() {
        setUpProductAdded();
        testAddProductToCartInvalidStore();
        tearDownOpenStore();
    }

    /**
     * part of use case 2.7.4 - add product to cart
     */
    private void testAddProductToCartInvalidStore() {
        ProductData product = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.addProductToCart(data.getId(Data.VALID),product.getProductName(),product.getStoreName(),product.getAmount()).getValue());
    }


    /**
     * use case 2.8 - test reserveCart Products
     * success tests
     */
    @Test
    @Transactional
    public void testSuccessBuyProducts() {
        setUpProductAddedToCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertTrue(logicManager.purchaseCart(data.getId(Data.VALID),country, paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartPaymentSystemCrashed() {
        paymentSystem = new PaymentSystemStubPay();
        init();
        setUpProductAddedToCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID),country, paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - buy Cart
     */
    @Test
    public void testBuyCartSupplySystemCrashed() {
        supplySystem = new SupplySystemStubDeliver();
        init();
        setUpProductAddedToCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), country, paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartSupplySystemCrashedAndPaymentCancel() {
        paymentSystem = new PaymentSystemStubCancel();
        supplySystem = new SupplySystemStubDeliver();
        init();
        setUpProductAddedToCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), country, paymentData, address).getValue());
        tearDownOpenStore();

    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartNullPayment(){
        setUpProductAddedToCart();
        // null data payment
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID),country, null, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartNullAddressPayment() {
        setUpProductAddedToCart();
        // null address in payment
        PaymentData paymentData = data.getPaymentData(Data.NULL_ADDRESS);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID),country, paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartEmptyAddressPayment() {
        setUpProductAddedToCart();
        // empty address in payment
        PaymentData paymentData = data.getPaymentData(Data.EMPTY_ADDRESS);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID),country, paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartEmptyPayment() {
        setUpProductAddedToCart();
        // empty payment
        PaymentData paymentData = data.getPaymentData(Data.EMPTY_PAYMENT);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID),country, paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartPaymentNullName() {
        setUpProductAddedToCart();
        // null name in payment
        PaymentData paymentData = data.getPaymentData(Data.NULL_NAME);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), country, paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartPaymentEmptyName(){
        setUpProductAddedToCart();
        // empty name in payment
        PaymentData paymentData = data.getPaymentData(Data.EMPTY_NAME);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), country, paymentData, address).getValue());
        tearDownOpenStore();

    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartNullAddress() {
        setUpProductAddedToCart();
        // null address
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.NULL_ADDRESS).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), country,paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartEmptyAddress() {
        setUpProductAddedToCart();
        // empty address
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.EMPTY_ADDRESS).getAddress();
        String country = data.getDeliveryData(Data.VALID).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), country, paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartEmptyCountry() {
        setUpProductAddedToCart();
        // empty country
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.EMPTY_COUNTRY).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), country, paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 2.8 - test buy Cart
     */
    @Test
    @Transactional
    public void testBuyCartNullCountry() {
        setUpProductAddedToCart();
        // null country
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        String country = data.getDeliveryData(Data.NULL_COUNTRY).getCountry();
        assertFalse(logicManager.purchaseCart(data.getId(Data.VALID), country, paymentData, address).getValue());
        tearDownOpenStore();
    }

    /**
     * test: use case 3.1 - Logout
     */
    @Test
    @Transactional
    public void testLogout() {
        setUpLogedInUser();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        int id = data.getId(Data.VALID);
        assertTrue(logicManager.logout(id).getValue());
        Subscribe daoSubscribe = cashe.findSubscribe(subscribe.getName());
        assertEquals(-1, daoSubscribe.getSessionNumber().intValue());
        tearDownLogin();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    @Transactional
    public void testOpenStoreNull() {
        setUpLogedInUser();
        assertFalse(logicManager.openStore(data.getId(Data.VALID), data.getStore(Data.NULL)).getValue());
        tearDownLogin();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    @Transactional
    public void testOpenStoreNullName() {
        setUpLogedInUser();
        assertFalse(logicManager.openStore(data.getId(Data.VALID), data.getStore(Data.NULL_NAME)).getValue());
        tearDownLogin();
    }

    @Test
    @Transactional
    public void testOpenStoreNullDiscription() {
        setUpLogedInUser();
        assertFalse(logicManager.openStore(data.getId(Data.VALID), data.getStore(Data.NULL_DESCRIPTION)).getValue());
        tearDownLogin();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    @Test
    @Transactional
    public void testOpenStoreReopen() {
        setUpOpenedStore();
        StoreData storeData = data.getStore(Data.VALID);
        assertFalse(logicManager.openStore(data.getId(Data.VALID), storeData).getValue());
        tearDownOpenStore();
    }

    @Transactional
    @Test
    public void testWriteReview(){
        setUpBoughtProduct();
        testWriteReviewTest();
        tearDownOpenStore();
    }
    /**
     * use case 3.3 - write review
     */
    protected void testWriteReviewTest() {
        testWriteReviewInvalid();
        testWriteReviewValid();
    }

    /**
     * part of use case 3.3 - invalid tests
     */
    private void testWriteReviewInvalid() {
        testWriteReviewInvalidNullStore();
        testWriteReviewInvalidNullPoduct();
        testWriteReviewInvalidNullContent();
        testWriteReviewInvalidEmptyContent();
        testWriteReviewInvalidWrongStore();
    }


    /**
     * part of use case 3.3 - write review with null store
     */
    private void testWriteReviewInvalidNullStore() {
        Review review = data.getReview(Data.NULL_STORE);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(), review.getProductName(), review.getContent()).getValue());
    }

    /**
     * part of use case 3.3 - write review with null product
     */
    private void testWriteReviewInvalidNullPoduct() {
        Review review = data.getReview(Data.NULL_PRODUCT);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(), review.getProductName(), review.getContent()).getValue());
    }

    /**
     * part of use case 3.3 - write review with null content
     */
    private void testWriteReviewInvalidNullContent() {
        Review review = data.getReview(Data.NULL_CONTENT);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(), review.getProductName(), review.getContent()).getValue());
    }

    /**
     * part of use case 3.3 - write review with empty content
     */
    private void testWriteReviewInvalidEmptyContent() {
        Review review = data.getReview(Data.EMPTY_CONTENT);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(), review.getProductName(), review.getContent()).getValue());
    }

    /**
     * part of use case 3.3 - write review with wrong store
     */
    private void testWriteReviewInvalidWrongStore() {
        Review review = data.getReview(Data.WRONG_STORE);
        assertFalse(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()).getValue());
    }


    /**
     * part of use case 3.3 - write review
     */
    protected void testWriteReviewValid() {
        Review review = data.getReview(Data.VALID);
        assertTrue(logicManager.addReview(data.getId(Data.VALID), review.getStore(),review.getProductName(),review.getContent()).getValue());
    }

    /**
     * use case 3.5 -add request
     * in this level we test the:
     * 1. enter null content
     * 2. enter request to invalid store
     */
    @Test
    public void testAddRequest(){
        setUpOpenedStore();
        testAddRequestSuccess();
        tearDownOpenStore();
    }


    /**
     * part of use case 3.5 -add request
     */
    private void testAddRequestSuccess() {
        Request request = data.getRequest(Data.VALID);
        assertTrue(logicManager.addRequest(data.getId(Data.VALID),request.getStoreName(),request.getContent()).getValue());
    }

    @Test
    public void testAddRequestWrongName() {
        setUpOpenedStore();
        Request request1 = data.getRequest(Data.WRONG_STORE);
        assertFalse(logicManager.addRequest(data.getId(Data.VALID),request1.getStoreName(), request1.getContent()).getValue());
        tearDownOpenStore();
    }

    /**
     * part of use case 3.5 -add request
     */
    @Test
    public void testAddRequestNullName() {
        setUpOpenedStore();
        Request request2 = data.getRequest(Data.NULL_NAME);
        assertFalse(logicManager.addRequest(data.getId(Data.VALID),request2.getStoreName(), request2.getContent()).getValue());
        tearDownOpenStore();
    }

    /**
     * part of use case 3.5 -add request
     */
    @Test
    public void testAddRequestContentNull() {
        setUpOpenedStore();
        Request request2 = data.getRequest(Data.NULL_CONTENT);
        assertFalse(logicManager.addRequest(data.getId(Data.VALID),request2.getStoreName(), request2.getContent()).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 3.7 - watch purchase history
     */
    @Test
    public void testWatchPurchaseHistory() {
        setUpBoughtProduct();
        List<Purchase> purchases = logicManager.watchMyPurchaseHistory(data.getId(Data.VALID)).getValue();
        assertNotNull(purchases);
        assertTrue(purchases.isEmpty());
        tearDownOpenStore();
    }

    /**
     * use case 4.1.1 - add product fail
     */
    @Test
    @Transactional
    public void testAddProductFail(){
        setUpOpenedStore();
        testAddProductFailNullProduct();
        testAddProductFailNullProductName();
        testAddProductFailNullCategory();
        testAddProductNullStoreName();
        testAddProductNullDiscount();
        testAddProductNegativeAmount();
        testAddProductNegativePrice();
        testAddProductNullPurchasePolicy();

    }

    /**
     * part of use case 4.1.1 - add product
     */
    private void testAddProductNullPurchasePolicy() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NULL_PURCHASE)).getValue());
    }

    /**
     * part of use case 4.1.1 - add product
     */
    private void testAddProductNegativePrice() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NEGATIVE_PRICE)).getValue());
    }

    /**
     * part of use case 4.1.1 - add product
     */
    private void testAddProductNegativeAmount() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NEGATIVE_AMOUNT)).getValue());
    }

    /**
     * part of use case 4.1.1 - add product
     */
    private void testAddProductNullDiscount() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NULL_DISCOUNT)).getValue());
    }

    /**
     * part of use case 4.1.1 - add product
     */
    private void testAddProductFailNullCategory() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NULL_CATEGORY)).getValue());
    }

    /**
     * part of use case 4.1.1 - add product
     */
    private void testAddProductFailNullProductName() {
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.NULL_NAME)).getValue());
    }

    /**
     * part of use case 4.1.1 - add product
     */
    private void testAddProductNullStoreName(){
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.WRONG_STORE)).getValue());
    }

    /**
     * part of use case 4.1.1 - add product
     */
    protected void testAddProductFailNullProduct(){
        assertFalse(logicManager.addProductToStore(data.getId(Data.VALID),null).getValue());
    }

    /**
     * use case 4.1.2 -delete product
     */
    @Test
    public void testRemoveProductFromStore(){
        setUpProductAdded();
        testRemoveProductSuccess();
        testRemoveProductTwiceFail();
        tearDownOpenStore();
    }

    /**
     * part of use case 4.1.2 -delete product
     * test remove with no exist store
     */
    private void testRemoveProductTwiceFail() {
        assertFalse(logicManager.removeProductFromStore(data.getId(Data.VALID),data.getSubscribe(Data.VALID).getName()
                ,data.getProductData(Data.VALID).getProductName()).getValue());
    }

    /**
     * part of use case 4.1.2 -delete product
     */
    protected void testRemoveProductSuccess() {
        ProductData p=data.getProductData(Data.VALID);
        assertTrue(logicManager.removeProductFromStore(data.getId(Data.VALID),p.getStoreName(),p.getProductName()).getValue());

    }

    /**
     * test use case 4.1.3 - edit product in store
     */
    @Test
    public void testEditProductSuccess() {
        setUpProductAdded();
        assertTrue(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.EDIT)).getValue());
        tearDownOpenStore();
    }

    /**
     * test use case 4.1.3 - edit product in store
     */
    @Test
    public void testEditProductFail(){
        setUpOpenedStore();
        testEditProductFailNullProduct();
        testEditProductFailNullProductName();
        testEditProductFailNullCategory();
        testEditProductNullStoreName();
        testEditProductNullDiscount();
        testEditProductNegativeAmount();
        testEditProductNegativePrice();
        testEditProductNullPurchasePolicy();
        tearDownOpenStore();
    }

    /**
     * part of test use case 4.1.3 - edit product in store
     */
    private void testEditProductNullPurchasePolicy() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NULL_PURCHASE)).getValue());
    }

    /**
     * part of test use case 4.1.3 - edit product in store
     */
    private void testEditProductNegativePrice() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NEGATIVE_PRICE)).getValue());
    }

    /**
     * part of test use case 4.1.3 - edit product in store
     */
    private void testEditProductNegativeAmount() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NEGATIVE_AMOUNT)).getValue());
    }

    /**
     * part of test use case 4.1.3 - edit product in store
     */
    private void testEditProductNullDiscount() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NULL_DISCOUNT)).getValue());
    }

    /**
     * part of test use case 4.1.3 - edit product in store
     */
    private void testEditProductFailNullCategory() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NULL_CATEGORY)).getValue());
    }

    /**
     * part of test use case 4.1.3 - edit product in store
     */
    private void testEditProductFailNullProductName() {
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.NULL_NAME)).getValue());
    }

    /**
     * part of test use case 4.1.3 - edit product in store
     */
    private void testEditProductNullStoreName(){
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),data.getProductData(Data.WRONG_STORE)).getValue());
    }

    /**
     * part of test use case 4.1.3 - edit product in store
     */
    protected void testEditProductFailNullProduct(){
        assertFalse(logicManager.editProductFromStore(data.getId(Data.VALID),null).getValue());
    }

    /**
     * use case 4.2.1.1 -add discount to store
     */
    @Test
    @Transactional
    public void testAddDiscountToStoreSuccess(){
        setUpProductAdded();
        testAddDiscountToStoreSuccessTest();
        tearDownProductAdded();
    }

    @Test
    public void testAddDiscountToStoreSuccessTest(){
        Discount discount=data.getDiscounts(Data.VALID).get(0);
        GsonBuilder builderDiscount = new GsonBuilder();
        builderDiscount.registerTypeAdapter(Discount.class, new InterfaceAdapter());
        Gson discountGson = builderDiscount.create();
        String discountToAdd=discountGson.toJson(discount,Discount.class);
        assertTrue(logicManager.addDiscount(data.getId(Data.VALID),discountToAdd,
                data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * use case 4.2.1.1 -add discount to store
     */
    @Test
    public void testAddDiscountToStoreNotExistStore(){
        setUpProductAdded();
        Discount discount=data.getDiscounts(Data.VALID).get(0);
        GsonBuilder builderDiscount = new GsonBuilder();
        builderDiscount.registerTypeAdapter(Discount.class, new InterfaceAdapter());
        Gson discountGson = builderDiscount.create();
        String discountToAdd=discountGson.toJson(discount,Discount.class);
        assertFalse(logicManager.addDiscount(data.getId(Data.VALID),discountToAdd,
                discountToAdd).getValue());
        tearDownProductAdded();
    }

    /**
     * use case 4.2.1.1 -add discount to store
     */
    @Test
    public void testAddDiscountToStoreNullDiscount(){
        setUpProductAdded();
        Discount discount=null;
        GsonBuilder builderDiscount = new GsonBuilder();
        builderDiscount.registerTypeAdapter(Discount.class, new InterfaceAdapter());
        Gson discountGson = builderDiscount.create();
        String discountToAdd=discountGson.toJson(discount,Discount.class);
        assertFalse(logicManager.addDiscount(data.getId(Data.VALID),discountToAdd,
                data.getStore(Data.VALID).getName()).getValue());
        tearDownProductAdded();
    }

    /**
     * use case 4.2.1.1 -add discount to store
     */
    @Test
    public void testAddDiscountToStoreNotValidDiscount(){
        setUpProductAdded();
        Discount discount=data.getDiscounts(Data.NULL_PRODUCT).get(0);
        GsonBuilder builderDiscount = new GsonBuilder();
        builderDiscount.registerTypeAdapter(Discount.class, new InterfaceAdapter());
        Gson discountGson = builderDiscount.create();
        String discountToAdd=discountGson.toJson(discount,Discount.class);
        assertFalse(logicManager.addDiscount(data.getId(Data.VALID),discountToAdd,
                data.getStore(Data.VALID).getName()).getValue());
        tearDownProductAdded();
    }

    /**
     * use case 4.2.1.1 -add discount to store
     */
    @Test
    public void testAddDiscountToStoreStringWithNoSence(){
        setUpProductAdded();
        assertFalse(logicManager.addDiscount(data.getId(Data.VALID),"string",
                data.getStore(Data.VALID).getName()).getValue());
        tearDownProductAdded();
    }



    /**
     * use case 4.2.1.2 -remove discount from store
     * test not existing store
     */
    @Test
    public void testDeleteDiscountFromStoreFailStore() {
        setUpDiscountAdded();
        String userName=data.getSubscribe(Data.ADMIN).getName();
        //invalid storeName
        assertFalse(logicManager.deleteDiscountFromStore(data.getId(Data.VALID),0,userName).getValue());
    }

    /**
     * use case 4.2.1.3 -view discounts from store
     */
    @Test
    public void testViewDiscountSuccess(){
        setUpDiscountAdded();
        assertNotNull(logicManager.viewDiscounts(data.getStore(Data.VALID).getName()).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 4.2.1.3 -view discounts from store
     * test not existing store
     */
    @Test
    public void testViewDiscountNotExistingStore(){
        setUpDiscountAdded();
        assertNull(logicManager.viewDiscounts(data.getSubscribe(Data.VALID).getName()).getValue());
    }

    /**
     * use case 4.2.2.1 - update the store policy
     */
    @Test
    public void testUpdatePolicy() {
        setUpProductAdded();
        testUpdatePolicyTest();
        tearDownOpenStore();
    }

    protected void testUpdatePolicyTest(){
        PurchasePolicy policy = data.getPurchasePolicy(Data.VALID_BASKET_PURCHASE_POLICY);
        GsonBuilder builderPolicy = new GsonBuilder();
        builderPolicy.registerTypeAdapter(PurchasePolicy.class,new InterfaceAdapter());
        Gson policyGson = builderPolicy.create();
        String policyToAdd = policyGson.toJson(policy, PurchasePolicy.class);
        assertTrue(logicManager.updatePolicy(data.getId(Data.VALID),policyToAdd,
                data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * use case 4.2.2.1 - update the store policy
     * fail test with not existing store store
     */
    @Test
    @Transactional
    public void testUpdatePolicyNotFoundStore() {
        setUpProductAdded();
        PurchasePolicy policy = null;
        GsonBuilder builderPolicy = new GsonBuilder();
        builderPolicy.registerTypeAdapter(PurchasePolicy.class,new InterfaceAdapter());
        Gson policyGson = builderPolicy.create();
        String policyToAdd = policyGson.toJson(policy, PurchasePolicy.class);
        assertFalse(logicManager.updatePolicy(data.getId(Data.VALID),policyToAdd,
                data.getStore(Data.WRONG_STORE).getName()).getValue());
    }

    /**
     * use case 4.2.2.1 - update the store policy
     * fail test with null policy
     */
    @Test
    public void testUpdatePolicyNullPolicy() {
        setUpProductAdded();
        assertFalse(logicManager.updatePolicy(data.getId(Data.VALID),null,
                data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * use case 4.2.2.1 - update the store policy
     * with not valid policy
     */
    @Test
    public void testUpdatePolicyInvalidPolicy() {
        setUpProductAdded();
        PurchasePolicy policy = data.getPurchasePolicy(Data.INVALID_BASKET_PURCHASE_POLICY);
        GsonBuilder builderPolicy = new GsonBuilder();
        builderPolicy.registerTypeAdapter(PurchasePolicy.class,new InterfaceAdapter());
        Gson policyGson = builderPolicy.create();
        String policyToAdd = policyGson.toJson(policy, PurchasePolicy.class);
        assertFalse(logicManager.updatePolicy(data.getId(Data.VALID),policyToAdd,
                data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * use case 4.2.2.1 - update the store policy
     * with not valid policy
     */
    @Test
    public void testUpdatePolicyWrongPolicy() {
        setUpProductAdded();
        assertFalse(logicManager.updatePolicy(data.getId(Data.VALID),"test wrong policy",
                data.getStore(Data.VALID).getName()).getValue());
    }

    @Test
    @Transactional
    public void testViewStorePolicy(){
        setUpPurchasePolicyAdded();
        testViewStorePolicyTest();
        tearDownOpenStore();
    }

    /**
     * use case 4.2.2.2 - view the store policy
     */
    protected void testViewStorePolicyTest() {
        assertNotNull(logicManager.viewPolicy(data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * use case 4.2.2.2 - view the store policy
     * test fail - no store found
     */
    @Test
    public void testViewStorePolicyFail() {
        setUpPurchasePolicyAdded();
        assertNull(logicManager.viewPolicy(data.getStore(Data.WRONG_STORE).getName()).getValue());
    }

    /**
     * test use case 4.3 - add owner
     */
    @Test
    public void testManageOwner(){
        setUpOpenedStore();
        testManageOwnerFail();
        testManageOwnerFailAgain();
        testManageOwnerSuccess();
    }

    /**
     * part of test use case 4.3 - add owner
     */
    private void testManageOwnerFailAgain() {
        assertFalse(logicManager.manageOwner(data.getId(Data.VALID),data.getSubscribe(Data.VALID).getName(),
                data.getSubscribe(Data.VALID2).getName()).getValue());
    }

    /**
     * part of test use case 4.3 - add owner
     */
    protected void testManageOwnerSuccess() {
        assertTrue(logicManager.manageOwner(data.getId(Data.VALID),data.getStore(Data.VALID).getName(),
                data.getSubscribe(Data.VALID2).getName()).getValue());
    }

    /**
     * part of test use case 4.3 - add owner
     */
    protected void testManageOwnerFail() {
        assertFalse(logicManager.manageOwner(data.getId(Data.VALID),data.getStore(Data.VALID).getName()
                ,data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * use case 4.5 add manager
     */
    @Test
    public void testAddManagerToStore(){
        setUpOpenedStore();
        testAddManagerToStoreFailStore();
        testAddManagerToStoreFailUser();
        testAddManagerStoreSuccess();
        tearDownOpenStore();
    }

    /**
     * part of use case 4.5 add manager
     */
    protected void testAddManagerStoreSuccess() {
        assertTrue(logicManager.addManager(data.getId(Data.VALID),data.getSubscribe(Data.ADMIN).getName(),
                data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * test not existing store
     */
    private void testAddManagerToStoreFailStore() {
        String userName=data.getSubscribe(Data.ADMIN).getName();
        String storeName=data.getStore(Data.VALID).getName();
        //invalid storeName
        assertFalse(logicManager.addManager(data.getId(Data.VALID),userName,userName).getValue());
        //assertFalse(stores.get(storeName).getPermissions().containsKey(userName));
        assertFalse(daos.getStoreDao().find(storeName).getPermissions().containsKey(userName));
    }

    /**
     * test not existing user
     */
    private void testAddManagerToStoreFailUser(){
        String storeName=data.getStore(Data.VALID).getName();
        assertFalse(logicManager.addManager(data.getId(Data.VALID),storeName,storeName).getValue());
        // assertFalse(stores.get(storeName).getPermissions().containsKey(storeName));
        assertFalse(daos.getStoreDao().find(storeName).getPermissions().containsKey(storeName));
    }

    /**
     * test use case 4.6.1- add permission
     */
    @Test
    public void testAddPermission(){
        setUpManagerAdded();
        testAddPermissionFail();
        testAddPermissionSuccess();
        tearDownManagerAdded();

    }

    /**
     * part of test use case 4.6.1 - add permission
     * test fail:
     * 1. wrong user name
     * 2. wrong store name
     * 3. null list
     * 4. list with null
     */
    private void testAddPermissionFail() {
        testAddPermissionFailWrongUserName();
        testAddPermissionFailWrongStoreName();
        testAddPermissionFailWrongNullPermissions();
        testAddPermissionFailWrongPermissionListWithNull();
    }

    /**
     * part of test use case 4.6.1 - add permission
     * 1. wrong user name
     */
    private void testAddPermissionFailWrongUserName(){
        String store=data.getStore(Data.VALID).getName();
        List<PermissionType> types=data.getPermissionTypeList();
        assertFalse(logicManager.addPermissions(data.getId(Data.VALID),types,store,store).getValue());
    }

    /**
     * part of test use case 4.6.1 - add permission
     * 2. wrong store name
     */
    private void testAddPermissionFailWrongStoreName(){
        String user=data.getSubscribe(Data.ADMIN).getName();
        List<PermissionType> types=data.getPermissionTypeList();
        assertFalse(logicManager.addPermissions(data.getId(Data.VALID),types,user,user).getValue());
    }

    /**
     * part of test use case 4.6.1 - add permission
     * 3. null list
     */
    private void testAddPermissionFailWrongNullPermissions(){
        String user=data.getSubscribe(Data.ADMIN).getName();
        String store=data.getStore(Data.VALID).getName();
        assertFalse(logicManager.addPermissions(data.getId(Data.VALID),null,store,user).getValue());
    }

    /**
     * part of test use case 4.6.1 - add permission
     * 4. list with null
     */
    private void testAddPermissionFailWrongPermissionListWithNull(){
        String user=data.getSubscribe(Data.ADMIN).getName();
        String store=data.getStore(Data.VALID).getName();
        List<PermissionType> types=data.getPermissionTypeList();
        types.add(null);
        assertFalse(logicManager.addPermissions(data.getId(Data.VALID),types,store,user).getValue());
        types.remove(null);
    }

    /**
     * part of test use case 4.6.1 - add permission - success
     */
    protected void testAddPermissionSuccess() {
        assertTrue(currUser.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()).getValue());
    }

    /**
     * test use case 4.6.2 - remove permissions
     */
    @Test
    public void testRemovePermission(){
        setUpPermissionsAdded();
        testRemovePermissionFail();
        testRemovePermissionSuccess();
        tearDownPermissionAdded();
    }

    /**
     * part of test use case 4.6.2 - remove permissions
     * test fail:
     * 1. wrong user name
     * 2. wrong store name
     * 3. null list
     * 4. list with null
     */
    private void testRemovePermissionFail() {
        String user=data.getSubscribe(Data.ADMIN).getName();
        String store=data.getStore(Data.VALID).getName();
        List<PermissionType> types=data.getPermissionTypeList();
        assertFalse(logicManager.removePermissions(data.getId(Data.VALID),types,store,store).getValue());
        assertFalse(logicManager.removePermissions(data.getId(Data.VALID),types,user,user).getValue());
        assertFalse(logicManager.removePermissions(data.getId(Data.VALID),null,store,user).getValue());
        types.add(null);
        assertFalse(logicManager.removePermissions(data.getId(Data.VALID),types,store,user).getValue());
        types.remove(null);
    }

    /**
     * part of test use case 4.6.2 - remove permissions - success
     */
    protected void testRemovePermissionSuccess() {
        assertTrue(currUser.removePermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()).getValue());
    }

    /**
     * test use case 4.7 - remove manager
     */
    @Test
    public void testRemoveManager(){
        setUpManagerAddedSubManagerAdded();
        testRemoveManagerFailStore();
        testRemoveManagerFailUser();
        testRemoveManagerSuccess();
        tearDownManagerAdded();
    }

    /**
     * part of test use case 4.7 - remove manager
     */
    protected void testRemoveManagerSuccess() {
        assertTrue(logicManager.removeManager(data.getId(Data.VALID),data.getSubscribe(Data.ADMIN).getName(),data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * test not existing user
     */
    private void testRemoveManagerFailStore() {
        String storeName=data.getStore(Data.VALID).getName();
        //invalid username
        assertFalse(logicManager.removeManager(data.getId(Data.VALID),storeName,storeName).getValue());
    }

    /**
     * test not existing store
     */
    private void testRemoveManagerFailUser() {
        String userName=data.getSubscribe(Data.ADMIN).getName();
        //invalid storeName
        assertFalse(logicManager.removeManager(data.getId(Data.VALID),userName,userName).getValue());
    }

    /**
     * use case 4.9.1 -view request
     */
    @Test
    public void testStoreViewRequest(){
        setUpRequestAdded();
        //testStoreViewRequestSuccess();
        testStoreViewRequestFailNullName();
        testStoreViewRequestFailWrongStore();
        tearDownOpenStore();

    }

//    /**
//     * part of use case 4.9.1 -view request
//     */
//    private void testStoreViewRequestSuccess() {
//        Request request = data.getRequest(Data.VALID);
//        assertFalse(currUser.viewRequest(request.getStoreName()).isEmpty());
//    }
    //TODO fix this

    /**
     * part of use case 4.9.1 -view request
     */
    private void testStoreViewRequestFailNullName() {
        Request request1 = data.getRequest(Data.NULL_NAME);
        assertTrue(logicManager.viewStoreRequest(data.getId(Data.VALID), request1.getStoreName()).getValue().isEmpty());
    }

    /**
     * part of use case 4.9.1 -view request
     */
    private void testStoreViewRequestFailWrongStore() {
        Request request2 = data.getRequest(Data.WRONG_STORE);
        assertTrue(logicManager.viewStoreRequest(data.getId(Data.VALID), request2.getStoreName()).getValue().isEmpty());
    }

    /**
     * use case 4.9.2 -replay request
     */
    @Test
    public void testReplayRequest(){
        setUpRequestAdded();
        testReplayRequestSuccess();
        testReplayRequestFailWrongStore();
    }

    /**
     * part of use case 4.9.2 -replay request
     */
    private void testReplayRequestSuccess() {
        Request request = data.getRequest(Data.VALID);
        assertNotNull(currUser.replayToRequest(request.getStoreName(),request.getId(), request.getContent()).getValue());
    }

    /**
     * part of use case 4.9.2 -replay request
     */
    private void testReplayRequestFailWrongStore() {
        Request request1 = data.getRequest(Data.WRONG_STORE);
        assertNull(logicManager.replayRequest(data.getId(Data.VALID), request1.getStoreName(), request1.getId(), request1.getContent()).getValue());
    }

    /**
     * use case 6.4.1 - watch User History
     */
    @Test
    public void testWatchUserHistory(){
        setUpBoughtProductAdminState();
        testWatchUserHistoryUserNotExist();
        testWatchUserHistorySuccess();
    }

    /**
     * test user that not exist on users map
     */
    private void testWatchUserHistoryUserNotExist() {
        assertNull(logicManager.watchUserPurchasesHistory(data.getId(Data.VALID), data.getStore(Data.VALID).getName()).getValue());
    }

    /**
     * test success
     */
    protected void testWatchUserHistorySuccess() {
        assertNotNull(logicManager.watchUserPurchasesHistory(data.getId(Data.ADMIN),data.getSubscribe(Data.VALID).getName()).getValue());
    }

    /**
     * use case 4.10
     * test store that not exist on users map
     */
    @Test
    public void testWatchStoreHistoryStoreNotExist() {
        setUpBoughtProductAdminState();
        assertNull(logicManager.watchStorePurchasesHistory(data.getId(Data.ADMIN), data.getSubscribe(Data.VALID).getName()).getValue());
        tearDownOpenStore();
    }

    /**
     * use case 4.10
     * test success
     */
    @Test
    public void testWatchStoreHistorySuccess() {
        setUpBoughtProductAdminState();
        assertNotNull(logicManager.watchStorePurchasesHistory(data.getId(Data.ADMIN), data.getStore(Data.VALID).getName()).getValue());
        tearDownOpenStore();
    }

    /**
     * tests for getStoresManagedByUser
     */
    @Test
    public void testGetMyStoresFailNoStores(){
        setUpLogedInUser();
        Response<List<StoreData>> response =  logicManager.getStoresManagedByUser(data.getId(Data.VALID));
        assertNull(response.getValue());
        assertEquals(response.getReason(),OpCode.No_Stores_To_Manage);
    }

    @Test
    public void testGetMyStoreFailUserNoExits(){
        Response<List<StoreData>> response =logicManager.getStoresManagedByUser(-1);
        assertNull(response.getValue());
        assertEquals(response.getReason(),OpCode.No_Stores_To_Manage);


    }
    /**
     * tests for getPermissionsForStore
     */
    @Test
    public void testGetPermissionsForStoreFailInvalidStore(){
        setUpLogedInUser();
        Response<Set<StorePermissionType>> response=
                logicManager.getPermissionsForStore(data.getId(Data.VALID),
                        "InvalidStore");
        assertNull(response.getValue());
        assertEquals(response.getReason(),OpCode.Dont_Have_Permission);
    }

    @Test
    public void testGetPermissionsForStoreFailNotManager(){
        StoreData storeData = data.getStore(Data.VALID);
        Response<Set<StorePermissionType>> response=
                logicManager.getPermissionsForStore(data.getId(Data.VALID),
                        storeData.getName());
        assertNull(response.getValue());
        assertEquals(response.getReason(),OpCode.Dont_Have_Permission);

    }


    /**
     * tests for getManagersOfStore
     * fail not existing store in the system
     */
    @Test
    public void testGetManagersOfStoreFailStoreNotExist(){
        StoreData storeData = data.getStore(Data.VALID);
        assertNull(logicManager.getManagersOfStore(storeData.getName()).getValue());
    }

    /**
     * get all the users for the admin
     */
    @Test
    public void testGetAllUsers() {
        setUpRegisteredUser();
        Subscribe sub=data.getSubscribe(Data.ADMIN);
        logicManager.login(data.getId(Data.ADMIN),sub.getName(),sub.getPassword());
        List<String> users = logicManager.getAllUsers(data.getId(Data.ADMIN)).getValue();
        assertNotNull(users);
        assertTrue(users.contains(data.getSubscribe(Data.VALID).getName()));
    }


    @Test
    public void findStore(){
        StoreDao storeDao=new StoreDao();
        Store s=storeDao.find("hanut");
        assertTrue(true);
    }

    @Test
    public void removeSubscribe(){
        SubscribeDao subdao=new SubscribeDao();
        subdao.remove("yuv");
    }

    @Test
    public void removeStore(){
        StoreDao storeDao=new StoreDao();
        Store store=storeDao.find("hanut");
        storeDao.removeStore("hanut");
    }

    @Test
    public void addRequest(){
        RequestDao requestDao=new RequestDao();
        requestDao.addRequest(new Request("yuv","hanut","hello",0));
    }

    @Test
    public void addDiscount(){
        DiscountDao discountDao=new DiscountDao();
        discountDao.addDiscount(new RegularDiscount("shok",8));
    }

    @Test
    public void testdb() {
        assertTrue(true);
    }

    @After
    public void resetTables() {
        Subscribe other=data.getSubscribe(Data.VALID2);
        SubscribeDao subscribeDao = new SubscribeDao();
        subscribeDao.remove(other.getName());
        subscribeDao.remove(data.getSubscribe(Data.VALID).getName());
        subscribeDao.remove(data.getSubscribe(Data.ADMIN).getName());
    }

    /** ------------------------------- tear downs --------------- */

    /**
     * tear down connect
     */
    public void tearDownConnect() {
        connectedUsers = new ConcurrentHashMap<>();
        currUser = null;
    }

    /**
     * tear down for register user
     */
    public void tearDownRegisteredUser() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        SubscribeDao subscribeDao = new SubscribeDao();
        subscribeDao.remove(subscribe.getName());
        tearDownConnect();
    }

    /**
     * tear down for login
     */
    public void tearDownLogin() {
        logicManager.logout(data.getId(Data.VALID));
        tearDownRegisteredUser();
    }

    /**
     * tear down for open store
     */
    public void tearDownOpenStore() {
        stores = new ConcurrentHashMap<>();
        StoreData storeData = data.getStore(Data.VALID);
        StoreDao storeDao = new StoreDao();
        storeDao.removeStore(storeData.getName());
        tearDownLogin();
    }

    /**
     * tear down for product
     */
    public void tearDownProductAdded() {
        setUpOpenedStore();
        logicManager.addProductToStore(data.getId(Data.VALID),data.getProductData(Data.VALID));
        ProductData productData = data.getProductData(Data.VALID);
        logicManager.removeProductFromStore(data.getId(Data.VALID),productData.getStoreName(),
                productData.getProductName());
        tearDownOpenStore();
    }

    public void tearDownManagerAdded(){
        tearDownOpenStore();

    }
    public void tearDownPermissionAdded(){
        tearDownManagerAdded();
    }

}
