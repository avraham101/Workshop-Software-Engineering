package Domain.Discount;

import Domain.Discount.Term.Term;
import Domain.Product;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Set;

@Entity
@Table(name="product_term_discount")
public class ProductTermDiscount extends Discount {

    @OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="term_id",referencedColumnName = "id")
    private Term term;

    @Column(name="product")
    private String product;

    @Column(name="amount")
    private int amount;

    public ProductTermDiscount() {
    }

    public ProductTermDiscount(Term term, String product, int amount) {
        this.term = term;
        this.product = product;
        this.amount = amount;
    }

    @Override
    public void calculateDiscount(HashMap<Product, Integer> list) {
        for(Product p:list.keySet()){
            if(p.getName().equals(product))
                list.put(p,Math.max(list.get(p)-amount,0));
        }
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list) {
        boolean found=false;
        for(Product p:list.keySet())
            if(p.getName().equals(product)){
                if(list.get(p)>=amount)
                    found=true;
                else
                    break;
            }
        return found&&term.checkTerm(list,product,amount);
    }

    @Override
    public boolean isValid() {
        return term!=null&&term.isValid()&&product!=null&&amount>0;
    }

    @Override
    public Set<String> getProducts() {
        Set<String> producs=term.getProducts();
        return producs;
    }
}
