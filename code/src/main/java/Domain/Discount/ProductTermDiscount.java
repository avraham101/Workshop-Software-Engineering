package Domain.Discount;

import Domain.Discount.Term.Term;
import Domain.Product;

import java.util.HashMap;

public class ProductTermDiscount implements Discount {
    private Term term;
    private String product;
    private int amount;

    public ProductTermDiscount(Term term, String product, int amount) {
        this.term = term;
        this.product = product;
        this.amount = amount;
    }

    @Override
    public void calculateDiscount(HashMap<Product, Integer> list) {
        for(Product p:list.keySet()){
            if(p.getName().equals(product))
                list.put(p,Math.max(p.getAmount()-amount,0));
        }
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list) {
        boolean found=false;
        for(Product p:list.keySet())
            if(p.getName().equals(product)){
                if(p.getAmount()>=amount)
                    found=true;
                else
                    break;
            }
        return found&&term.checkTerm(list,product,amount);
    }
}
