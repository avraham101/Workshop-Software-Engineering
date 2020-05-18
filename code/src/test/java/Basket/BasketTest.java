package Basket;

import Data.Data;
import Data.TestData;
import DataAPI.*;
import Domain.*;
import Stubs.StoreStub;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
        String userName = data.getSubscribe(Data.VALID).getName();
        basket = new Basket(store, userName);
    }

    private void initStore() {
        Permission permission = new Permission(data.getSubscribe(Data.VALID));
        StoreData storeData = data.getStore(Data.VALID);
        store = new StoreStub(storeData.getName(),permission,"description");
        permission.setStore(store);
    }

    /**--------------set-ups----------------------*/
    protected void setUpAddedToBasket(){
        Product product = data.getRealProduct(Data.VALID);
        basket.addProduct(product, product.getAmount());
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
     * use case 2.8 cancel basket
     */
    @Test
    public void testCancelBasket() {
        List<Integer> amountInBasket = new LinkedList<>();
        List<Integer> amountInStore = new LinkedList<>();
        for (ProductInCart product : basket.getProducts().values()) {
            amountInBasket.add(product.getAmount());
        }
        for (ProductInCart product : basket.getProducts().values()) {
            Product productInStore = this.store.getProduct(product.getProductName());
            amountInStore.add(productInStore.getAmount());
        }
        int i = 0;
        this.basket.cancel();
        for (ProductInCart product : basket.getProducts().values()) {
            Product productInStore = this.store.getProduct(product.getProductName());
            assertEquals(productInStore.getAmount(), amountInBasket.get(i) + amountInStore.get(i));
            i += 1;
        }
    }

//TODO delete it

//    /**
//     * use case 2.8 buy basket
//     */
//    @Test
//    public void testBuySuccess() {
//        setUpAddedToBasket();
//        int price = 0;
//        List<ProductData> productDataList = new LinkedList<>();
//        PaymentData paymentData = data.getPaymentData(Data.VALID);
//        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
//        assertTrue(basket.buy(paymentData, deliveryData));
//        for (ProductInCart product: basket.getProducts().values()) {
//            price += product.getPrice();
//            Product realProduct = store.getProduct(product.getProductName());
//            productDataList.add(new ProductData(realProduct, store.getName()));
//        }
//        assertTrue(deliveryData.getProducts().containsAll(productDataList));
//        assertEquals(price, paymentData.getTotalPrice(),0.01);
//    }
}