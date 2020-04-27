package PurchasePolicy;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.PurchasePolicy.SystemPurchasePolicy;
import org.junit.Before;
import org.junit.Test;

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
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(policy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test for valid basket purchase policy - fail due large amount
     */
    @Test
    public void testSystemPurchasePolicyFail() {
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertFalse(policy.standInPolicy(paymentData, deliveryData));
    }

}
