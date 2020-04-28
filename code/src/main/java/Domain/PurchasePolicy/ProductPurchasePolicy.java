package Domain.PurchasePolicy;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.Product;

import java.util.*;

public class ProductPurchasePolicy implements PurchasePolicy {

    private HashMap<String, Integer> maxAmountPerProduct;

    public ProductPurchasePolicy(HashMap<String, Integer> maxAmountPerProduct) {
        this.maxAmountPerProduct = maxAmountPerProduct;
    }

    @Override
    public boolean isValid() {
        return (maxAmountPerProduct != null && !maxAmountPerProduct.isEmpty());
    }

    @Override
    public List<String> getProducts() {
        return new LinkedList<>(maxAmountPerProduct.keySet());
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, String country, HashMap<Product, Integer> products) {
        for (Product product: products.keySet()) {
            if (!(products.get(product) <= maxAmountPerProduct.get(product.getName()))) {
                return false;
            }
        }
        return true;
    }

}
