package Domain.Discount.Term;

import Domain.Product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XorTerm implements Term{
    private List<Term> terms;

    public XorTerm(List<Term> terms) {
        this.terms = terms;
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list, String product, int amount) {
        int counter=0;
        for(Term t:terms){
            if(t.checkTerm(list,product,amount))
                counter++;
        }
        return counter==1;
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
