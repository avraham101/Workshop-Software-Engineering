package LogicManagerTests;

import Data.*;
import DataAPI.CartData;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Stubs.StoreStub;
import Stubs.SubscribeStub;
import Stubs.UserStub;
import Systems.HashSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

//class for Unit test all stubs
import static org.junit.Assert.*;

public class LogicManagerAllStubsTest {

    protected LogicManager logicManager;
    protected User currUser;
    protected HashMap<String,Subscribe> users;
    protected HashMap<String,Store> stores;
    protected TestData data;

    /**
     * Adding Stores must be in type StoreStub
     * example: stores.put(Key,new StoreStub(...))
     * Adding Users must be in type UserStub
     * example: users.put(Key, new UserStub(...))
     */


    @Before
    public void setUp() {
        currUser=new UserStub();
        init();
        //make sure we are using SubscribeStub
        Subscribe dataSubscribe = data.getSubscribe(Data.ADMIN);
        Subscribe subscribe = users.get(dataSubscribe.getName());
        users.put(subscribe.getName(), new SubscribeStub(subscribe.getName(), subscribe.getPassword()));
    }

    protected void init() {
        data=new TestData();
        users=new HashMap<>();
        stores=new HashMap<>();
        logicManager = new LogicManager(users,stores,currUser);
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        logicManager.register(subscribe.getName(),subscribe.getPassword());
    }

    @Test
    public void test() {
        testExternalSystems();
        testRegister();
        testLogin();
        testOpenStore();
        testAddProduct();
        testLogout();
        testAddProductToCart();
        testWatchCartDetails();
        testEditProductsInCart();
        testDeleteProductFromCart();
        testViewDataStores();
        testViewProductsInStore();
    }


    /**
     * test: use case 1.1 - Init System
     */
    private void testExternalSystems() {
        ProxyPayment proxyPayment = new ProxyPayment();
        assertTrue(proxyPayment.connect());
        ProxySupply proxySupply = new ProxySupply();
        assertTrue(proxySupply.connect());
        try {
            HashSystem hashSystem = new HashSystem();
            hashSystem.encrypt("testExternalSystems");
        } catch (Exception e) {
            fail();
        }
    }

    /**
     * test: use case 3.1 - Logout
     */
    protected void testLogout() {
        assertTrue(currUser.logout());
    }

    /**
     * test: use case 2.2 - Register
     */
    public void testRegister() {
        testRegisterSuccess();
        testRegisterFailWrongName();
        testRegisterFailWrongPassword();
        testRegisterFailNull();
    }

