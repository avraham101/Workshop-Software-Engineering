package Basket;

import Data.*;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.Purchase;
import Domain.*;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * test functions of the class Basket
 */
public class BasketTestReal extends BasketTest{

    @Before
    public void setUp() {
        data = new TestData();
        Store store = data.getRealStore(Data.VALID);
        String userName = data.getSubscribe(Data.VALID).getName();
        basket = new Basket(store, userName);
    }

    /**
     * use case 2.8 - reserveCart cart
     */
    @Test
    public void testBuySuccess() {
        int price = 0;
        List<ProductData> productDataList = new LinkedList<>();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertTrue(basket.buy(paymentData, deliveryData));
        for (ProductInCart product: basket.getProducts().values()) {
            price += product.getPrice();
            Product realProduct = basket.getStore().getProduct(product.getProductName());
            productDataList.add(new ProductData(realProduct , basket.getStore().getName()));
        }
        assertTrue(deliveryData.getProducts().containsAll(productDataList));
        assertEquals(price, paymentData.getTotalPrice(),0.01);
    }

    /**
     * use case 2.8 - buy cart when not stands in the store policy
     */
    @Test
    public void testBuyNotStandsInPolicy(){
        basket.getStore().setPurchasePolicy(new BasketPurchasePolicy(0));
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertFalse(basket.buy(paymentData, deliveryData));
        assertTrue(deliveryData.getProducts().isEmpty());
        assertEquals(0,paymentData.getTotalPrice(),0.001);
    }

    /**
     * use case 2.8 - reserveCart cart
     */
    @Test
    public void testBuyBasket() {
        Purchase result = basket.savePurchase(data.getSubscribe(Data.VALID).getName());
        assertNotNull(result);
    }

}