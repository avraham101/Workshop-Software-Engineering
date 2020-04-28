package Discount;

import Data.*;
import Domain.Discount.ProductTermDiscount;
import Domain.Product;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ProductTermDiscountTest {
    private TestData data;
    private ProductTermDiscount productTermDiscount;
    private HashMap<Product,Integer> productAndAmounts;

    @Before
    public void setUp() {
        data=new TestData();
        productAndAmounts=data.getProductsAndAmount(Data.VALID);
    }

    /**
     * test function: calculate discount
     * test: check that amounts are like they should be after discount
     */
    @Test
    public void calculateDiscount() {
        productTermDiscount=new ProductTermDiscount(data.getTerm(Data.VALID),
                data.getRealProduct(Data.VALID2).getName(),3);
        productTermDiscount.calculateDiscount(productAndAmounts);
        //test that the product discount remove the product from the list
        for(Product p:productAndAmounts.keySet())
            if(p.getName().equals(data.getRealProduct(Data.VALID2).getName()))
                assertEquals(97,productAndAmounts.get(p).intValue());
    }

    /**
     * test function: check term
     * case: success
     */
    @Test
    public void checkTermSuccess() {
        productTermDiscount=new ProductTermDiscount(data.getTerm(Data.VALID),
                data.getRealProduct(Data.VALID2).getName(),3);
        assertTrue(productTermDiscount.checkTerm(productAndAmounts));

    }

    /**
     * test function: check term
     * case: fail
     */
    @Test
    public void checkTermFail() {
        productTermDiscount=new ProductTermDiscount(data.getTerm(Data.VALID),
                data.getRealProduct(Data.VALID).getName(),100);
        assertFalse(productTermDiscount.checkTerm(productAndAmounts));
    }

    /**
     * test function: is valid
     * case: success
     */
    @Test
    public void isValidSuccess() {
        productTermDiscount=new ProductTermDiscount(data.getTerm(Data.VALID),
                data.getRealProduct(Data.VALID).getName(),100);
        assertTrue(productTermDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidNullTerm(){
        productTermDiscount=new ProductTermDiscount(null,
                data.getRealProduct(Data.VALID).getName(),100);
        assertFalse(productTermDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidNotValidTerm(){
        productTermDiscount=new ProductTermDiscount(data.getTerm(Data.NULL_PRODUCT),
                data.getRealProduct(Data.VALID).getName(),100);
        assertFalse(productTermDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidNullProduct() {
        productTermDiscount=new ProductTermDiscount(data.getTerm(Data.VALID),
                null,100);
        assertFalse(productTermDiscount.isValid());
    }

    /**
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidNegAmount(){
        productTermDiscount=new ProductTermDiscount(data.getTerm(Data.VALID),
                data.getRealProduct(Data.VALID).getName(),0);
        assertFalse(productTermDiscount.isValid());
    }
}