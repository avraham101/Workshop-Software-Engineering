package Subscribe;

import Data.*;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

public class SubscribeRealTest extends SubscribeAllStubsTest {

    @Before
    public void setUp(){
        data = new TestData();
        cart = new Cart();
        sub = new Subscribe("Yuval","Sabag", cart);
        super.initStore();
    }

    /**
     * use case 2.7 add to cart
     */
    @Override @Test
    public void testAddProductToCart() {
        super.testAddProductToCart();
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        HashMap<Product,Integer> products = cart.getBasket(store.getName()).getProducts();
        assertEquals(1,products.size());
        Iterator<Product> iterator =  products.keySet().iterator();
        Product real = iterator.next();
        assertEquals(real.getName(),product.getName());
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Override @Test
    public void testReservedCart() {
        setUpReserved();
        assertTrue(sub.reserveCart());
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Test
    public void testCancelCart() {
        setUpReserved();
        int expected = amountProductInStore();
        sub.reserveCart();
        sub.cancelCart();
        int result = amountProductInStore();
        assertEquals(expected,result);
    }

    /**
     * use case 2.8
     * help function for getting the amount
     * @return
     */
    private int amountProductInStore() {
        Store store = null;
        for(Basket b: cart.getBaskets().values()) {
            store = b.getStore();
            break;
        }
        assertNotNull(store);
        Product product = null;
        for(Product p :store.getProducts().values()) {
            product = p;
            break;
        }
        assertNotNull(product);
        return product.getAmount();
    }

    /**
     * use case 2.8 - buy cart save test
     */
    @Test
    public void testSavePurchase() {
        setUpSave();
        Store store = null;
        int storeExpected = 0;
        for(Basket basket: sub.getCart().getBaskets().values()) {
            store = basket.getStore();
            storeExpected = store.getPurchases().size() + 1;
            break;
        }
        int expected = sub.getPurchases().size() +1;
        sub.savePurchase(sub.getName());
        int result = sub.getPurchases().size();
        assertEquals(expected, result);
        assertEquals(storeExpected, store.getPurchases().size());
    }

    /**
     * test: use case 3.1 - Logout
     */
    @Override @Test
    public void logoutTest(){
        User user=new User();
        assertTrue(sub.logout(user));
        assertTrue(user.getState() instanceof Guest);
        assertEquals(sub.getSessionNumber().get(),-1);
    }

    /**
     * use case 3.2 - Open Store
     */
    @Override @Test
    public void openStoreTest() {
        StoreData storeData=data.getStore(Data.VALID);
        Store store = sub.openStore(storeData);
        assertEquals(storeData.getName(), store.getName());
        assertEquals(storeData.getDiscountPolicy(), store.getDiscount());
        assertEquals(storeData.getPurchasePolicy(), store.getPurchasePolicy());
        //test Owner permissions
        ConcurrentHashMap<String, Permission> permissions = sub.getPermissions();
        assertTrue(permissions.containsKey(store.getName()));
        Permission permission = permissions.get(store.getName());
        assertTrue(permission.getPermissionType().contains(PermissionType.OWNER));
    }

    /**
     * test use case 3.7 - watch purchases
     */
    @Test
    public void testWatchPurchases() {
        setUpProductBought();
        List<Purchase> list = sub.watchMyPurchaseHistory();
        assertNotNull(list);
        assertEquals(1,list.size());
    }

    /**
     * test use case 4.1.1 - add product to store
     * test the product was added to store
     */
    @Override @Test
    public void addProductToStoreTestSuccess() {
        super.addProductToStoreTestSuccess();
        ProductData pData=data.getProductData(Data.VALID);
        Product p= sub.getPermissions().get(data.getStore(Data.VALID).getName()).getStore().getProducts().
                get(pData.getProductName());
        assertTrue(p.equal(pData));
    }

    /**
     * use case 4.1.3 - edit product
     */
    @Override
    protected void testSuccessEditProduct() {
        super.testSuccessEditProduct();
        ProductData product=data.getProductData(Data.EDIT);
        assertTrue(sub.getPermissions().get(product.getStoreName()).getStore()
                .getProducts().get(product.getProductName()).equal(product));
    }

    /**
     * use case 4.5 - add manager
     * check also if the permission was added
     */

    @Override
    protected void testAddManagerStoreSuccess() {
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
    @Override @Test
    public void testAddPermissions() {
        super.testAddPermissions();
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
    protected void testAddPermissionSuccess() {
        super.testAddPermissionSuccess();
        assertTrue(sub.getGivenByMePermissions().get(0).getPermissionType().
                containsAll(data.getPermissionTypeList()));
    }

    /**
     * check use case 4.6.2 - remove permissions
     */
    @Override
    protected void testRemovePermissionSuccess() {
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
        assertTrue(sub.removeManager(data.getSubscribe(Data.ADMIN).getName(),data.getStore(Data.VALID).getName()));
        assertFalse(niv.getPermissions().containsKey(storeName));
        assertFalse(p.getOwner().getPermissions().containsKey(storeName));

    }

}
