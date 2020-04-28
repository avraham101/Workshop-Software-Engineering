package Domain.Discount;

import Domain.Product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class    OrDiscount implements Discount{

    private List<Discount> discounts;

    public OrDiscount(List<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public void calculateDiscount(HashMap<Product, Integer> list) {
        for(Discount d:discounts){
            if(d.checkTerm(list))
                d.calculateDiscount(list);
        }
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list) {
        for(Discount d:discounts){
            if(d.checkTerm(list))
                return true;
        }
        return false;
    }

    @Override
    public boolean isValid() {
        if(discounts==null||discounts.isEmpty())
            return false;
        for(Discount d:discounts){
            if(!d.isValid())
                return false;
        }
        return true;
    }

    @Override
    public Set<String> getProducts() {
        Set<String> products=new HashSet<>();
        for(Discount d:discounts)
            products.addAll(d.getProducts());
        return products;
    }
}
