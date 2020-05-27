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
import Persitent.SubscribeDao;
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
     * use case 4.7 - remove manager
     * make user admin manage user niv(VALID2)
     * remove Admin from being manager and check that niv was removed from being a manager recursively
     */
    @Override @Test
    public void testRemoveManagerStoreSuccess() {
        setUpManagerAdded();
        Subscribe subscribe = data.getSubscribe(Data.ADMIN);
        StoreData storeData = data.getStore(Data.VALID);
        assertTrue(this.subscribe.removeManager(subscribe, storeData.getName()).getValue());

        Store store = daoHolder.getStoreDao().find(storeData.getName());
        assertEquals(1,store.getPermissions().values().size());

        subscribe = daoHolder.getSubscribeDao().find(subscribe.getName());
        Permission permission = subscribe.getPermissions().get(storeData.getName());
        assertNull(permission);
        tearDownStore();
    }

    /**
     * tests for getStatus
     */
    @Test
    public void testGetStatusRegularSuccess() {
        setUpLoginSubscribe();
        assertEquals(StatusTypeData.REGULAR, this.subscribe.getStatus());
    }

    @Test
    public void testGetStatusManagerSuccess(){
        setUpStoreOpened();
        assertEquals(StatusTypeData.MANAGER,this.subscribe.getStatus());
        tearDownStore();
    }

    /**
     * tests for getMyManagedStores
     */
    @Test
    public void testGetMyManagedStoresStoresSuccess(){
        setUpStoreOpened();
        List<Store> stores = this.subscribe.getMyManagedStores();
        assertNotNull(stores);
        StoreData storeData = data.getStore(Data.VALID);
        boolean output = false;
        for(Store s: stores) {
            if(s.getName().equals(storeData.getName())) {
                output = true;
                break;
            }
        }
        assertTrue(output);
        tearDownStore();
    }

    @Test
    public void testGetMyManagedStoresNoStoresSuccess(){
        setUpLoginSubscribe();
        List<Store> stores = this.subscribe.getMyManagedStores();
        assertNull(stores);
    }

    /**
     * test for getPermissionsForStore
     */
    @Test
    public void testGetPermissionsForStoreSuccessOwner(){
        setUpStoreOpened();
        Collection<Permission> permissions = this.subscribe.getPermissions().values();
        Permission p = permissions.iterator().next();
        assertTrue(p.getPermissionType().contains(PermissionType.OWNER));
        tearDownStore();
    }

    @Test
    public void testGetPermissionsForStoreSuccessManagerNoPermissions(){
        setUpStoreOpened();
        StoreData storeData = data.getStore(Data.VALID);
        Subscribe sub = data.getSubscribe(Data.VALID2);
        logicManagerDriver.register(sub.getName(), sub.getPassword());

        logicManagerDriver.addManager(0,sub.getName(),storeData.getName());

        int newId =  logicManagerDriver.connectToSystem();
        logicManagerDriver.login(newId, sub.getName(), sub.getPassword());
        Set <StorePermissionType> permissionTypes=
                sub.getPermissionsForStore(data.getStore(Data.VALID).getName());
        assertNotNull(permissionTypes);
        assertTrue(permissionTypes.isEmpty());


        daoHolder.getSubscribeDao().remove(sub.getName());
        tearDownStore();
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
    public void testGetPermissionsForStoreFailStoreNotExist() {
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
