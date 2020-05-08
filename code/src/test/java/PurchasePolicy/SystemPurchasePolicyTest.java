package PurchasePolicy;

import Data.Data;
import Data.TestData;
import DataAPI.PaymentData;
import Domain.Product;
import Domain.PurchasePolicy.SystemPurchasePolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SystemPurchasePolicyTest {

    private TestData data;
    private SystemPurchasePolicy policy;

    @Before
    public void setUp() {
        data = new TestData();
        policy = (SystemPurchasePolicy)data.getPurchasePolicy(Data.VALID_SYSTEM_PURCHASE_POLICY);
    }


    /**
     * test for valid basket purchase policy
     */
    @Test
    public void testSystemPurchasePolicy() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String country = data.getDeliveryData(Data.VALID).getCountry();
        HashMap<Product, Integer> products = data.getProductsAndAmount(Data.VALID);
        assertTrue(policy.standInPolicy(paymentData, country, products));
    }

    /**
     * test for valid basket purchase policy - fail due large amount
     */
    @Test
    public void testSystemPurchasePolicyFail() {
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        String country = data.getDeliveryData(Data.VALID).getCountry();
        HashMap<Product, Integer> products = data.getProductsAndAmount(Data.VALID);
        assertFalse(policy.standInPolicy(paymentData, country, products));
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
    public void checkNotValidPolicy() {
        policy = (SystemPurchasePolicy) data.getPurchasePolicy(Data.INVALID_SYSTEM_PURCHASE_POLICY);
        assertFalse(policy.isValid());
    }
}
