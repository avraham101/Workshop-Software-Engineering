package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import Domain.Product;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
            if (maxAmountPerProduct.containsKey(product.getName())&&
                    !(products.get(product) <= maxAmountPerProduct.get(product.getName()))) {
                return false;
            }
        }
        return true;
    }

}
