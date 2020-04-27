package Discount;

import Data.Data;
import Data.TestData;
import Domain.Discount.StoreDiscount;
import Domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class StoreDiscountTest {
    private TestData data;
    private StoreDiscount storeDiscount;
    private HashMap<Product,Integer> productAndAmounts;

    @Before
    public void setUp() {
        data=new TestData();
        productAndAmounts=data.getProductsAndAmount();
        storeDiscount=new StoreDiscount(100,10);
    }

    /**
     * test function: calculate discount
     * test: check that prices are like they should be after discount
     */
    @Test
    public void calculateDiscount() {
        storeDiscount.calculateDiscount(productAndAmounts);
        for(Product p:productAndAmounts.keySet())
            if(p.getName().equals(data.getRealProduct(Data.VALID).getName()))
                assertEquals(9,p.getPrice(),0.01);
            else//product valid2
                assertEquals(9,p.getPrice(),0.01);


    }

    /**
     * test function: check term
     * case: success
     */
    @Test
    public void checkTermSuccess() {
        assertTrue(storeDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: check term
     * case: fail
     */
    @Test
    public void checkTermFail() {
        storeDiscount=new StoreDiscount(3000,10);
        assertFalse(storeDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: is valid
     * case: success
     */
    @Test
    public void isValidSuccess() {
        storeDiscount=new StoreDiscount(3000,10);
        assertTrue(storeDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidLessThan0Amount() {
        storeDiscount=new StoreDiscount(0,10);
        assertFalse(storeDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidOver100Percantage(){
        storeDiscount=new StoreDiscount(3000,101);
        assertFalse(storeDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidLessThan0Percantage(){
        storeDiscount=new StoreDiscount(3000,0);
        assertFalse(storeDiscount.isValid());
    }
}