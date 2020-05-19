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
        if (amountPerProduct == null || amountPerProduct.isEmpty())
            return false;
        for (ProductMinMax minMax: amountPerProduct.values()) {
            if (minMax.getMin() > minMax.getMax())
                return false;
        }
        return true;
    }

    @Override
    public List<String> getProducts() {
        return new LinkedList<>(amountPerProduct.keySet());
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, String country,
                                 HashMap<Product, Integer> products) {
        for (Product product: products.keySet()) {
            if(amountPerProduct.get(product.getName())==null)
                return false;
            if (!(products.get(product) <= amountPerProduct.get(product.getName()).getMax()) ||
                    !(products.get(product) >= amountPerProduct.get(product.getName()).getMin())){
                return false;
            }
        }
        return true;
    }

}
