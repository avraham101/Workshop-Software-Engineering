package Domain.PurchasePolicy.ComposePolicys;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.Product;
import Domain.PurchasePolicy.PurchasePolicy;
import java.util.HashMap;
import java.util.List;

public class OrPolicy implements PurchasePolicy {

    private List<PurchasePolicy> policyList;

    public OrPolicy(List<PurchasePolicy> policyList) {
        this.policyList = policyList;
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData) {
        for (PurchasePolicy policy: policyList) {
            if(! policy.standInPolicy(paymentData, deliveryData)) {
                return false;
            }
        }
        return true;
    }
}
