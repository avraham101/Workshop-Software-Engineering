package PurchasePolicy;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.PurchasePolicy.ProductPurchasePolicy;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProductPurchasePolicyTest {

    private TestData data;
    private ProductPurchasePolicy policy;

    @Before
    public void setUp() {
        data = new TestData();
        policy = (ProductPurchasePolicy)data.getPurchasePolicy(Data.VALID_PRODUCT_PURCHASE_POLICY);
    }


    /**
     * test for valid basket purchase policy
     */
    @Test
    public void testProductPurchasePolicy() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(policy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test for valid basket purchase policy - fail due large amount
     */
    @Test
    public void testProductPurchasePolicyFail() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.LARGE_AMOUNT);
        assertFalse(policy.standInPolicy(paymentData, deliveryData));
    }

}
