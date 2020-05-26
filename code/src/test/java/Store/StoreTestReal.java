package Store;

import Data.Data;
import DataAPI.ProductData;
import DataAPI.Response;
import DataAPI.StoreData;
import Domain.Cart;
import Domain.ProductInCart;
import Domain.Store;
import Domain.Subscribe;
import Drivers.LogicManagerDriver;
import Persitent.CartDao;
import Persitent.DaoHolders.DaoHolder;
import Persitent.StoreDao;
import Persitent.SubscribeDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class StoreTestReal extends StoreTestsAllStubs {


    @Before
    public void setUp() {
        try {
            logicDriver = new LogicManagerDriver();
        }
        catch (Exception e) {
            fail();
        }
        daoHolder = new DaoHolder();
        setUpStore();
        store = daoHolder.getStoreDao().find(data.getRealStore(Data.VALID).getName());
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

    //private void setUpProduct


    /**
     * part of 2.8 - buy cart
     */
    @Test
    public void calculatePrice(){
        setUpDiscountAdded();
        HashMap<String, ProductInCart> productAmount = data.getCart(Data.VALID);
        double expected = 0;
        double discount = 1;
        for(ProductInCart productInCart: productAmount.values()) {
            expected += productInCart.getAmount() * productInCart.getPrice() - discount;
        }
        double price=store.calculatePrice(productAmount);
        assertEquals(price, expected,0.001);
    }

    /**
     * use case 4.1.1
     */
    @Override
    protected void testAddProductSuccess() {
        setUpProductAdded();
        //ProductData p = daoHolder.getProductDao().find(data.getRealProduct(Data.VALID));
        assertTrue(store.getProducts().get(p.getProductName()).equal(p));
        //tearDoenP
    }

    /**
     * test use case 4.1.2
     */
    @Override
    protected void testSuccessRemoveProduct() {
        super.testSuccessRemoveProduct();
        assertFalse(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.3
     */
    @Override
    protected void testSuccessEditProduct() {
        super.testSuccessEditProduct();
        ProductData product=data.getProductData(Data.EDIT);
        assertTrue(store.getProducts().get(product.getProductName()).equal(product));
    }

}
