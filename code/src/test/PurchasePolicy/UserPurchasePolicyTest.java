package PurchasePolicy;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.Product;
import org.junit.Before;
import org.junit.Test;
import Domain.PurchasePolicy.UserPurchasePolicy;


import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserPurchasePolicyTest {

    private TestData data;
    private UserPurchasePolicy policy;

    @Before
    public void setUp() {
        data = new TestData();
        policy = (UserPurchasePolicy)data.getPurchasePolicy(Data.VALID_USER_PURCHASE_POLICY);
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
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        String country = data.getDeliveryData(Data.INVALID_COUNTRY).getCountry();
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
    public void checkNotValidPolicyNullList() {
        policy = (UserPurchasePolicy) data.getPurchasePolicy(Data.NULL_USER_PURCHASE_POLICY);
        assertFalse(policy.isValid());
    }

    /**
     * check if the policy is indeed not valid
     */
    @Test
    public void checkNotValidPolicyEmptyList() {
        policy = (UserPurchasePolicy) data.getPurchasePolicy(Data.EMPTY_USER_PURCHASE_POLICY);
        assertFalse(policy.isValid());
    }
}
