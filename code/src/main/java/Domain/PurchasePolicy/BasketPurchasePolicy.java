package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import Domain.Product;
import Domain.ProductInCart;

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

    /**
     * check if the number of products is less than the maximum
     * @param paymentData - the data of the payment
     * @param country - the country of the user
     * @param products - the products of the basket
     * @return - true if stand in the policy
     */
    @Override
    public boolean standInPolicy(PaymentData paymentData, String country,
                                 HashMap<String, ProductInCart> products) {
        int counter = 0;
        for (ProductInCart product: products.values()) {
            int amount = product.getAmount();
            counter += amount;
        }
        return (counter <= maxAmount);
    }

}
