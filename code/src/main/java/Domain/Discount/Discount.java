package Domain.Discount;

import Domain.Product;

import java.util.HashMap;

public interface Discount {
    public void calculateDiscount(HashMap<Product, Integer> list);
    public boolean checkTerm(HashMap<Product, Integer> list);


}
