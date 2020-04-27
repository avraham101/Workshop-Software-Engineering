package Domain.Discount;

import Domain.Product;

import java.util.HashMap;
import java.util.List;

public class XorDiscount implements Discount {
    private List<Discount> discounts;

    public XorDiscount(List<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public void calculateDiscount(HashMap<Product, Integer> list) {
        for(Discount d:discounts){
            if(d.checkTerm(list)) {
                d.calculateDiscount(list);
                break;
            }
        }
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list) {
        int counter=0;
        for(Discount d:discounts){
            if(d.checkTerm(list))
                counter++;
        }
        return counter==1;
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
}
