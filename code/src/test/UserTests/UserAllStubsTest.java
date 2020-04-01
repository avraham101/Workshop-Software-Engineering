package UserTests;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Data.TestData;
import Data.Data;
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
        testAddProductToStoreSubscribe();
        //last test on list
        testLogoutSubscribe();
    }

    /**
     * login is last test of these sequence
     */
    protected void testGuest() {
        testLogoutGuest();
        testOpenStoreGuest();
        testAddProductToStoreGuest();
        //last guest test
        testLoginGuest();
    }
    /**
     * test use case 2.3 - Login
     */
    protected void testLoginGuest() {
        assertTrue(user.login(new Subscribe("niv","shirazi")));
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
     * test: use case 4.9.1 -add product to store
     * guest can't add product
     */
    protected void testAddProductToStoreGuest(){
        assertFalse(user.addProductToStore(testData.getProduct(Data.VALID)));
    }

    /**
     *test: use case 4.9.1 - add product to store in subscribe state
     */
    protected void testAddProductToStoreSubscribe(){
        assertTrue(user.addProductToStore(testData.getProduct(Data.VALID)));
    }

    protected class GuestStub extends Guest {

        @Override
        public boolean login(User user, Subscribe subscribe) {
            return true;
        }

        @Override
        public boolean logout(User user){
            return false;
        }

        @Override
        public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
            return null;
        }

        @Override
        public boolean addProductToStore(ProductData productData){
            return false;
        }
    }

    protected class SubscribeStub extends Subscribe {

        public SubscribeStub(String userName, String password) {
            super(userName, password);
        }

        @Override
        public boolean login(User user, Subscribe subscribe) {
            return false;
        }

        @Override
        public boolean logout(User user){
            return true;
        }

        @Override
        public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
            return new Store(storeDetails.getName(), new PurchesPolicy(), new DiscountPolicy(),
                    new Permission(this), supplySystem, paymentSystem);
        }

        @Override
        public boolean addProductToStore(ProductData productData){
            return true;
        }
    }
}
