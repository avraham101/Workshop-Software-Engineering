package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import DataAPI.ProductMinMax;
import Domain.Product;
import org.hibernate.collection.internal.PersistentMap;

import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "product_policy")
public class ProductPurchasePolicy extends PurchasePolicy {


   @OneToMany(cascade= CascadeType.ALL,fetch = FetchType.EAGER)
   @MapKey(name = "productName")
   @JoinTable(name="min_max_and_policies",
           joinColumns ={@JoinColumn(name = "pol_id", referencedColumnName="pol_id")},
           inverseJoinColumns={@JoinColumn(name="min_max_id", referencedColumnName="pr_min_max_id")}
   )
    private Map<String, ProductMinMax> amountPerProduct;

    public ProductPurchasePolicy(HashMap<String, ProductMinMax> maxAmountPerProduct) {
        this.amountPerProduct =  maxAmountPerProduct;
    }

    public ProductPurchasePolicy() {
    }

    @Override
    public boolean isValid() {
        if (amountPerProduct == null || amountPerProduct.isEmpty())
            return false;
        for (ProductMinMax minMax: amountPerProduct.values()) {
            if(minMax.getMin()<0 || minMax.getMax()<0)
                return false;
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
            if(amountPerProduct.containsKey(product.getName())) {
                if (!(products.get(product) <= amountPerProduct.get(product.getName()).getMax()) ||
                        !(products.get(product) >= amountPerProduct.get(product.getName()).getMin())) {
                    return false;
                }
            }
        }
        return true;
    }

    public Map<String, ProductMinMax> getAmountPerProduct() {
        return amountPerProduct;
    }
}
