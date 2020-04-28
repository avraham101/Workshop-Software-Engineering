package Domain.PurchasePolicy;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class BasketPurchasePolicy implements PurchasePolicy {

    private int maxAmount; // the maximum amount pf products per basket

    public BasketPurchasePolicy(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData) {
        int counter = 0;
        for (ProductData productData: deliveryData.getProducts()) {
            counter += productData.getAmount();
        }
        return (counter <= maxAmount);
    }

    @Override
    public boolean isValid() {
        return (maxAmount >= 0);
    }

    @Override
    public List<String> getProducts() {
        return new LinkedList<>();
    }

}
