package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import Domain.Product;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="user_policy")
public class UserPurchasePolicy extends PurchasePolicy {

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="policy_country", joinColumns=@JoinColumn(name="id"))
    @Column(name="country")
    private List<String> countries; // list of countries that can buy from the store

    public UserPurchasePolicy(List<String> countries) {
        this.countries = countries;
    }

    public UserPurchasePolicy() {
    }

    @Override
    public boolean isValid() {
        return (countries != null && !countries.isEmpty());
    }

    @Override
    public List<String> getProducts() {
        return new LinkedList<>();
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, String country,
                                 HashMap<Product, Integer> products) {
        return countries.contains(country);
    }
}