    /**
     * part of test use case 2.2 - Register
     */
    public void testRegisterSuccess() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertTrue(logicManager.register(subscribe.getName(),subscribe.getPassword()));
    }

    /**
     * part of test use case 2.2 - Register
     */
    public void testRegisterFailWrongName() {
        Subscribe subscribe = data.getSubscribe(Data.WRONG_NAME);
        assertFalse(logicManager.register(subscribe.getName(),subscribe.getPassword()));
    }

    /**
     * part of test use case 2.2 - Register
     */
    public void testRegisterFailWrongPassword() {
        Subscribe subscribe = data.getSubscribe(Data.WRONG_PASSWORD);
        assertFalse(logicManager.register(subscribe.getName(), subscribe.getName()));
    }

    /**
     * part of test use case 2.2 - Register
     */
    public void testRegisterFailNull() {
        Subscribe subscribe = data.getSubscribe(Data.NULL);
        assertFalse(logicManager.register(subscribe.getName(), subscribe.getName()));
    }

    /**
     * test use case 2.3 - Login
     */
    public void testLogin() {
        testLoginFailNull();
        testLoginFailWrongName();
        testLoginFailWrongPassword();
        testLoginSuccess();
    }

    /**
     * part of use case 2.3 - Login
     */
    private void testLoginFailNull() {
        Subscribe subscribe = data.getSubscribe(Data.NULL);
        assertFalse(logicManager.login(subscribe.getName(), subscribe.getPassword()));
    }

    /**
     * part of use case 2.3 - Login
     */
    private void testLoginFailWrongName() {
        Subscribe subscribe = data.getSubscribe(Data.WRONG_NAME);
        assertFalse(logicManager.login(subscribe.getName(), subscribe.getPassword()));
    }

    /**
     * part of use case 2.3 - Login
     */
    private void testLoginFailWrongPassword() {
        Subscribe subscribe = data.getSubscribe(Data.WRONG_PASSWORD);
        assertFalse(logicManager.login(subscribe.getName(), subscribe.getPassword()));
    }

    /**
     * part of use case 2.3 - Login
     */
    protected void testLoginSuccess() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        assertTrue(logicManager.login(subscribe.getName(),subscribe.getPassword()));
    }

    /**
     * test use case 3.2 - Open Store
     */
    protected void testOpenStore() {
        testOpenStoreFail();
        testOpenStoreSucces();
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    private void testOpenStoreFail() {
        assertFalse(logicManager.openStore(data.getStore(Data.NULL)));
        assertFalse(logicManager.openStore(data.getStore(Data.NULL_NAME)));
        assertFalse(logicManager.openStore(data.getStore(Data.NULL_PURCHASE)));
        assertFalse(logicManager.openStore(data.getStore(Data.NULL_DISCOUNT)));
    }

    /**
     * part of test use case 3.2 - Open Store
     */
    protected void testOpenStoreSucces(){
        StoreData storeData = data.getStore(Data.VALID);
        assertTrue(logicManager.openStore(storeData));
        Store store = stores.get(storeData.getName());
        Permission permission = new Permission(data.getSubscribe(Data.VALID));
        StoreStub storeStub = new StoreStub(store.getName(),store.getPurchesPolicy(),
                store.getDiscout(),permission,store.getSupplySystem(),
                store.getPaymentSystem());
        permission.setStore(storeStub);
        stores.put(storeData.getName(),storeStub);
    }


    /**
     * use case 4.1.1 -add product
     */

    //TODO: Added
    protected void testAddProduct(){
        testAddProductFail();
        testProductSuccess();
    }

    protected void testProductSuccess() {
        assertTrue(logicManager.addProductToStore(data.getProductData(Data.VALID)));
    }

    protected void testAddProductFail(){
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NULL_NAME)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.WRONG_STORE)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NULL_CATEGORY)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NULL_DISCOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NEGATIVE_AMOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NEGATIVE_PRICE)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NULL_PURCHASE)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.OVER_100_PERCENTAGE)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.WRONG_DISCOUNT)));
        assertFalse(logicManager.addProductToStore(data.getProductData(Data.NEGATIVE_PERCENTAGE)));
    }

    /**
     * use case 2.4.1 - view all stores details
     */
    protected void testViewDataStores() {
        List<StoreData> expected = new LinkedList<>();
        expected.add(data.getStore(Data.VALID));
        assertEquals(expected, logicManager.viewStores());
        assertNotEquals(null, logicManager.viewStores());
    }

    /**
     * use case 2.4.2 - view the products in some store test
     */
    protected void testViewProductsInStore() {
        List<ProductData> expected = new LinkedList<>();
        String storeName = data.getStore(Data.VALID).getName();
        assertEquals(expected, logicManager.viewProductsInStore(storeName));

        expected.add(data.getProductData(Data.NULL_CATEGORY));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.remove(data.getProductData(Data.NULL_CATEGORY));

        expected.add(data.getProductData((Data.NULL_NAME)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.add(data.getProductData((Data.NULL_NAME)));

        expected.add(data.getProductData((Data.NULL_DISCOUNT)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.add(data.getProductData((Data.NULL_DISCOUNT)));

        expected.add(data.getProductData((Data.NULL_PURCHASE)));
        assertNotEquals(expected, logicManager.viewProductsInStore(storeName));
        expected.add(data.getProductData((Data.NULL_PURCHASE)));
    }

    /**
     * use case 4.1.2 -delete product
     */
    protected void removeProductFromStore(){
        testRemoveProductSuccess();
        testRemoveProductFail();
    }

    /**
     * test remove with no exist store
     */

    private void testRemoveProductFail() {
        assertFalse(logicManager.removeProductFromStore(data.getSubscribe(Data.VALID).getName()
                ,data.getProductData(Data.VALID).getProductName()));
    }

    protected void testRemoveProductSuccess() {
        ProductData p=data.getProductData(Data.VALID);
        assertTrue(logicManager.removeProductFromStore(p.getStoreName(),p.getProductName()));
    }

    /**
     * use case 2.7.1 fails tests
     */
    protected void testWatchCartDetails() {
        testWatchCartDetailsNull();
        testWatchCartDetailsNullStore();
    }

    /**
     * use case 2.7.1 fail when the product is null
     */
    private void testWatchCartDetailsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        CartData cartData = logicManager.watchCartDetatils();
        assertFalse(cartData.getProducts().contains(productData));
    }

    /**
     * use case 2.7.1 fail when the basket is null
     */
    private void testWatchCartDetailsNullStore() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        CartData cartData = logicManager.watchCartDetatils();
        assertFalse(cartData.getProducts().contains(productData));
    }


    /**
     * use case 2.7.2 delete product from cart
     * fails tests
     */
    protected void testDeleteProductFromCart() {
        testDeleteProductFromCartBasketIsNull();
        testDeleteProductFromCartProductIsNull();
    }

    /**
     * use case 2.7.2 delete product from cart
     * fails test - product is null
     */
    private void testDeleteProductFromCartProductIsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        assertFalse(logicManager.deleteFromCart(productData.getProductName(),productData.getStoreName()));
    }

    /**
     * use case 2.7.2 delete product from cart
     * fails test - basket is null
     */
    private void testDeleteProductFromCartBasketIsNull() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.deleteFromCart(productData.getProductName(),productData.getStoreName()));
    }

    /**
     * use case 2.7.3 fail tests
     */
    protected void testEditProductsInCart() {
        testEditProductsInCartBasketIsNull();
        testEditProductsInCartNegativeAmount();
        testEditProductsInCartProductIsNull();
    }

    /**
     * use case 2.7.3 fail test when the product is null
     */
    private void testEditProductsInCartProductIsNull() {
        ProductData productData = data.getProductData(Data.NULL_PRODUCT);
        assertFalse(logicManager.editProductInCart(productData.getProductName(),productData.getStoreName(),productData.getAmount()));
    }

    /**
     * use case 2.7.3 fail test when the amount is negative
     */
    private void testEditProductsInCartNegativeAmount() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.editProductInCart(productData.getProductName(),productData.getStoreName(),productData.getAmount()));
    }

    /**
     * use case 2.7.3 fail test when the basket is null
     */
    private void testEditProductsInCartBasketIsNull() {
        ProductData productData = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.editProductInCart(productData.getProductName(),productData.getStoreName(),productData.getAmount() + 1));
    }


    /**
     *  use case 2.7.4 - add product to cart
     */
    protected void testAddProductToCart() {
        testAddProductToCartInvalidStore();
    }

    /**
     * part of use case 2.7.4 - add product to cart
     */
    private void testAddProductToCartInvalidStore() {
        ProductData product = data.getProductData(Data.NULL_STORE);
        assertFalse(logicManager.aadProductToCart(product.getProductName(),product.getStoreName(),product.getAmount()));
    }


    
}