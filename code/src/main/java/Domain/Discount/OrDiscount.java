package Domain.Discount;

import Domain.Product;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="or_discount")
public class OrDiscount extends Discount{

    @OneToMany(cascade= CascadeType.ALL)
    @JoinTable(name="discounts_inside_discounts",
            joinColumns =@JoinColumn(name = "holder_id", referencedColumnName="id"),
            inverseJoinColumns={@JoinColumn(name="holdee_id", referencedColumnName="id")}
    )
    private List<Discount> discounts;

    public OrDiscount(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public OrDiscount() {
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
