package Basket;

import Data.Data;
import Data.TestData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.*;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    /**
     * use case 2.8 - buy cart
     * test if the basket is available for buying
     */
    @Override
    protected void testIfBasketAvailableToBuy() {
        super.testIfBasketAvailableToBuy();
        testIfBasketAvailableToBuyFails();
    }

    /**
     * use case 2.8 - buy cart
     * test if the basket is available for buying
     * fail test
     */
    private void testIfBasketAvailableToBuyFails() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.EMPTY_ADDRESS).getAddress();
        Product product = data.getRealProduct(Data.VALID);
        basket.addProduct(product,product.getAmount()*2);
        assertFalse(basket.available(paymentData, address));
        basket.addProduct(product,product.getAmount());
    }

}