package Domain.PurchasePolicy.ComposePolicys;

import DataAPI.PaymentData;
import Domain.Product;
import Domain.PurchasePolicy.PurchasePolicy;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name ="xor_policy")
public class XorPolicy extends PurchasePolicy {

    @OneToMany(cascade= CascadeType.ALL)
    @JoinTable(name="policy_inside_policy",
            joinColumns =@JoinColumn(name = "holder_id", referencedColumnName="id"),
            inverseJoinColumns={@JoinColumn(name="holdee_id", referencedColumnName="id")}
    )
    private List<PurchasePolicy> policyList;

    public XorPolicy(List<PurchasePolicy> policyList) {
        this.policyList = policyList;
    }

    public XorPolicy() {
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
