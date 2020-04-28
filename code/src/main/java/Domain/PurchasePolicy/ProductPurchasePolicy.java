package Domain.PurchasePolicy;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;

import java.util.*;

public class ProductPurchasePolicy implements PurchasePolicy {

    private HashMap<String, Integer> maxAmountPerProduct;

    public ProductPurchasePolicy(HashMap<String, Integer> maxAmountPerProduct) {
        this.maxAmountPerProduct = maxAmountPerProduct;
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData) {
        for (ProductData product: deliveryData.getProducts()) {
            if (!(product.getAmount() <= maxAmountPerProduct.get(product.getProductName()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean isValid() {
        return (maxAmountPerProduct != null && !maxAmountPerProduct.isEmpty());
    }

    @Override
    public List<String> getProducts() {
        return new LinkedList<>(maxAmountPerProduct.keySet());
    }

}
