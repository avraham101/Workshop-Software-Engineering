package PurchasePolicy;

import Data.Data;
import Data.TestData;
import Domain.PurchasePolicy.BasketPurchasePolicy;
import org.junit.Before;

public class OrPurchasePolicyTest {

    private TestData data;
    private BasketPurchasePolicy basketPurchasePolicy;

    @Before
    public void setUp() {
        data = new TestData();
        basketPurchasePolicy = (BasketPurchasePolicy)data.getPurchasePolicy(Data.VALID_BASKET_PURCHASE_POLICY);
    }



}
