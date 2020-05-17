package Domain.PurchasePolicy.ComposePolicys;

import DataAPI.PaymentData;
import Domain.Product;
import Domain.ProductInCart;
import Domain.PurchasePolicy.PurchasePolicy;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class OrPolicy implements PurchasePolicy {

    private List<PurchasePolicy> policyList;

    public OrPolicy(List<PurchasePolicy> policyList) {
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

    /**
     * check or between policies
     * @param paymentData - the data of the payment
     * @param country - the country of the user
     * @param products - the products of the basket
     * @return - true if one of the the policies stands
     */
    @Override
    public boolean standInPolicy(PaymentData paymentData, String country,
                                 HashMap<String, ProductInCart> products) {
        boolean output = false;
        for (PurchasePolicy policy: policyList) {
            output = output | policy.standInPolicy(paymentData, country, products);
        }
        return output;
    }
}
