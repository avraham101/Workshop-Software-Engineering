package Domain.PurchasePolicy.ComposePolicys;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.PurchasePolicy.PurchasePolicy;
import java.util.LinkedList;
import java.util.List;

public class OrPolicy implements PurchasePolicy {

    private List<PurchasePolicy> policyList;

    public OrPolicy(List<PurchasePolicy> policyList) {
        this.policyList = policyList;
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData) {
        boolean output = false;
        for (PurchasePolicy policy: policyList) {
            output = output | policy.standInPolicy(paymentData, deliveryData);
        }
        return output;
    }

    @Override
    public boolean isValid() {
        if (policyList == null || policyList.isEmpty())
            return false;
        for (PurchasePolicy policy: policyList) {
            if (!policy.isValid()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> getProducts() {
        List<String> products = new LinkedList<>();
        for (PurchasePolicy policy: policyList) {
            products.addAll(policy.getProducts());
        }
        return products;
    }
}
