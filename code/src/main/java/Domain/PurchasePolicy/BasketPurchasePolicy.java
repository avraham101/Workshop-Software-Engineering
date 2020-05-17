package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import Domain.Product;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class BasketPurchasePolicy implements PurchasePolicy {

    private int maxAmount; // the maximum amount pf products per basket

    public BasketPurchasePolicy(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public boolean isValid() {
        return (maxAmount >= 0);
    }

    @Override
    public List<String> getProducts() {
        return new LinkedList<>();
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, String country, HashMap<Product, Integer> products) {
        int counter = 0;
        for (int amount: products.values()) {
            counter += amount;
        }
        return (counter <= maxAmount);
    }

}
