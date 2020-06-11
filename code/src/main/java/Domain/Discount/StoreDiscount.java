package Domain.Discount;

import Domain.Product;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="store_discount")
public class StoreDiscount extends Discount {

    @Column(name="min_amount")
    private int minAmount;

    @Column(name="percantage")
    private double percentage;

    public StoreDiscount(int minAmount, double percentage) {
        this.minAmount = minAmount;
        this.percentage = percentage;
    }

    public StoreDiscount() {
    }

    @Override
    public void calculateDiscount(HashMap<Product, Integer> list) {
        for(Product p:list.keySet())
            p.setPrice(p.getPrice()*(100-percentage)/100);
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list) {
        int sum=0;
        for(Product p:list.keySet())
            sum+=list.get(p)*p.getPrice();
        return sum>=minAmount;
    }

    @Override
    public boolean isValid() {
        return minAmount>0&&percentage>0&&percentage<100;
    }

    @Override
    public Set<String> getProducts() {
        return new HashSet<>();
    }
}
