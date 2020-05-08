package Domain.Discount;

import Domain.Product;

import java.util.HashMap;
import java.util.Set;

public interface Discount {
    void calculateDiscount(HashMap<Product, Integer> list);
    boolean checkTerm(HashMap<Product, Integer> list);
    boolean isValid();
    Set<String> getProducts();
}
