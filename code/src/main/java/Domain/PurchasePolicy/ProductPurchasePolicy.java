package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import DataAPI.ProductMinMax;
import Domain.Product;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ProductPurchasePolicy implements PurchasePolicy {

    private HashMap<String, ProductMinMax> amountPerProduct;

    public ProductPurchasePolicy(HashMap<String, ProductMinMax> maxAmountPerProduct) {
        this.amountPerProduct = maxAmountPerProduct;
    }

    @Override
    public boolean isValid() {
        return (amountPerProduct != null && !amountPerProduct.isEmpty());
    }

    @Override
    public List<String> getProducts() {
        return new LinkedList<>(amountPerProduct.keySet());
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, String country,
                                 HashMap<Product, Integer> products) {
        for (Product product: products.keySet()) {
            if (!(products.get(product) <= amountPerProduct.get(product.getName()).getMax()) ||
                    !(products.get(product) >= amountPerProduct.get(product.getName()).getMin())){
                return false;
            }
        }
        return true;
    }

}
