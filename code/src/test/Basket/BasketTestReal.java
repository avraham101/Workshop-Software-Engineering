package Basket;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.*;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
        basket = new Basket(store);
    }

    /**------------------------------set-ups------------*/
    /**
     * set up for checking buying product
     */
    private void setUpForBuy(){
        setUpAddedToBasket();
    }

    /**------------------------------set-ups------------*/


    /**
     * use case 2.8 - reserveCart cart
     */
    @Override @Test
    public void testBuySuccess() {
        setUpForBuy();
        int price = 0;
        List<ProductData> productDataList = new LinkedList<>();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertTrue(basket.buy(paymentData, deliveryData));
        for (Product product: basket.getProducts().keySet()) {
            price += product.getPrice();
            productDataList.add(new ProductData(product, basket.getStore().getName()));
        }
        assertTrue(deliveryData.getProducts().containsAll(productDataList));
        assertEquals(price, paymentData.getTotalPrice(),0.01);
    }

    /**
     * use case 2.8 - buy cart when not stands in the store policy
     */
    @Test
    public void testBuyNotStandsInPolicy(){
        setUpForBuy();
        basket.getStore().setPurchasePolicy(new BasketPurchasePolicy(0));
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID2);
        assertFalse(basket.buy(paymentData, deliveryData));
        assertTrue(deliveryData.getProducts().isEmpty());
        assertEquals(0,paymentData.getTotalPrice(),0.001);
    }



}