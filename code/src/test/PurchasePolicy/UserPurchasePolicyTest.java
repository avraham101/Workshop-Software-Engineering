package PurchasePolicy;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import org.junit.Before;
import org.junit.Test;
import Domain.PurchasePolicy.UserPurchasePolicy;


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
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(policy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test for valid basket purchase policy - fail due large amount
     */
    @Test
    public void testSystemPurchasePolicyFail() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.INVALID_COUNTRY);
        assertFalse(policy.standInPolicy(paymentData, deliveryData));
    }

}
