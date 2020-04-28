package Domain.PurchasePolicy.ComposePolicys;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.Product;
import Domain.PurchasePolicy.PurchasePolicy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class XorPolicy implements PurchasePolicy {

    private List<PurchasePolicy> policyList;

    public XorPolicy(List<PurchasePolicy> policyList) {
        this.policyList = policyList;
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

    @Override
    public boolean standInPolicy(PaymentData paymentData, String country, HashMap<Product, Integer> products) {
        int counter = 0;
        for (PurchasePolicy policy: policyList) {
            if(policy.standInPolicy(paymentData, country, products)) {
                counter += 1;
            }
        }
        return (counter == 1);
    }
}
