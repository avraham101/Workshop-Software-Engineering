package Discount;

import Data.Data;
import Data.TestData;
import Domain.Discount.RegularDiscount;
import Domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class RegularDiscountTest {
    private TestData data;
    private RegularDiscount reqularDiscount;
    private HashMap<Product,Integer> productAndAmounts;

    @Before
    public void setUp() {
        data=new TestData();
        productAndAmounts=data.getProductsAndAmount();
        reqularDiscount=new RegularDiscount(data.getRealProduct(Data.VALID).getName(),10);
    }

    /**
     * test function: calculate discount
     * test: check that prices are like they should be after discount
     */
    @Test
    public void calculateDiscount() {
        reqularDiscount.calculateDiscount(productAndAmounts);
        for(Product p:productAndAmounts.keySet()){
            if(p.getName().equals(data.getRealProduct(Data.VALID).getName()))
                assertEquals(9,p.getPrice(),0.01);
        }
    }

    /**
     * test function: check term
     * case: success
     */
    @Test
    public void checkTermSuccess() {
        assertTrue(reqularDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: check term
     * case: fail
     */
    @Test
    public void checkTermFail() {
        reqularDiscount=new RegularDiscount(data.getSubscribe(Data.ADMIN).getName(),10);
        assertFalse(reqularDiscount.checkTerm(productAndAmounts));
    }
}