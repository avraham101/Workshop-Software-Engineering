package Domain.PurchasePolicy;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.Product;

import java.util.HashMap;

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
}
