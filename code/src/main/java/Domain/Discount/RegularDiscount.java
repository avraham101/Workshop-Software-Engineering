package Domain.Discount;

import Domain.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="regular_discount")
public class RegularDiscount extends Discount {

    @Column(name="product")
    private String product;

    @Column(name="percantage")
    private double percantage;

    public RegularDiscount(String product, double percantage) {
        this.product = product;
        this.percantage = percantage;
    }

    public RegularDiscount() {
    }

    @Override
    public void calculateDiscount(HashMap<Product, Integer> list) {
        for(Product p:list.keySet())
            if(p.getName().equals(product))
                p.setPrice(p.getPrice() * (100-percantage) / 100);
            return;
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list) {
        for(Product p: list.keySet()){
            if(p.getName().equals(product))
                return true;
        }
        return false;
    }

    @Override
    public boolean isValid() {
        return product!=null&&percantage>0&&percantage<=100;
    }

    @Override
    public Set<String> getProducts() {
        Set<String> products=new HashSet<>();
        products.add(product);
        return products;
    }

    public String getProduct() {
        return product;
    }

    public double getPercantage() {
        return percantage;
    }
}
