package Basket;

import Data.Data;
import Data.TestData;
import DataAPI.*;
import Domain.*;
import Persitent.*;
import Persitent.DaoHolders.DaoHolder;
import Stubs.StoreStub;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * test functions of the class Basket
 */
public class BasketTest {

    protected TestData data;
    protected LogicDriver logicDriver;
    protected DaoHolder daoHolder;
    protected Basket basket;
    protected Store store;
    protected int id;

    @Before
    public void setUp() {
        data = new TestData();
        daoHolder = new DaoHolder();
        try {
            logicDriver = new LogicDriver();
        } catch (Exception e) {
            fail();
        }
        setUpStore();
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        Cache cache = new Cache();
        Subscribe real = (Subscribe)cache.findUser(this.id).getState();
        Store store = real.getPermissions().get(storeData.getName()).getStore();
        this.basket = new Basket( store, subscribe.getName());
//        real.getCart().setBasket(storeData.getName(),this.basket);
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

    protected void setUpProductAddedToBasket() {
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            Product product = new Product(productData,new Category(productData.getCategory()));
            if(!basket.addProduct(product, product.getAmount()))
                fail();
        }
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        CartDao cartDao = daoHolder.getCartDao();
        Cart cart = cartDao.find(subscribe.getName());
        if(cart==null)
            fail();
        cartDao.remove(cart);
        StoreData storeData = data.getStore(Data.VALID);
        cart.setBasket(storeData.getName(),this.basket);
        if(!cartDao.add(cart))
            fail();
    }

    @After
    public void tearDownStore() {
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

    /**
     * use case 2.7.1 - add product to cart
     * test add product in a basket
     */
    @Test
    public void teatAddToBasket() {
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            Product product = new Product(productData,new Category(productData.getCategory()));
            assertTrue(basket.addProduct(product, product.getAmount()));
        }
    }

    /**
     * use case 2.7.2 - remove product from cart
     * test delete product from a basket
     */
    @Test
    public void testDeleteFromBasket() {
        setUpProductAddedToBasket();
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            Product product = new Product(productData,new Category(productData.getCategory()));
            assertTrue(basket.deleteProduct(product.getName()));
        }
    }

    /**
     * use case 2.7.3 - edit product
     * test edit amount of product in a basket
     */
    @Test
    public void testEditAmountFromBasket() {
        setUpProductAddedToBasket();
        int new_amount = 10;
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            String productName = productData.getProductName();
            assertTrue(basket.editAmount(productName, new_amount));
        }
        Subscribe subscribe = data.getSubscribe(Data.VALID);
        StoreData storeData = data.getStore(Data.VALID);
        Cart cart =  daoHolder.getCartDao().find(subscribe.getName());
        Basket basket =  cart.getBasket(storeData.getName());
        assertNotNull(basket);
        Map<String, ProductInCart> basketProducts = basket.getProducts();
        for (ProductData productData: products.keySet()) {
            assertTrue(basketProducts.containsKey(productData.getProductName()));
            int amount = basketProducts.get(productData.getProductName()).getAmount();
            assertEquals(new_amount, amount);
        }
    }

    /**
     * use case 2.7.3 - edit product
     * test edit amount of product in a basket
     */
    @Test
    public void testEditAmountFromBasketNullProduct() {
        setUpProductAddedToBasket();
        assertFalse(basket.editAmount(null,5));
    }

    /**
     * use case 2.7.3 - edit product
     * test edit amount of product in a basket
     */
    @Test
    public void testEditAmountFromBasketWrongProductName() {
        setUpProductAddedToBasket();
        ProductData productData = data.getProductData(Data.WRONG_NAME);
        assertFalse(basket.editAmount(productData.getProductName(),productData.getAmount()));
    }

    /**
     * use case 2.8 cancel basket
     */
    @Test
    public void testCancelBasket() {
        setUpProductAddedToBasket();
        List<Integer> amountInBasket = new LinkedList<>();
        List<Integer> amountInStore = new LinkedList<>();
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (Integer amount: products.values()) {
            amountInBasket.add(amount);
        }
        StoreData storeData = data.getStore(Data.VALID);
        Store store = daoHolder.getStoreDao().find(storeData.getName());
        for (Product product : store.getProducts().values()) {
            amountInStore.add(product.getAmount());
        }
        int i = 0;
        this.basket.cancel();
        store = daoHolder.getStoreDao().find(storeData.getName());
        for (ProductData product : products.keySet()) {
            Product productInStore = store.getProduct(product.getProductName());
            assertEquals(productInStore.getAmount(), amountInBasket.get(i) + amountInStore.get(i));
            i += 1;
        }
    }
}

class LogicDriver extends LogicManager{

    public LogicDriver() throws Exception {
        super("Admin", "Admin", new ProxyPayment(), new ProxySupply(), new DaoHolder(),
                new Cache());
    }
}