package Guest;

import Data.Data;
import Data.TestData;
import DataAPI.*;
import Domain.*;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import Drivers.LogicManagerDriver;
import Persitent.Cache;
import Persitent.DaoHolders.DaoHolder;
import Persitent.DaoInterfaces.ICartDao;
import Persitent.DaoInterfaces.IStoreDao;
import Persitent.DaoInterfaces.ISubscribeDao;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class GuestTestReal extends GuestTest{

    private int id;
    private DaoHolder daoHolder;
    private LogicManagerDriver logicDriver;

    @Override
    @Before
    public void setUp(){
        data = new TestData();
        cart = new Cart("Guest");
        guest = new Domain.Guest(cart);
        daoHolder = new DaoHolder();
        try {
            logicDriver = new LogicManagerDriver();
        } catch ( Exception e){
            fail();
        }
        this.id = logicDriver.connectToSystem();
        Cache cache = new Cache();
        User current = cache.findUser(this.id);
        this.cart = current.getState().getCart();
    }

    /**
     * add valid product to the cart
     */
    private void setUpProductAddedToCart(){
        Store store = data.getRealStore(Data.VALID);
        Product product = data.getRealProduct(Data.VALID);
        guest.addProductToCart(store,product,product.getAmount());
    }

    private void setUpStore() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        ProductData productData = data.getProductData(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        int id = logicDriver.connectToSystem();
        Response<Boolean> response = logicDriver.register(subscribe.getName(), subscribe.getPassword());
        if(response.getValue()) {
            response = logicDriver.login(id, subscribe.getName(), subscribe.getPassword());
            if(response.getValue()) {
                response = logicDriver.openStore(id, storeData);
                if(response.getValue()) {
                    response = logicDriver.addProductToStore(id, productData);
                }
                else
                    fail();
            }
            else
                fail();
        }
        else
            fail();
    }

    private void setUpCart() {
        setUpStore();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        ProductData productData = data.getProductData(Data.VALID);
        Product product = store.getProduct(productData.getProductName());
        guest.addProductToCart(store,product,product.getAmount());
    }

    private void setUpReserveCart() {
        setUpCart();
        guest.reserveCart();
    }

    private void tearDownStore() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        ISubscribeDao subscribeDao = daoHolder.getSubscribeDao();
        subscribeDao.remove(subscribe.getName());
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        subscribeDao.remove(admin.getName());
        StoreData storeData = data.getStore(Data.VALID);
        IStoreDao storeDao = daoHolder.getStoreDao();
        storeDao.removeStore(storeData.getName());
        ICartDao cartDao = daoHolder.getCartDao();
        Cart cart = cartDao.find(subscribe.getName());
        if(cart!=null)
            cartDao.remove(cart);
    }

    /**
     * use case 2.7 add to cart
     */
    @Override @Test
    public void testAddProductToCart() {
        setUpStore();
        StoreData storeData = data.getStore(Data.VALID);
        Store store =  daoHolder.getStoreDao().find(storeData.getName());
        ProductData productData = data.getProductData(Data.VALID);
        Product product = store.getProduct(productData.getProductName());

        Cache cache = new Cache();
        User geust = cache.findUser(id);
        assertTrue(geust.addProductToCart(store,product,product.getAmount()));

        Cart cart = geust.getState().getCart();
        Basket basket =  cart.getBaskets().get(store.getName());
        assertNotNull(basket);
        Map<String,ProductInCart> map = basket.getProducts();
        assertTrue(map.containsKey(product.getName()));
        ProductInCart productInCart = map.get(product.getName());
        assertEquals(product.getAmount(), productInCart.getAmount());

        tearDownStore();
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Override @Test
    public void testReservedCart() {
        setUpCart();
        StoreData storeData = data.getStore(Data.VALID);
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        ProductData productData = data.getProductData(Data.VALID);
        Product product = store.getProduct(productData.getProductName());
        int prev_amount =  product.getAmount();
        assertTrue(guest.reserveCart());
        store = daoHolder.getStoreDao().find(storeData.getName());
        product = store.getProduct(productData.getProductName());
        int cur_amont =  product.getAmount();
        ProductInCart inCart = guest.getCart().getBasket(store.getName()).getProducts().get(product.getName());
        int in_cart = inCart.getAmount();
        assertEquals(prev_amount, cur_amont+in_cart);
        tearDownStore();
    }

    /**
     * use case - 2.8 reserveCart cart
     */
    @Test
    public void testCancelCart() {
        setUpCart();
        int expected = amountProductInStore();
        guest.reserveCart();
        guest.cancelCart();
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
        setUpReserveCart();
        guest.savePurchase(guest.getName());
        StoreData storeData = data.getStore(Data.VALID);
        Store realStore = daoHolder.getStoreDao().find(storeData.getName());
        assertEquals(0, realStore.getPurchases().size());
        assertTrue(guest.getCart().getBaskets().isEmpty());
        tearDownStore();
    }

    /**
     * use case - 2.8 buy cart
     */
    @Test
    @Override
    public void testBuyCart() {
        setUpReserveCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        guest.getCart().getBaskets().get(data.getStore(Data.VALID).getName()).getStore().setPurchasePolicy(
                new BasketPurchasePolicy(0));
        assertTrue(guest.buyCart(paymentData,deliveryData));
        tearDownStore();
    }


    @Test
    public void testGetMyManagedStoresNoStoresSuccess() {
        assertNull(guest.getMyManagedStores());
    }

    @Test
    public void testGetPermissionsForStoreSuccess() {
        assertNull(guest.getPermissionsForStore(data.getStore(Data.VALID).getName()));
    }

    /**
     * test use case 4.5
     */
    @Test
    @Override
    public void testAddManager() {
        setUpStore();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        assertFalse(guest.addManager(subscribe, storeData.getName()).getValue());
        tearDownStore();
    }


}
