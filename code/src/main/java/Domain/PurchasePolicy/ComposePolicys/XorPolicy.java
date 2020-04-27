package Domain.PurchasePolicy.ComposePolicys;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.Product;
import Domain.PurchasePolicy.PurchasePolicy;

import java.util.HashMap;
import java.util.List;

public class XorPolicy implements PurchasePolicy {

    private List<PurchasePolicy> policyList;

    public XorPolicy(List<PurchasePolicy> policyList) {
        this.policyList = policyList;
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData) {
        int counter = 0;
        for (PurchasePolicy policy: policyList) {
            if(policy.standInPolicy(paymentData, deliveryData)) {
                counter += 1;
            }
        }
        return (counter == 1);
    }

    /**
     * add policy to the policies list
     * @param purchasePolicy - the policy we want to add
     */
    public void addPolicy(PurchasePolicy purchasePolicy) {
        policyList.add(purchasePolicy);
    }
}
