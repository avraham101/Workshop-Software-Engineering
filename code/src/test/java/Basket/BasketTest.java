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
        basket = new Basket(store);
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
     * use case 2.8 - reserveCart cart
     */
    @Test
    public void testBuyBasket() {
        setUpAddedToBasket();
        Purchase result = basket.savePurchase(data.getSubscribe(Data.VALID).getName());
        assertNotNull(result);
    }

    /**
     * use case 2.8 cancel basket
     */
    @Test
    public void testCancelBasket() {
        List<Integer> amountInBasket = new LinkedList<>();
        List<Integer> amountInStore = new LinkedList<>();
        for (Product product : basket.getProducts().keySet()) {
            amountInBasket.add(product.getAmount());
        }
        for (Product product : basket.getProducts().keySet()) {
            Product productInStore = this.store.getProduct(product.getName());
            amountInStore.add(productInStore.getAmount());
        }
        int i = 0;
        this.basket.cancel();
        for (Product product : basket.getProducts().keySet()) {
            Product productInStore = this.store.getProduct(product.getName());
            assertEquals(productInStore.getAmount(), amountInBasket.get(i) + amountInStore.get(i));
            i += 1;
        }
    }


    /**
     * use case 2.8 buy basket
     */
    @Test
    public void testBuySuccess() {
        setUpAddedToBasket();
        int price = 0;
        List<ProductData> productDataList = new LinkedList<>();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertTrue(basket.buy(paymentData, deliveryData));
        for (Product product: basket.getProducts().keySet()) {
            price += product.getPrice();
            productDataList.add(new ProductData(product, store.getName()));
        }
        assertTrue(deliveryData.getProducts().containsAll(productDataList));
        assertEquals(price, paymentData.getTotalPrice(),0.01);
    }
}