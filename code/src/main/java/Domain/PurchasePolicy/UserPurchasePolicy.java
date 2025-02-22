package Domain.PurchasePolicy;

import DataAPI.OpCode;
import DataAPI.PaymentData;
import DataAPI.Response;
import Domain.Product;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="user_policy")
public class UserPurchasePolicy extends PurchasePolicy {

    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection
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
    public Response<Boolean> standInPolicy(PaymentData paymentData, String country,
                          HashMap<Product, Integer> products) {
        if (countries.contains(country)) {
            return new Response<>(true, OpCode.Success);
        }
        return new Response<>(false , OpCode.Country_Policy_Failed);
    }
}
