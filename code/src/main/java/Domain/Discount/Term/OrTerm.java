package Domain.Discount.Term;

import Domain.Product;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="or_term")
public class OrTerm extends Term{
    @OneToMany(cascade= CascadeType.ALL)
    @JoinTable(name="terms_inside_terms",
            joinColumns =@JoinColumn(name = "holder_id", referencedColumnName="id"),
            inverseJoinColumns={@JoinColumn(name="holdee_id", referencedColumnName="id")}
    )
    private List<Term> terms;


    public OrTerm(List<Term> terms) {
        this.terms = terms;
    }

    public OrTerm() {
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list, String product, int amount) {
        for(Term t:terms){
            if(t.checkTerm(list,product,amount))
                return true;
        }
        return false;
    }

    @Override
    public boolean isValid() {
        if(terms==null||terms.isEmpty())
            return false;
        for(Term t:terms)
            if(!t.isValid())
                return false;
        return true;
    }

    @Override
    public Set<String> getProducts() {
        Set<String> products=new HashSet<>();
        for(Term t:terms)
            products.addAll(t.getProducts());
        return products;
    }
}
