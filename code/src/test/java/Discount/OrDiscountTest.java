package Discount;

import Data.Data;
import Data.TestData;
import Domain.Discount.AndDiscount;
import Domain.Discount.Discount;
import Domain.Discount.OrDiscount;
import Domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class OrDiscountTest {

    private TestData data;
    private OrDiscount orDiscount;
    private HashMap<Product,Integer> productAndAmounts;
    private List<Discount> discounts;

    @Before
    public void setUp() {
        data=new TestData();
        productAndAmounts=data.getProductsAndAmount(Data.VALID);
        discounts=data.getDiscounts(Data.VALID);
        orDiscount=new OrDiscount(discounts);
    }

    /**
     * test function: calculate discount
     * case: success all the discount happened
     */
    @Test
    public void calculateDiscountTest() {
        orDiscount.calculateDiscount(productAndAmounts);
        for(Product p:productAndAmounts.keySet())
            assertEquals(9,p.getPrice(),0.01);
    }

    /**
     * test function: check term
     * case: success when only one condition is expected
     */
    @Test
    public void checkTermSuccess() {
        discounts.remove(0);
        discounts.addAll(data.getDiscounts(Data.NOT_STANDS_IN_TERM));
        assertTrue(orDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: check term
     * case: fail when all conditions are not existed
     */
    @Test
    public void checkTermFail() {
        discounts.clear();
        discounts.addAll(data.getDiscounts(Data.NOT_STANDS_IN_TERM));
        assertFalse(orDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: is valid
     * case: success
     */
    @Test
    public void isValidSuccess() {
        assertTrue(orDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidHasNotValidDiscount() {
        discounts.addAll(data.getDiscounts(Data.NULL_PRODUCT));
        assertFalse(orDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidHasNullDiscount() {
        orDiscount=new OrDiscount(null);
        assertFalse(orDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidEmptyDiscountList() {
        discounts.clear();
        assertFalse(orDiscount.isValid());
    }
}