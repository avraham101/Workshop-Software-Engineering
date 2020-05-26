package Cart;

import Data.Data;
import Data.TestData;
import DataAPI.*;
import Domain.*;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import Drivers.LogicManagerDriver;
import Persitent.CartDao;
import Persitent.Dao;
import Persitent.DaoHolders.DaoHolder;
import Persitent.StoreDao;
import Persitent.SubscribeDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CartTest {

    private Domain.Cart cart;
    private TestData data;
    private DaoHolder daoHolder;
    private LogicManagerDriver logicDriver;
    private int id;

    @Before
    public void setUp(){
        data = new TestData();
        daoHolder = new DaoHolder();
        try {
            logicDriver = new LogicManagerDriver();
        } catch (Exception e) {
            fail();
        }
        setUpStore();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        Subscribe real = daoHolder.getSubscribeDao().find(subscribe.getName());
        cart = real.getCart();
    }

    private void setUpStore() {
        //int id = data.getId(Data.VALID);
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        ProductData productData = data.getProductData(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        this.id = logicDriver.connectToSystem();
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

    @After
    public void tearDown() {
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        SubscribeDao subscribeDao = daoHolder.getSubscribeDao();
        subscribeDao.remove(subscribe.getName());
        Subscribe admin = data.getSubscribe(Data.ADMIN);
        subscribeDao.remove(admin.getName());
        StoreData storeData = data.getStore(Data.VALID);
        StoreDao storeDao = daoHolder.getStoreDao();
        storeDao.removeStore(storeData.getName());
        CartDao cartDao = daoHolder.getCartDao();
        Cart cart = cartDao.find(subscribe.getName());
        if(cart!=null)
            cartDao.remove(cart);
    }

    /**-------------------------set-ups------------------------------*/

    /**
     *  prepare product in the cart
     */
    private void setUpProductAdded() {
        StoreData storeData = data.getStore(Data.VALID);
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        ProductData productData = data.getProductData(Data.VALID);
        Product product = store.getProduct(productData.getProductName());
        cart.addProduct(store,product,product.getAmount());
    }

    /**
     * set up for But Cart
     */
    private void setUpBuy() {
        setUpProductAdded();
        cart.reserveCart();
    }

    /**
     * set up for But Cart
     */
    private void setUpSave() {
        setUpProductAdded();
        cart.reserveCart();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        cart.buy(paymentData, deliveryData);
    }

    /**-------------------------set-ups------------------------------*/

    /**
     * use case 2.7 - add product to cart
     */
    @Test
    public void testAddProduct() {
        StoreData storeData = data.getStore(Data.VALID);
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        ProductData productData = data.getProductData(Data.VALID);
        Product product = store.getProduct(productData.getProductName());
        assertTrue(cart.addProduct(store,product,product.getAmount()));
        Basket basket = cart.getBasket(storeData.getName());
        assertNotNull(basket);
        Map<String,ProductInCart> productInBasket = basket.getProducts();
        assertTrue(productInBasket.containsKey(productData.getProductName()));
        ProductInCart productInCart = productInBasket.get(productData.getProductName());
        assertEquals(product.getAmount(), productInCart.getAmount());
    }

    /**
     * use case 2.8 - reserveCart
     */
    @Test
    public void testReservedCart() {
        setUpProductAdded();
        assertTrue(cart.reserveCart());
        ProductData productData = data.getProductData(Data.VALID);
        Store store = daoHolder.getStoreDao().find(productData.getStoreName());
        Product product = store.getProduct(productData.getProductName());
        int currentAmount = product.getAmount();
        assertEquals(0, currentAmount);
    }

    /**
     * use case 2.8 - reserveCart
     */
    @Test
    public void testBuyCartSuccess() {
        setUpBuy();
        int size = 0;
        double sum =0;
        for(Basket b:cart.getBaskets().values()) {
            Map<String, ProductInCart> products = b.getProducts();
            for(ProductInCart productInCart: products.values()) {
                int amount = productInCart.getAmount();
                sum += amount * productInCart.getPrice();
                size++;
            }
        }
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertTrue(cart.buy(paymentData, deliveryData));
        assertEquals(sum,paymentData.getTotalPrice(),0.001);
        assertEquals(size,deliveryData.getProducts().size());
    }

    /**
     * 2.8 -buy cart
     * buy cart fail because one basket policy fails
     */
    @Test
    public void testBuyCartPolicyFail(){
        setUpBuy();
        cart.getBaskets().get(data.getStore(Data.VALID).getName()).getStore().setPurchasePolicy(
                new BasketPurchasePolicy(0));
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertFalse(cart.buy(paymentData,deliveryData));
        assertTrue(deliveryData.getProducts().isEmpty());
        assertEquals(paymentData.getTotalPrice(),0,0.001);
    }

    /**
     * use case 2.8 - reserved Cart Empty
     */
    @Test
    public void testReservedEmptyCart() {
        assertFalse(cart.reserveCart());
    }

    /**
     * use case 2.8 - reserveCart
     */
    @Test
    public void testCancelCart() {
        setUpProductAdded();
        int expected = amountProductInStore();
        cart.reserveCart();
        cart.cancel();
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
     * use case 2.8 - reserveCart
     */
    @Test
    public void testSavePurchases() {
        setUpSave();
        int size = cart.getBaskets().size();
        String name = data.getSubscribe(Data.VALID).getName();
        List<Purchase> list = cart.savePurchases(name);
        assertNotNull(list);
        assertEquals(size, list.size());
    }

}
