package Domain.Discount;

import Domain.Product;

import java.util.HashMap;

public class StoreDiscount implements Discount {
    private int minAmount;
    private double percentage;

    @Override
    public void calculateDiscount(HashMap<Product, Integer> list) {
        for(Product p:list.keySet())
            p.setPrice(p.getPrice()*percentage/100);
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list) {
        int sum=0;
        for(Product p:list.keySet())
            sum+=p.getAmount()*p.getPrice();
        return sum>=minAmount;
    }
}
