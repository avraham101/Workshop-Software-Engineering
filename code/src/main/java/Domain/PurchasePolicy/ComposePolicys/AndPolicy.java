package Domain.PurchasePolicy.ComposePolicys;

import DataAPI.PaymentData;
import Domain.Product;
import Domain.PurchasePolicy.PurchasePolicy;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
@Entity
@Table(name = "and_policy")
public class AndPolicy extends PurchasePolicy {

    @OneToMany(cascade= CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="policy_inside_policy",
            joinColumns =@JoinColumn(name = "holder_id", referencedColumnName="pol_id"),
            inverseJoinColumns={@JoinColumn(name="holdee_id", referencedColumnName="pol_id")}
    )
    private List<PurchasePolicy> policyList;

    public AndPolicy(List<PurchasePolicy> policyList) {
        this.policyList = policyList;
    }

    public AndPolicy() {
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
        for (PurchasePolicy policy: policyList) {
            if(! policy.standInPolicy(paymentData, country, products)) {
                return false;
            }
        }
        return true;
    }

}
