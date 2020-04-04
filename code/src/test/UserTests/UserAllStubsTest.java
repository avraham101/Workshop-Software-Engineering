package UserTests;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Data.TestData;
import Data.Data;
import Stubs.AdminStub;
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
    protected TestData data;


    @Before
    public void setUp() {
        initGuest();
    }

    protected void initGuest() {
        userState = new GuestStub();
        user = new User(userState);
        data =new TestData();
    }

    protected void initSubscribe() {
        String userName = null;
        String password = null;
        userState = new SubscribeStub(userName, password);
        user = new User(userState);
    }

    protected void initAdmin(){
        Subscribe s=data.getSubscribe(Data.ADMIN);
        userState = new AdminStub(s.getName(), s.getPassword());
        user = new User(userState);
    }

    @Test
    public void test() {
        testGuest();
        testSubscribe();
        testAdmin();
    }

    private void testAdmin() {
        initAdmin();
        testCanWatchUserHistoryAdmin();
        testCanWatchStoreHistoryAdmin();
    }

    private void testSubscribe() {
        initSubscribe();
        testLoginSubscribe();
        testOpenStoreSubscribe();
        testAddManagerSubscribe();
        testCanWatchStoreHistorySubscribe();
        testCanWatchUserHistorySubscribe();
        testAddPermissionsSubscribe();
        testRemovePermissionsSubscibe();
        testAddProductToStoreSubscribe();
        testEditProductFromStoreSubscribe();
        testRemoveProductFromStoreSubscribe();
        testRemoveManagerSubscribe();
        //last test on list
        testLogoutSubscribe();
    }

    /**
     * login is last test of these sequence
     */
    protected void testGuest() {
        testLogoutGuest();
        testOpenStoreGuest();
        testCanWatchUserHistoryGuest();
        testCanWatchStoreHistoryGuest();
        testAddManagerGuest();
        testRemoveManagerGuest();
        testAddProductToStoreGuest();
        testEditProductFromStoreGuest();
        testRemoveProductFromStoreGuest();
        testAddPermissionsGuest();
        testRemovePermissionsGuest();
        //last guest test
        testLoginGuest();
    }
    /**
     * test use case 2.3 - Login
     */
    protected void testLoginGuest() {
        assertTrue(user.login(data.getSubscribe(Data.VALID)));
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
        assertFalse(user.addProductToStore(data.getProductData(Data.VALID)));
    }

    /**
     *test: use case 4.1.1 - add product to store in subscribe state
     */
    protected void testAddProductToStoreSubscribe(){
        assertTrue(user.addProductToStore(data.getProductData(Data.VALID)));
    }

    /**
     * test : use case 4.1.2 -remove product to store
     * guest can't do it
     */
    protected void testRemoveProductFromStoreGuest(){
        ProductData product= data.getProductData(Data.VALID);
        assertFalse(user.removeProductFromStore(product.getStoreName(),product.getProductName()));
    }

    /**
     * test : use case 4.1.2 -remove product to store
     * guest can't do it
     */
    protected void testRemoveProductFromStoreSubscribe(){
        ProductData product= data.getProductData(Data.VALID);
        assertTrue(user.removeProductFromStore(product.getStoreName(),product.getProductName()));
    }

    /**
     * test use case 4.1.3 - edit product from store
     */
    protected void testEditProductFromStoreGuest(){
        assertFalse(user.editProductFromStore(data.getProductData(Data.EDIT)));
    }

    protected void testEditProductFromStoreSubscribe() {
        assertTrue(user.editProductFromStore(data.getProductData(Data.EDIT)));
    }

    /**
     * test use case 4.5 -addManager
     */

    protected void testAddManagerGuest(){
        assertFalse(user.addManager(data.getSubscribe(Data.ADMIN), data.getStore(Data.VALID).getName()));
    }

    protected void testAddManagerSubscribe(){
        assertTrue(user.addManager(data.getSubscribe(Data.ADMIN), data.getStore(Data.VALID).getName()));
    }

    /**
     * test se case 4.6.1 - add permissions
     */
    private void testAddPermissionsGuest(){
        assertFalse(user.addPermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(), data.getStore(Data.VALID).getName()));
    }

    protected void testAddPermissionsSubscribe(){
        assertTrue(user.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()));
    }

    /**
     * test use case 4.6.2 - remove permissions
     */
    private void testRemovePermissionsGuest(){
        assertFalse(user.removePermissions(data.getPermissionTypeList(),
                data.getSubscribe(Data.ADMIN).getName(), data.getStore(Data.VALID).getName()));
    }

    protected void testRemovePermissionsSubscibe(){
        assertTrue(user.removePermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.ADMIN).getName()));
    }

    /**
     * test use case 4.7 -remove manager
     */

    protected void testRemoveManagerGuest(){
        assertFalse(user.removeManager(data.getSubscribe(Data.ADMIN).getName(), data.getStore(Data.VALID).getName()));
    }

    protected void testRemoveManagerSubscribe(){
        assertTrue(user.removeManager(data.getSubscribe(Data.ADMIN).getName(), data.getStore(Data.VALID).getName()));
    }


    /**
     * use case 6.4.1 - watch user store
     */
    public void testCanWatchUserHistoryGuest(){
        assertFalse(user.canWatchUserHistory());
    }

    /**
     * use case 6.4.1 - admin watch user store
     */
    public void testCanWatchUserHistorySubscribe(){
        assertFalse(user.canWatchUserHistory());
    }

    /**
     * use case 6.4.1 - admin watch user store
     */
    public void testCanWatchUserHistoryAdmin(){
        assertTrue(user.canWatchUserHistory());
    }

    /**
     * use case 6.4.2 ,4.10- watch store
     */
    public void testCanWatchStoreHistoryGuest(){
        assertFalse(user.canWatchStoreHistory(data.getStore(Data.VALID).getName()));
    }

    /**
     * use case 6.4.2 - watch store
     */
    public void testCanWatchStoreHistorySubscribe(){
        assertTrue(user.canWatchStoreHistory(data.getStore(Data.VALID).getName()));
    }

    /**
     * use case 6.4.2 - admin watch store
     */
    public void testCanWatchStoreHistoryAdmin(){
        assertTrue(user.canWatchStoreHistory(data.getStore(Data.VALID).getName()));
    }
}
