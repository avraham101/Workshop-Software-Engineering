package PurchasePolicy;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.PurchasePolicy.*;
import Domain.PurchasePolicy.ComposePolicys.AndPolicy;
import Domain.PurchasePolicy.ComposePolicys.OrPolicy;
import Domain.PurchasePolicy.ComposePolicys.XorPolicy;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ComposePurchasePolicyTest {

    private TestData data;
    private OrPolicy orPolicy;
    private AndPolicy andPolicy;
    private XorPolicy xorPolicy;

    @Before
    public void setUp() {
        data = new TestData();
        List<PurchasePolicy> policies = setPolicies();
        orPolicy = new OrPolicy(policies);
        andPolicy = new AndPolicy(policies);
        xorPolicy = new XorPolicy(policies);
    }

    @After
    public void tearDown() {
        List<PurchasePolicy> empty = new LinkedList<>();
        orPolicy = new OrPolicy(empty);
        andPolicy = new AndPolicy(empty);
        xorPolicy = new XorPolicy(empty);
    }


    /**
     * set up list of or with and
     */
    private void setUpOrWithAnd() {
        List<PurchasePolicy> policiesAnd = new LinkedList<>();
        List<PurchasePolicy> policiesOr = new LinkedList<>();
        BasketPurchasePolicy basketPurchasePolicy = (BasketPurchasePolicy)data.getPurchasePolicy(Data.VALID_BASKET_PURCHASE_POLICY);
        ProductPurchasePolicy productPurchasePolicy = (ProductPurchasePolicy)data.getPurchasePolicy(Data.VALID_PRODUCT_PURCHASE_POLICY);
        SystemPurchasePolicy systemPurchasePolicy = (SystemPurchasePolicy)data.getPurchasePolicy(Data.VALID_SYSTEM_PURCHASE_POLICY);
        policiesAnd.add(basketPurchasePolicy);
        policiesAnd.add(productPurchasePolicy);
        policiesOr.add(systemPurchasePolicy);
        andPolicy = new AndPolicy(policiesAnd);
        policiesOr.add(andPolicy);
        orPolicy = new OrPolicy(policiesOr);
    }

    /**
     * set up list of or with and
     */
    private void setUpAndWithOr() {
        List<PurchasePolicy> policiesOr = new LinkedList<>();
        List<PurchasePolicy> policiesAnd = new LinkedList<>();
        BasketPurchasePolicy basketPurchasePolicy = (BasketPurchasePolicy)data.getPurchasePolicy(Data.VALID_BASKET_PURCHASE_POLICY);
        ProductPurchasePolicy productPurchasePolicy = (ProductPurchasePolicy)data.getPurchasePolicy(Data.VALID_PRODUCT_PURCHASE_POLICY);
        SystemPurchasePolicy systemPurchasePolicy = (SystemPurchasePolicy)data.getPurchasePolicy(Data.VALID_SYSTEM_PURCHASE_POLICY);
        policiesOr.add(basketPurchasePolicy);
        policiesOr.add(productPurchasePolicy);
        policiesAnd.add(systemPurchasePolicy);
        orPolicy = new OrPolicy(policiesOr);
        policiesAnd.add(orPolicy);
        andPolicy = new AndPolicy(policiesAnd);
    }

    /**
     * set up list of or with xor
     */
    private void setUpOrWithXor() {
        List<PurchasePolicy> policiesOr = new LinkedList<>();
        List<PurchasePolicy> policiesXor = new LinkedList<>();
        BasketPurchasePolicy basketPurchasePolicy = (BasketPurchasePolicy)data.getPurchasePolicy(Data.VALID_BASKET_PURCHASE_POLICY);
        ProductPurchasePolicy productPurchasePolicy = (ProductPurchasePolicy)data.getPurchasePolicy(Data.VALID_PRODUCT_PURCHASE_POLICY);
        SystemPurchasePolicy systemPurchasePolicy = (SystemPurchasePolicy)data.getPurchasePolicy(Data.VALID_SYSTEM_PURCHASE_POLICY);
        policiesXor.add(basketPurchasePolicy);
        policiesXor.add(systemPurchasePolicy);
        policiesOr.add(productPurchasePolicy);
        xorPolicy = new XorPolicy(policiesXor);
        policiesOr.add(xorPolicy);
        orPolicy = new OrPolicy(policiesOr);
    }

    /**
     * set up list of and with xor
     */
    private void setUpAndWithXor() {
        List<PurchasePolicy> policiesAnd = new LinkedList<>();
        List<PurchasePolicy> policiesXor = new LinkedList<>();
        BasketPurchasePolicy basketPurchasePolicy = (BasketPurchasePolicy)data.getPurchasePolicy(Data.VALID_BASKET_PURCHASE_POLICY);
        ProductPurchasePolicy productPurchasePolicy = (ProductPurchasePolicy)data.getPurchasePolicy(Data.VALID_PRODUCT_PURCHASE_POLICY);
        SystemPurchasePolicy systemPurchasePolicy = (SystemPurchasePolicy)data.getPurchasePolicy(Data.VALID_SYSTEM_PURCHASE_POLICY);
        policiesXor.add(basketPurchasePolicy);
        policiesXor.add(systemPurchasePolicy);
        policiesAnd.add(productPurchasePolicy);
        xorPolicy = new XorPolicy(policiesXor);
        policiesAnd.add(xorPolicy);
        andPolicy = new AndPolicy(policiesAnd);
    }

    /**
     * set up list of xor with and
     */
    private void setUpXorWithAnd() {
        List<PurchasePolicy> policiesXor = new LinkedList<>();
        List<PurchasePolicy> policiesAnd = new LinkedList<>();
        UserPurchasePolicy userPurchasePolicy = (UserPurchasePolicy) data.getPurchasePolicy(Data.VALID_USER_PURCHASE_POLICY);
        ProductPurchasePolicy productPurchasePolicy = (ProductPurchasePolicy)data.getPurchasePolicy(Data.VALID_PRODUCT_PURCHASE_POLICY);
        SystemPurchasePolicy systemPurchasePolicy = (SystemPurchasePolicy)data.getPurchasePolicy(Data.VALID_SYSTEM_PURCHASE_POLICY);
        policiesAnd.add(userPurchasePolicy);
        policiesAnd.add(systemPurchasePolicy);
        policiesXor.add(productPurchasePolicy);
        andPolicy = new AndPolicy(policiesAnd);
        policiesXor.add(andPolicy);
        xorPolicy = new XorPolicy(policiesXor);
    }

    /**
     * set up list of xor with or
     */
    private void setUpXorWithOr() {
        List<PurchasePolicy> policiesXor = new LinkedList<>();
        List<PurchasePolicy> policiesOr = new LinkedList<>();
        UserPurchasePolicy userPurchasePolicy = (UserPurchasePolicy) data.getPurchasePolicy(Data.VALID_USER_PURCHASE_POLICY);
        ProductPurchasePolicy productPurchasePolicy = (ProductPurchasePolicy)data.getPurchasePolicy(Data.VALID_PRODUCT_PURCHASE_POLICY);
        SystemPurchasePolicy systemPurchasePolicy = (SystemPurchasePolicy)data.getPurchasePolicy(Data.VALID_SYSTEM_PURCHASE_POLICY);
        policiesOr.add(userPurchasePolicy);
        policiesOr.add(systemPurchasePolicy);
        policiesXor.add(productPurchasePolicy);
        andPolicy = new AndPolicy(policiesOr);
        policiesXor.add(andPolicy);
        xorPolicy = new XorPolicy(policiesXor);
    }

    /**
     * set list of policies
     * @return - a list of all the policies
     */
    private List<PurchasePolicy> setPolicies() {
        BasketPurchasePolicy basketPurchasePolicy = (BasketPurchasePolicy)data.getPurchasePolicy(Data.VALID_BASKET_PURCHASE_POLICY);
        ProductPurchasePolicy productPurchasePolicy = (ProductPurchasePolicy)data.getPurchasePolicy(Data.VALID_PRODUCT_PURCHASE_POLICY);
        SystemPurchasePolicy systemPurchasePolicy = (SystemPurchasePolicy)data.getPurchasePolicy(Data.VALID_SYSTEM_PURCHASE_POLICY);
        UserPurchasePolicy userPurchasePolicyTest = (UserPurchasePolicy)data.getPurchasePolicy(Data.VALID_USER_PURCHASE_POLICY);
        List<PurchasePolicy> policies = new LinkedList<>();
        policies.add(basketPurchasePolicy);
        policies.add(productPurchasePolicy);
        policies.add(systemPurchasePolicy);
        policies.add(userPurchasePolicyTest);
        return policies;
    }

    /**
     * test an or composition between policies
     */
    @Test
    public void testOrPolicies() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(orPolicy .standInPolicy(paymentData, deliveryData));
    }

    /**
     * test an or composition between policies with fail in user policy
     */
    @Test
    public void testOrPoliciesFailInOne() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.INVALID_COUNTRY);
        assertTrue(orPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test an or composition between policies with fail in all policies
     */
    @Test
    public void testOrPoliciesFailInAll() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.INVALID_COUNTRY);
        assertTrue(orPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test an and composition between policies
     */
    @Test
    public void testAndPolicies() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(andPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test an and composition between policies with one policy fail
     */
    @Test
    public void testAndPoliciesOneFail() {
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertFalse(andPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test an and composition between policies with all policies fail
     */
    @Test
    public void testAndPoliciesAllFail() {
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.FAIL_POLICY);
        assertFalse(andPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test an xor composition between policies with one policy stand
     */
    @Test
    public void testXorPoliciesOnePoliciesStands() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.FAIL_POLICY);
        assertTrue(xorPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test an xor composition between policies with all the policies stands
     */
    @Test
    public void testXorPoliciesAllPoliciesStands() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertFalse(xorPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test an xor composition between policies with no policy
     */
    @Test
    public void testXorPoliciesAllPoliciesFails() {
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.FAIL_POLICY);
        assertFalse(xorPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test policies with and between them with or
     */
    @Test
    public void testOrWithAnd() {
        setUpOrWithAnd();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(orPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test policies with and between them with or
     */
    @Test
    public void testOrWithAndOneSideFail() {
        setUpOrWithAnd();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.LARGE_AMOUNT);
        assertTrue(orPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test policies with and between them with or
     */
    @Test
    public void testOrWithAndBothSidesFail() {
        setUpOrWithAnd();
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.FAIL_POLICY);
        assertFalse(orPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * check or policies with and between them
     */
    @Test
    public void testAndWithOr() {
        setUpAndWithOr();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(andPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * check or policies with and between them when one side fail
     */
    @Test
    public void testAndWithOrOneSideFail() {
        setUpAndWithOr();
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertFalse(andPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * check xor policies with or between them
     */
    @Test
    public void testOrWithXor() {
        setUpOrWithXor();
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(orPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * check xor policies with or between them with One side fail
     */
    @Test
    public void testOrWithXorOneSideFail() {
        setUpOrWithXor();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.LARGE_AMOUNT);
        assertTrue(orPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * check xor policies with or between them with both sides fail
     */
    @Test
    public void testOrWithXorAllSidesFail() {
        setUpOrWithXor();
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.FAIL_POLICY);
        assertFalse(orPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * check xor policies with and between them
     */
    @Test
    public void testAndWithXor() {
        setUpAndWithXor();
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(andPolicy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * check xor policies with and between them with one side fail
     */
    @Test
    public void testAndWithXorOneSideFail() {
        setUpAndWithXor();
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.LARGE_AMOUNT);
        assertFalse(andPolicy.standInPolicy(paymentData, deliveryData));
    }

    @Test
    public void testXorWithAnd() {
        setUpXorWithAnd();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.LARGE_AMOUNT);
        assertTrue(xorPolicy.standInPolicy(paymentData, deliveryData));
    }

    @Test
    public void testXorWithAndFail() {
        setUpXorWithAnd();
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertFalse(xorPolicy.standInPolicy(paymentData, deliveryData));
    }

    @Test
    public void testXorWithOr() {
        setUpXorWithOr();
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.INVALID_COUNTRY);
        assertTrue(xorPolicy.standInPolicy(paymentData, deliveryData));
    }

    @Test
    public void testXorWithOrFail() {
        setUpXorWithOr();
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.FAIL_POLICY);
        assertFalse(xorPolicy.standInPolicy(paymentData, deliveryData));
    }


}
