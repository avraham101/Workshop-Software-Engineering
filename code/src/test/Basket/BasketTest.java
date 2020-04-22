package Basket;

import Data.Data;
import Data.TestData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Stubs.StoreStub;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.*;

/**
 * test functions of the class Basket
 */
public class BasketTest {

    protected TestData data;
    protected Basket basket;
    protected Store store;

    @Before
    public void setUp() {
        data = new TestData();
        initStore();
        basket = new Basket(store);
    }

    private void initStore() {
        Permission permission = new Permission(data.getSubscribe(Data.VALID));
        StoreData storeData = data.getStore(Data.VALID);
        store = new StoreStub(storeData.getName(),storeData.getPurchasePolicy(),
                storeData.getDiscountPolicy(),permission);
        permission.setStore(store);
    }

    /**--------------set-ups----------------------*/
    protected void setUpAddedToBasket(){
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            Product product = data.getRealProduct(Data.VALID);
            basket.addProduct(product, product.getAmount());
        }
    }

    /**
     * use case 2.7.1 - add product to cart
     * test add product in a basket
     */
    @Test
    public void teatAddToBasket() {
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            Product product = data.getRealProduct(Data.VALID);
            assertTrue(basket.addProduct(product, product.getAmount()));
        }
    }

    /**
     * use case 2.7.2 - remove product from cart
     * test delete product from a basket
     */
    @Test
    public void testDeleteFromBasket() {
        setUpAddedToBasket();
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            Product product = new Product(productData,new Category(""));
            assertTrue(basket.deleteProduct(product.getName()));
        }
    }

    /**
     * use case 2.7.3 - edit product
     * test edit amount of product in a basket
     */
    @Test
    public void testEditAmountFromBasket() {
        setUpAddedToBasket();
        HashMap<ProductData, Integer> products = data.getProductsInBasket(Data.VALID);
        for (ProductData productData: products.keySet()) {
            String productName = productData.getProductName();
            assertTrue(basket.editAmount(productName,5));
        }
        assertFalse(basket.editAmount(null,5));
    }

    /**
     * use case 2.8 - reserveCart cart
     * test if the basket is available for buying
     */
    @Test
    public void testIfBasketAvailableToBuy() {
        setUpAddedToBasket();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        assertTrue(basket.available(paymentData, address));
    }

    /**
     * use case 2.8 - reserveCart cart
     */
    @Test
    public void testBuyBasket() {
        setUpAddedToBasket();
       Purchase result = basket.reservedBasket();
       assertNull(result);
    }

}