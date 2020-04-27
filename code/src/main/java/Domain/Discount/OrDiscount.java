package Domain.Discount;

import Domain.Product;

import java.util.HashMap;
import java.util.List;

public class OrDiscount implements Discount{

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
}
