package PurchasePolicy;

import Data.Data;
import Data.TestData;
import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.Permission;
import Domain.PermissionType;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import Domain.Subscribe;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;


import static org.junit.Assert.*;

public class BasketPurchasePolicyTest {

    private TestData data;
    private BasketPurchasePolicy policy;

    @Before
    public void setUp() {
        data = new TestData();
        policy = (BasketPurchasePolicy)data.getPurchasePolicy(Data.VALID_BASKET_PURCHASE_POLICY);
    }


    /**
     * test for valid basket purchase policy
     */
    @Test
    public void testBasketPurchasePolicy() {
        PaymentData paymentData = data.getPaymentData(Data.VALID);
        DeliveryData deliveryData = data.getDeliveryData(Data.VALID);
        assertTrue(policy.standInPolicy(paymentData, deliveryData));
    }

    /**
     * test for valid basket purchase policy - fail due large amount
     */
    @Test
    public void testBasketPurchasePolicyFail() {
        PaymentData paymentData = data.getPaymentData(Data.UNDER_AGE);
        DeliveryData deliveryData = data.getDeliveryData(Data.FAIL_POLICY);
        assertFalse(policy.standInPolicy(paymentData, deliveryData));
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
        policy = (BasketPurchasePolicy)data.getPurchasePolicy(Data.INVALID_BASKET_PURCHASE_POLICY);
        assertFalse(policy.isValid());
    }

}
