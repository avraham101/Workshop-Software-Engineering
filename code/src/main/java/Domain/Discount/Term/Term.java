package Domain.Discount.Term;

import Domain.Product;

import java.util.HashMap;
import java.util.Set;

public interface Term {
    boolean checkTerm(HashMap<Product, Integer> list,String product,int amount);

    boolean isValid();

    Set<String> getProducts();
}
