package Domain.Discount.Term;

import Domain.Product;

import java.util.HashMap;
import java.util.List;

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
}
