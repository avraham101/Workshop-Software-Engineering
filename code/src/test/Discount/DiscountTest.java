package Discount;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DiscountTest {

    private TestData testData;
    private DiscountPolicy discountPolicy;

    @Before
    public void setUp(){
        discountPolicy = new DiscountPolicy();
        testData = new TestData();
    }

    /**
     * main test class for guest
     */
    @Test
    public void testPolicy() {
        HashMap<ProductData, Integer> hash = testData.getProductsInBasket(Data.VALID);
        HashMap<Product, Integer> map = new HashMap<>();
        double expected = 0;
        for(ProductData p: hash.keySet()) {
            expected +=  p.getPrice() * hash.get(p);
            map.put(new Product(p, new Category(p.getCategory())), hash.get(p));
        }
        assertEquals(expected, discountPolicy.stands(map),0.001);
    }

}
