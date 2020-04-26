package Domain.PurchasePolicy;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import java.util.HashMap;

public class ProductPurchasePolicy implements PurchasePolicy {

    private HashMap<ProductData, Integer> maxAmountPerProduct;

    public ProductPurchasePolicy(HashMap<ProductData, Integer> maxAmountPerProduct) {
        this.maxAmountPerProduct = maxAmountPerProduct;
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData) {
        for (ProductData product: deliveryData.getProducts()) {
            if (!(product.getAmount() <= maxAmountPerProduct.get(product))) {
                return false;
            }
        }
        return true;
    }

}
