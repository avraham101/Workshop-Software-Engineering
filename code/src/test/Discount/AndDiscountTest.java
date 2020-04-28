package Discount;

import Data.Data;
import Data.TestData;
import Domain.Discount.AndDiscount;
import Domain.Discount.Discount;
import Domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class AndDiscountTest {

    private TestData data;
    private AndDiscount andDiscount;
    private HashMap<Product,Integer> productAndAmounts;
    private List<Discount> discounts;

    @Before
    public void setUp() {
        data=new TestData();
        productAndAmounts=data.getProductsAndAmount();
        discounts=data.getDiscounts(Data.VALID);
        andDiscount=new AndDiscount(discounts);
    }

    /**
     * test function: calculate discount
     * case: success all the discount happened
     */
    @Test
    public void calculateDiscountTest() {
        andDiscount.calculateDiscount(productAndAmounts);
        for(Product p:productAndAmounts.keySet())
            assertEquals(9,p.getPrice(),0.01);
    }

    /**
     * test function: check term
     * case: fail one of the discount didn't happen
     */
    @Test
    public void checkTermFail() {
        discounts.remove(0);
        discounts.addAll(data.getDiscounts(Data.NOT_STANDS_IN_TERM));
        assertFalse(andDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: check term
     * case: success all of the discount conditions happen
     */
    @Test
    public void checkTermSuccess() {
        assertTrue(andDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: is valid
     * case: success
     */
    @Test
    public void isValidSuccess() {
        assertTrue(andDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidHasNotValidDiscount() {
        discounts.addAll(data.getDiscounts(Data.NULL_PRODUCT));
        assertFalse(andDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidHasNullDiscount() {
        andDiscount=new AndDiscount(null);
        assertFalse(andDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidEmptyDiscountList() {
        discounts.clear();
        assertFalse(andDiscount.isValid());
    }
}