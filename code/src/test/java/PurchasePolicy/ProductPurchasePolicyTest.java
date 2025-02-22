package PurchasePolicy;

import Data.Data;
import Data.TestData;
import DataAPI.PaymentData;
import Domain.Product;
import Domain.PurchasePolicy.ProductPurchasePolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductPurchasePolicyTest {

    private TestData data;
    private ProductPurchasePolicy policy;

    @Before
    public void setUp() {
        Utils.Utils.TestMode();
        data = new TestData();
        policy = (ProductPurchasePolicy)data.getPurchasePolicy(Data.VALID_PRODUCT_PURCHASE_POLICY);
    }


    /**
     * test for valid basket purchase policy
     */
    @Test
    public void testProductPurchasePolicy() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String country = data.getDeliveryData(Data.VALID).getCountry();
        HashMap<Product, Integer> products = data.getProductsAndAmount(Data.VALID);
        assertTrue(policy.standInPolicy(paymentData, country, products).getValue());
    }

    /**
     * test for valid basket purchase policy - fail due large amount
     */
    @Test
    public void testProductPurchasePolicyFail() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String country = data.getDeliveryData(Data.VALID).getCountry();
        HashMap<Product, Integer> products = data.getProductsAndAmount(Data.LARGE_AMOUNT);
        assertFalse(policy.standInPolicy(paymentData, country, products).getValue());
    }

    /**
     * test for valid basket purchase policy - fail due small amount
     */
    @Test
    public void testProductPurchasePolicyFailSmallAmount() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String country = data.getDeliveryData(Data.VALID).getCountry();
        HashMap<Product, Integer> products = data.getProductsAndAmount(Data.SMALL_AMOUNT);
        assertFalse(policy.standInPolicy(paymentData, country, products).getValue());
    }



    /**
     * check if the policy is indeed valid
     */
    @Test
    public void checkValidPolicy() {
        assertTrue(policy.isValid());
    }

    /**
     * check if the policy is indeed not valid
     */
    @Test
    public void checkNotValidPolicyNullHash() {
        policy = (ProductPurchasePolicy) data.getPurchasePolicy(Data.NULL_PRODUCT_PURCHASE_POLICY);
        assertFalse(policy.isValid());
    }

    /**
     * check if the policy is indeed not valid with min greater than max
     */
    @Test
    public void checkNotValidPolicyMinGreaterThanMax() {
        policy = (ProductPurchasePolicy) data.getPurchasePolicy(Data.MIN_GREATER_THAN_MAX);
        assertFalse(policy.isValid());
    }

    /**
     * check if the policy is indeed not valid
     */
    @Test
    public void checkNotValidPolicyEmptyHash() {
        policy = (ProductPurchasePolicy) data.getPurchasePolicy(Data.EMPTY_PRODUCT_PURCHASE_POLICY);
        assertFalse(policy.isValid());
    }
}
