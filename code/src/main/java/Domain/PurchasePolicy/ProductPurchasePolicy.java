package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import Domain.Product;
import Domain.ProductInCart;

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

    /**
     * check if each product is both above the minimum and less than the maximum
     * @param paymentData - the data of the payment
     * @param country - the country of the user
     * @param products - the products of the basket
     * @return - true if stands in the policy
     */
    //TODO - add minimum
    @Override
    public boolean standInPolicy(PaymentData paymentData, String country,
                                 HashMap<String, ProductInCart> products) {
        for (ProductInCart product: products.values()) {
           String productNAme = product.getProductName();
           if (maxAmountPerProduct.containsKey(productNAme)&&
                   !(product.getAmount() <= maxAmountPerProduct.get(productNAme))) {
               return false;
           }
        }
        return true;
    }

}
