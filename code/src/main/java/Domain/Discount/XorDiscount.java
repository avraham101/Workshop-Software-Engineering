package Domain.Discount;

import Domain.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="xor_discount")
public class XorDiscount extends Discount {

    @OneToMany(cascade=CascadeType.ALL)
    @JoinTable(name="discounts_inside_discounts",
            joinColumns =@JoinColumn(name = "holder_id", referencedColumnName="id"),
            inverseJoinColumns={@JoinColumn(name="holdee_id", referencedColumnName="id")}
    )
    private List<Discount> discounts;

    public XorDiscount(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public XorDiscount() {
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

    @Override
    public Set<String> getProducts() {
        Set<String> products=new HashSet<>();
        for(Discount d:discounts)
            products.addAll(d.getProducts());
        return products;
    }
}
