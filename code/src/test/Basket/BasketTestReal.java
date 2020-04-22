package Basket;

import Data.Data;
import Data.TestData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.*;
import org.junit.Before;
import org.junit.Test;

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
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String address = data.getDeliveryData(Data.VALID).getAddress();
        basket.available(paymentData, address);
    }

    /**------------------------------set-ups------------*/

    /**
     * use case 2.8 - reserveCart cart
     * test if the basket is available for buying
     */
    @Override @Test
    public void testIfBasketAvailableToBuy() {
        super.testIfBasketAvailableToBuy();
        testIfBasketAvailableToBuyFails();
    }

    /**
     * use case 2.8 - reserveCart cart
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

    /**
     * use case 2.8 - reserveCart cart
     */
    @Override @Test
    public void testBuyBasket() {
        setUpForBuy();
        Purchase result = basket.reservedBasket();
        assertNotNull(result);
        Product product = data.getRealProduct(Data.VALID);
        assertEquals(basket.getStore().getName(), result.getStoreName());
        List<ProductData> productDataList = result.getProduct();
        assertFalse(productDataList.isEmpty());
        assertEquals(product.getName(), productDataList.get(0).getProductName());
    }



}