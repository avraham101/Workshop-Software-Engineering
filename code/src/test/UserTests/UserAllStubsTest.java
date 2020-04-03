package UserTests;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Data.TestData;
import Data.Data;
import Stubs.GuestStub;
import Stubs.SubscribeStub;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import org.junit.Before;
import org.junit.Test;
//class for Unit test all stubs
import static org.junit.Assert.*;
//class for Unit test all stubs


public class UserAllStubsTest {

    protected User user;
    protected UserState userState;
    protected TestData testData;


    @Before
    public void setUp() {
        initGuest();
    }

    protected void initGuest() {
        userState = new GuestStub();
        user = new User(userState);
        testData=new TestData();
    }

    protected void initSubscribe() {
        String userName = null;
        String password = null;
        userState = new SubscribeStub(userName, password);
        user = new User(userState);
    }

    @Test
    public void test() {
        testGuest();
        testSubscribe();
    }

    private void testSubscribe() {
        initSubscribe();
        testLoginSubscribe();
        testOpenStoreSubscribe();
        testAddManagerSubscribe();
        testAddProductToStoreSubscribe();
        testEditProductFromStoreSubscribe();
        testRemoveProductFromStoreSubscribe();
        //last test on list
        testLogoutSubscribe();
    }

    /**
     * login is last test of these sequence
     */
    protected void testGuest() {
        testLogoutGuest();
        testOpenStoreGuest();
        testAddManagerGuest();
        testAddProductToStoreGuest();
        testRemoveProductFromStoreGuest();
        testEditProductFromStoreGuest();
        //last guest test
        testLoginGuest();
    }
    /**
     * test use case 2.3 - Login
     */
    protected void testLoginGuest() {
        assertTrue(user.login(testData.getSubscribe(Data.VALID)));
    }

    /**
     * test use case 2.3 - Login
     */
    protected void testLoginSubscribe() {
        assertFalse(user.login(new Subscribe("niv","shirazi")));
    }

    /**
     * test: use case 3.1 - Logout
     */
    protected void testLogoutGuest(){
        assertFalse(user.logout());
    }

    /**
     * test: use case 3.1 - Logout
     */
    protected void testLogoutSubscribe(){
        assertTrue(user.logout());
    }

    /**
     * test: use case 3.2 - Open Store
     */
    protected void testOpenStoreGuest() {
        StoreData storeData = new StoreData("Store", new PurchesPolicy(), new DiscountPolicy());
        PaymentSystem paymentSystem = new ProxyPayment();
        SupplySystem supplySystem = new ProxySupply();
        assertNull(user.openStore(storeData,paymentSystem, supplySystem));
    }

    /**
     * test: use case 3.2 - Open Store
     */
    protected void testOpenStoreSubscribe() {
        StoreData storeData = new StoreData("Store", new PurchesPolicy(), new DiscountPolicy());
        PaymentSystem paymentSystem = new ProxyPayment();
        SupplySystem supplySystem = new ProxySupply();
        Store store = user.openStore(storeData,paymentSystem, supplySystem);
        assertEquals(storeData.getName(), store.getName());
    }

    /**
     * test: use case 4.1.1 -add product to store
     * guest can't add product
     */
    protected void testAddProductToStoreGuest(){
        assertFalse(user.addProductToStore(testData.getProductData(Data.VALID)));
    }

    /**
     *test: use case 4.1.1 - add product to store in subscribe state
     */
    protected void testAddProductToStoreSubscribe(){
        assertTrue(user.addProductToStore(testData.getProductData(Data.VALID)));
    }

    /**
     * test : use case 4.1.2 -remove product to store
     * guest can't do it
     */
    protected void testRemoveProductFromStoreGuest(){
        ProductData product= testData.getProductData(Data.VALID);
        assertFalse(user.removeProductFromStore(product.getStoreName(),product.getProductName()));
    }

    /**
     * test : use case 4.1.2 -remove product to store
     * guest can't do it
     */
    protected void testRemoveProductFromStoreSubscribe(){
        ProductData product= testData.getProductData(Data.VALID);
        assertTrue(user.removeProductFromStore(product.getStoreName(),product.getProductName()));
    }

    /**
     * test use case 4.1.3 - edit product from store
     */
    protected void testEditProductFromStoreGuest(){
        assertFalse(user.editProductFromStore(testData.getProductData(Data.EDIT)));
    }

    protected void testEditProductFromStoreSubscribe() {
        assertTrue(user.editProductFromStore(testData.getProductData(Data.EDIT)));
    }

    /**
     * test use case 4.5 -addManager
     */

    protected void testAddManagerGuest(){
        assertFalse(user.addManager(testData.getSubscribe(Data.ADMIN),testData.getStore(Data.VALID).getName()));
    }

    protected void testAddManagerSubscribe(){
        assertTrue(user.addManager(testData.getSubscribe(Data.ADMIN),testData.getStore(Data.VALID).getName()));
    }

}
