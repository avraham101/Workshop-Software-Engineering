package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import Domain.Product;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class UserPurchasePolicy implements PurchasePolicy {

    private List<String> countries; // list of countries that can buy from the store

    public UserPurchasePolicy(List<String> countries) {
        this.countries = countries;
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
    public boolean standInPolicy(PaymentData paymentData, String country, HashMap<Product, Integer> products) {
        return countries.contains(country);
    }
}
