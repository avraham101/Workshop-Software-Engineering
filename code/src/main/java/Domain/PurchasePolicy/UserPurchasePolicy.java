package Domain.PurchasePolicy;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.Product;

import java.util.HashMap;
import java.util.List;

public class UserPurchasePolicy implements PurchasePolicy {

    private List<String> countries; // list of countries that can buy from the store

    public UserPurchasePolicy(List<String> countries) {
        this.countries = countries;
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData) {
        return countries.contains(deliveryData.getCountry());
    }
}
