package Discount;

import Data.Data;
import Data.TestData;
import Domain.Discount.Discount;
import Domain.Discount.XorDiscount;
import Domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class XorDiscountTest {

    private TestData data;
    private XorDiscount xorDiscount;
    private HashMap<Product,Integer> productAndAmounts;
    private List<Discount> discounts;

    @Before
    public void setUp() {
        data=new TestData();
        productAndAmounts=data.getProductsAndAmount();
        discounts=data.getDiscounts(Data.VALID);
        xorDiscount=new XorDiscount(discounts);
    }


    /**
     * test function: calculate discount
     * case: success exactly one of the discount happened
     */
    @Test
    public void calculateDiscountTest() {
        xorDiscount.calculateDiscount(productAndAmounts);
        for(Product p:productAndAmounts.keySet())
            if(p.getName().equals(data.getRealProduct(Data.VALID).getName()))
                assertEquals(9,p.getPrice(),0.01);
            else
                assertEquals(10,p.getPrice(),0.01);
    }

    /**
     * test function: check term
     * case: success when only one condition is expected
     */
    @Test
    public void checkTermSuccess() {
        discounts.remove(0);
        discounts.addAll(data.getDiscounts(Data.NOT_STANDS_IN_TERM));
        assertTrue(xorDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: check term
     * case: fail when no condition is expected
     */
    @Test
    public void checkTermFailNoOneISValid() {
        discounts.clear();
        discounts.addAll(data.getDiscounts(Data.NOT_STANDS_IN_TERM));
        assertFalse(xorDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: check term
     * case: fail when more than one condition is expected
     */
    @Test
    public void checkTermFailMoreThanOneISValid() {
        assertFalse(xorDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: is valid
     * case: success
     */
    @Test
    public void isValidSuccess() {
        assertTrue(xorDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidHasNotValidDiscount() {
        discounts.addAll(data.getDiscounts(Data.NULL_PRODUCT));
        assertFalse(xorDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidHasNullDiscount() {
        xorDiscount=new XorDiscount(null);
        assertFalse(xorDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidEmptyDiscountList() {
        discounts.clear();
        assertFalse(xorDiscount.isValid());
    }



}