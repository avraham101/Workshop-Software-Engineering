package Domain.PurchasePolicy.ComposePolicys;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.Product;
import Domain.Purchase;
import Domain.PurchasePolicy.PurchasePolicy;

import java.util.HashMap;
import java.util.List;

public class AndPolicy implements PurchasePolicy {

    private List<PurchasePolicy> policyList;

    public AndPolicy(List<PurchasePolicy> policyList) {
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

    /**
     * add policy to the policies list
     * @param purchasePolicy - the policy we want to add
     */
    public void addPolicy(PurchasePolicy purchasePolicy) {
        policyList.add(purchasePolicy);
    }

}
