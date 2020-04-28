package Term;

import Data.Data;
import Data.TestData;
import Domain.Discount.Term.*;
import Domain.Product;
import org.junit.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TermTest {
    private Term term;
    private TestData data;
    private HashMap<Product,Integer> productAndAmounts;
    private List<Term> termsData;

    @Before
    public void setUp() {
            data=new TestData();
            productAndAmounts=data.getProductsAndAmount(Data.VALID);
            termsData=new ArrayList<>();
            termsData.add(data.getTerm(Data.VALID));
            termsData.add(data.getTerm(Data.VALID2));
    }

    /**
     * test class: base term
     * test function: check term
     * case: success
     */
    @Test
    public void checkTermBaseTermSuccess(){
        term=data.getTerm(Data.VALID);
        assertTrue(term.checkTerm(productAndAmounts,data.getRealProduct(Data.VALID).getName(),3));
    }

    /**
     * test class: base term
     * test function: check term
     * case: fail
     */
    @Test
    public void checkTermBaseTermFail(){
        term=data.getTerm(Data.VALID);
        assertFalse(term.checkTerm(productAndAmounts,data.getRealProduct(Data.VALID).getName(),100));
    }

    /**
     * test class: base term
     * test function: is valid
     * case: success
     */
    @Test
    public void isValidBaseTermTestSuccess() {
        term=data.getTerm(Data.VALID);
        assertTrue(term.isValid());
    }

    /**
     * test class: base term
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidBaseTermProductNull(){
        term=data.getTerm(Data.NULL_PRODUCT);
        assertFalse(term.isValid());
    }

    /**
     * test class: base term
     * test function: is valid
     * case: fail
     */
    @Test
    public void isValidBaseTermAmountLessThan0(){
        term=data.getTerm(Data.ZERO);
        assertFalse(term.isValid());
    }

    /**
     * test class: xor term
     * test function: check term
     * case: success
     */
    @Test
    public void checkTermXorTermSuccess(){
        term=new XorTerm(termsData);
        assertTrue(term.checkTerm(productAndAmounts,data.getRealProduct(Data.VALID).getName(),100));
    }

    /**
     * test class: xor term
     * test function: check term
     * case: fail
     */
    @Test
    public void checkTermXorTermFail(){
        term=new XorTerm(termsData);
        assertFalse(term.checkTerm(productAndAmounts,data.getRealProduct(Data.VALID).getName(),1));
    }

    /**
     * test class: xor term
     * test function: isValid
     * case: fail
     */
    @Test
    public void isValidXorTermFail(){
        term=new XorTerm(termsData);
        termsData.add(data.getTerm(Data.ZERO));
        assertFalse(term.isValid());
    }

    /**
     * test class: xor term
     * test function: cisValid
     * case: success
     */
    @Test
    public void isValidXorTermSuccess(){
        term=new XorTerm(termsData);
        assertTrue(term.isValid());
    }

    /**
     * test class: or term
     * test function: check term
     * case: success
     */
    @Test
    public void checkTermOrTermSuccess(){
        term=new OrTerm(termsData);
        assertTrue(term.checkTerm(productAndAmounts,data.getRealProduct(Data.VALID).getName(),100));
    }

    /**
     * test class: or term
     * test function: check term
     * case: fail
     */
    @Test
    public void checkTermOrTermFail(){
        term=new OrTerm(termsData);
        termsData.remove(1);
        assertFalse(term.checkTerm(productAndAmounts,data.getRealProduct(Data.VALID).getName(),100));
    }

    /**
     * test class: or term
     * test function: isValid
     * case: fail
     */
    @Test
    public void isValidOrTermFail(){
        term=new OrTerm(termsData);
        termsData.add(data.getTerm(Data.ZERO));
        assertFalse(term.isValid());
    }

    /**
     * test class: or term
     * test function: cisValid
     * case: success
     */
    @Test
    public void isValidOrrTermSuccess(){
        term=new OrTerm(termsData);
        assertTrue(term.isValid());
    }


    /**
     * test class: and term
     * test function: check term
     * case: success
     */
    @Test
    public void checkTermAndTermSuccess(){
        term=new AndTerm(termsData);
        assertTrue(term.checkTerm(productAndAmounts,data.getRealProduct(Data.VALID).getName(),1));
    }

    /**
     * test class: and term
     * test function: check term
     * case: fail
     */
    @Test
    public void checkTermAndTermFail(){
        term=new AndTerm(termsData);
        assertFalse(term.checkTerm(productAndAmounts,data.getRealProduct(Data.VALID).getName(),100));
    }

    /**
     * test class: and term
     * test function: isValid
     * case: fail
     */
    @Test
    public void isValidAndTermFail(){
        term=new AndTerm(termsData);
        termsData.add(data.getTerm(Data.ZERO));
        assertFalse(term.isValid());
    }

    /**
     * test class: and term
     * test function: cisValid
     * case: success
     */
    @Test
    public void isValidAndTermSuccess(){
        term=new AndTerm(termsData);
        assertTrue(term.isValid());
    }


}
