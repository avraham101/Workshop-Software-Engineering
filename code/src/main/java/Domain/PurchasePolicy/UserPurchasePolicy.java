package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import Domain.Product;
import Domain.ProductInCart;

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

    /**
     * check if the country is legal
     * @param paymentData - the data of the payment
     * @param country - the country of the user
     * @param products - the products of the basket
     * @return - true if stands in the policy
     */
    @Override
    public boolean standInPolicy(PaymentData paymentData, String country,
                                 HashMap<String, ProductInCart> products) {
        return countries.contains(country);
    }
}
