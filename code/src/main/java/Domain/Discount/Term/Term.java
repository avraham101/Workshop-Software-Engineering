package Domain.Discount.Term;

import Domain.Product;

import java.util.HashMap;

public interface Term {
    public boolean checkTerm(HashMap<Product, Integer> list,String product,int amount);
}
