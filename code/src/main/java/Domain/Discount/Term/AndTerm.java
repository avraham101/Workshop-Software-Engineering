package Domain.Discount.Term;

import Domain.Product;

import java.util.HashMap;
import java.util.List;

public class AndTerm implements Term {
    private List<Term> terms;

    public AndTerm(List<Term> terms) {
        this.terms = terms;
    }

    @Override
    public boolean checkTerm(HashMap<Product, Integer> list, String product, int amount) {
        for(Term t:terms) {
            if (!t.checkTerm(list,product,amount))
                return false;
        }

        return true;
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
}
