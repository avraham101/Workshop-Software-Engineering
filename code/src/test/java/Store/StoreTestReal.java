package Store;

import Data.Data;
import DataAPI.ProductData;
import DataAPI.Response;
import DataAPI.StoreData;
import Data.TestData;
import Domain.*;
import Domain.Discount.Discount;
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
        data = new TestData();
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
     * use case 3.3 - add review
     */
    @Test
    public void testAddReview() {
        Review review = data.getReview(Data.VALID);
        assertTrue(store.addReview(review));
        Product product= store.getProducts().get(review.getProductName());
        assertTrue(product.getReviews().contains(review));
        review = data.getReview(Data.WRONG_PRODUCT);
        assertFalse(store.addReview(review));
    }

    /**
     * use case 4.1.1
     */
    @Test
    public void testAddProductSuccess() {
        Product p = daoHolder.getProductDao().find(data.getRealProduct(Data.VALID));
        assertEquals(store.getProducts().get(p.getName()).getName(), p.getName());
        assertEquals(store.getProducts().get(p.getName()).getStore(), p.getStore());
    }

    /**
     * test use case 4.1.2
     */
    @Override
    @Test
    public void testSuccessRemoveProduct() {
        Product product = daoHolder.getProductDao().find(data.getRealProduct(Data.VALID));
        assertTrue(daoHolder.getProductDao().removeProduct(product));
        store = daoHolder.getStoreDao().find(data.getRealStore(Data.VALID).getName());
        assertFalse(store.getProducts().containsKey(data.getProductData(Data.VALID).getProductName()));
    }

    /**
     * use case 4.1.3
     */
    @Override
    @Test
    public void testSuccessEditProduct() {
        Product product = daoHolder.getProductDao().find(data.getRealProduct(Data.EDIT));
        int oldAmount = product.getAmount();
        product.setAmount(oldAmount + 1);
        daoHolder.getProductDao().updateProduct(product);
        product = daoHolder.getProductDao().find(data.getRealProduct(Data.EDIT));
        assertEquals(oldAmount + 1, product.getAmount());
        assertEquals(store.getProducts().get(product.getName()).getName(), product.getName());
        assertEquals(store.getProducts().get(product.getName()).getStore(), product.getStore());
    }

    /**
     * test use case 4.2.1.1 -add discount to store
     * success
     */
    @Test
    public void testAddDiscountToStoreSuccess(){
        Discount d = data.getDiscounts(Data.VALID).get(0);
        assertTrue(store.addDiscount(d).getValue());
        store = daoHolder.getStoreDao().find(store.getName());
        assertEquals(store.getDiscount().get(0),d);
    }


    /**
     * test use case 4.2.1.1 -add discount to store
     */
    @Test
    public void testAddDiscountFailProductNotInStore(){
        Product productToRemove = data.getRealProduct(Data.VALID);
        Product productDB = daoHolder.getProductDao().find(productToRemove);
        assertTrue(daoHolder.getProductDao().removeProduct(productDB));
        store = daoHolder.getStoreDao().find(store.getName());
        assertFalse(store.addDiscount(data.getDiscounts(Data.VALID).get(0)).getValue());
        assertTrue(store.getDiscount().isEmpty());
    }

    /**
     * test use case 4.2.1.2 - delete discount from store
     */
    @Test
    public void testDeleteProductFromStoreDiscountNotExistInStore(){
        assertFalse(store.deleteDiscount(0).getValue());
    }



}
