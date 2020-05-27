package Subscribe;

import Data.Data;
import Data.TestData;
import DataAPI.*;
import Domain.*;
import Domain.Discount.Discount;
import Domain.Discount.RegularDiscount;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import Domain.PurchasePolicy.PurchasePolicy;
import Drivers.LogicManagerDriver;
import Utils.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class SubscribeRealTest extends SubscribeAllStubsTest {

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
    @Override @Test
    public void testReservedCart() {
        setUpReserved();
        assertTrue(this.subscribe.reserveCart());
        tearDownStore();
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Test
    public void testCancelCart() {
        setUpReserved();
        int expected = amountProductInStore();
        this.subscribe.reserveCart();
        this.subscribe.cancelCart();
        int result = amountProductInStore();
        assertEquals(expected,result);
        tearDownStore();
    }

    /**
     * use case 2.8
     * help function for getting the amount
     * @return
     */
    private int amountProductInStore() {
        StoreData storeData = data.getStore(Data.VALID);
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertNotNull(store);
        ProductData productData = data.getProductData(Data.VALID);
        Product product = store.getProduct(productData.getProductName());
        assertNotNull(product);
        return product.getAmount();
    }

    /**
     * use case 2.8 - buy cart save test
     */
    @Test
    public void testSavePurchase() {
        setUpBuy();
        int expected = this.subscribe.getPurchases().size() +1;
        this.subscribe.savePurchase(this.subscribe.getName());
        int result = this.subscribe.getPurchases().size();
        assertEquals(expected, result);
        tearDownStore();
    }

    /**
     * use case - 2.8 buy cart
     */
    @Test
    public void testBuyCartFailPolicy(){
        setUpReserved();
        BasketPurchasePolicy policy = new BasketPurchasePolicy(0);
        GsonBuilder builderPolicy = new GsonBuilder();
        builderPolicy.registerTypeAdapter(PurchasePolicy.class,new InterfaceAdapter());
        Gson policyGson = builderPolicy.create();
        String policyToAdd = policyGson.toJson(policy, PurchasePolicy.class);
        StoreData storeData = data.getStore(Data.VALID);
        logicManagerDriver.updatePolicy(0, policyToAdd, storeData.getName());
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertFalse(this.subscribe.buyCart(paymentData,deliveryData));
        assertEquals(0,paymentData.getTotalPrice(),0.001);
        assertTrue(deliveryData.getProducts().isEmpty());
        tearDownStore();
    }

    /**
     * test: use case 3.1 - Logout
     */
    @Test
    public void logoutTest(){
        setUpLoginSubscribe();
        User user=new User();
        assertTrue(this.subscribe.logout(user));
        assertTrue(user.getState() instanceof Guest);
    }

    /**
     * use case 3.2 - Open Store
     */
    @Override @Test
    public void openStoreTest() {
        setUpLoginSubscribe();
        StoreData storeData = data.getStore(Data.VALID);
        this.subscribe.openStore(storeData);

        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertEquals(storeData.getName(), store.getName());
        assertEquals(storeData.getDescription(),store.getDescription());
        Collection<Permission> permissionList = this.subscribe.getPermissions().values();
        assertNotNull(permissionList);
        assertEquals(1, permissionList.size());
        Permission p = permissionList.iterator().next();
        assertEquals(this.subscribe.getName(), p.getOwner().getName());
        assertEquals(storeData.getName(), p.getStore().getName());
        assertTrue(p.getPermissionType().contains(PermissionType.OWNER));
        tearDownStore();
    }

    /**
     * test use case 3.7 - watch purchases
     */
    @Test
    public void testWatchPurchases() {
        setUpProductBought();
        List<Purchase> list = this.subscribe.watchMyPurchaseHistory().getValue();
        assertNotNull(list);
        assertEquals(1,list.size());
        Purchase purchase = list.get(0);
        StoreData storeData = data.getStore(Data.VALID);
        assertEquals(storeData.getName() ,purchase.getStoreName());
        assertEquals(this.subscribe.getName(),purchase.getBuyer());
        tearDownStore();
    }


    /**
     * use case 4.2.1.1 -add product to store
     * check that the product added
     */
    @Test @Override
    public void testAddDiscountToStoreSuccess(){
        setUpProductAdded();
        StoreData storeData = data.getStore(Data.VALID);
        List <Discount> discounts = data.getDiscounts(Data.VALID);
        assertTrue(this.subscribe.addDiscountToStore(storeData.getName(), discounts.get(0)).getValue());

        Store store = daoHolder.getStoreDao().find(storeData.getName());
        Discount real = store.getDiscount().values().iterator().next();
        assertTrue(real instanceof RegularDiscount);
        tearDownStore();
    }

    /**
     * use case 4.2.1.2 -remove product from store
     */
    @Test
    public void testRemoveDiscountFromStoreSuccess(){
        super.testRemoveDiscountFromStoreSuccess();
        Store store=sub.getPermissions().get(data.getStore(Data.VALID).getName()).getStore();
        assertTrue(store.getDiscount().isEmpty());
    }

    /**
     * use case 4.5 - add manager
     * check also if the permission was added
     */

    @Override
    @Test
    public void testAddManagerStoreSuccess() {
        //TODO
        super.testAddManagerStoreSuccess();
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
     * check use case 4.6.1 - add permission
     */
    @Test
    public void testAddPermissions() {
        //TODO
        testAddPermissionTwiceFail();
    }

    /**
     * part of test use case 4.6.1 - add permission
     */
    private void testAddPermissionTwiceFail() {
        assertFalse(sub.addPermissions(data.getPermissionTypeList(),
                data.getStore(Data.VALID).getName(),data.getSubscribe(Data.VALID).getName()).getValue());
    }

    /**
     * part of test use case 4.6.1 - add permissions to manager
     * test the permission was really added
     */
    @Override
    @Test
    public void testAddPermissionSuccess() {
        //TODO
        super.testAddPermissionSuccess();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType().
                containsAll(data.getPermissionTypeList()));
    }

    /**
     * check use case 4.6.2 - remove permissions
     */
    @Override
    @Test
    public void testRemovePermissionSuccess() {
        //TODO
        super.testRemovePermissionSuccess();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType().
                isEmpty());
    }

    /**
     * use case 4.7 - remove manager
     * make user admin manage user niv(VALID2)
     * remove Admin from being manager and check that niv was removed from being a manager recursively
     */
    @Override @Test
    public void testRemoveManagerStoreSuccess() {
        setUpManagerAdded();
        Permission p=sub.getGivenByMePermissions().get(0);
        Subscribe niv=data.getSubscribe(Data.VALID2);
        String storeName=p.getStore().getName();
        //add another manager
        p.getOwner().addManager(niv,storeName);
        assertTrue(sub.removeManager(data.getSubscribe(Data.ADMIN),data.getStore(Data.VALID).getName()).getValue());
        assertFalse(niv.getPermissions().containsKey(storeName));
        assertFalse(p.getOwner().getPermissions().containsKey(storeName));

    }

    /**
     * tests for getStatus
     */
    @Test
    public void  testGetStatusRegularSuccess(){
        assertEquals(StatusTypeData.REGULAR,sub.getStatus());
    }

    @Test
    public void testGetStatusManagerSuccess(){
        sub.openStore(data.getStore(Data.VALID));
        assertEquals(StatusTypeData.MANAGER,sub.getStatus());
    }

    /**
     * tests for getMyManagedStores
     */

    @Test
    public void testGetMyManagedStoresNoStoresSuccess(){
        assertNull(sub.getMyManagedStores());
    }

    @Test
    public void testGetMyManagedStoresHasStoresSuccess(){
        StoreData myStore = data.getStore(Data.VALID);
        sub.openStore(data.getStore(Data.VALID));
        List<Store> managedStores = sub.getMyManagedStores();
        assertNotNull(managedStores);
        assertTrue(!managedStores.isEmpty());
        Store managedStore = managedStores.get(0);
        StoreData managedStoreData = new StoreData(managedStore.getName(),managedStore.getDescription());
        assertEquals(myStore,managedStoreData);

    }

    /**
     * test for getPermissionsForStore
     */
    @Test
    public void testGetPermissionsForStoreSuccessOwner(){
        sub.openStore(data.getStore(Data.VALID));
       Set <StorePermissionType> permissionTypes=
               sub.getPermissionsForStore(data.getStore(Data.VALID).getName());
       assertNotNull(permissionTypes);
       assertTrue(permissionTypes.contains(StorePermissionType.OWNER));

    }

    @Test
    public void testGetPermissionsForStoreSuccessManagerNoPermissions(){
        Subscribe niv=data.getSubscribe(Data.VALID2);
        niv.openStore(data.getStore(Data.VALID));
        niv.addManager(sub,data.getStore(Data.VALID).getName());
        Set <StorePermissionType> permissionTypes=
                sub.getPermissionsForStore(data.getStore(Data.VALID).getName());
        assertNotNull(permissionTypes);
        assertTrue(permissionTypes.isEmpty());

    }

    @Test
    public void testGetPermissionsForSuccessManagerWithPermissions(){
        testGetPermissionsForStoreSuccessManagerNoPermissions();
        StoreData store = data.getStore(Data.VALID);
        Subscribe niv=data.getSubscribe(Data.VALID2);
        List<PermissionType> permissions = new ArrayList<>();
        permissions.add(PermissionType.ADD_MANAGER);
        permissions.add(PermissionType.PRODUCTS_INVENTORY);
        niv.addPermissions(permissions,store.getName(),sub.getName());
        Set<StorePermissionType> expected = new HashSet<>();
        expected.add(StorePermissionType.ADD_MANAGER);
        expected.add(StorePermissionType.PRODUCTS_INVENTORY);
        assertEquals(expected,sub.getPermissionsForStore(store.getName()));
    }

    @Test
    public void testGetPermissionsForStoreFailNoPermissions(){
        assertNull( sub.getPermissionsForStore(data.getStore(Data.VALID).getName()));

    }
    @Test
    public void testGetPermissionsForStoreFailStoreNotExist(){
        assertNull( sub.getPermissionsForStore("InvalidStore"));

    }

    /**
     * getManagersOfStoreUserManaged tests
     */
    @Test
    public void getManagersOfStoreUserManagedSuccess(){
        setUpManagerAdded();
        assertTrue(sub.getManagersOfStoreUserManaged(data.getStore(Data.VALID).getName()).getValue().
                contains(data.getSubscribe(Data.ADMIN).getName()));
    }

}
