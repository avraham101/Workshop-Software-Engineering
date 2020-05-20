package Domain.Discount.Term;

import Domain.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="base_term")
public class BaseTerm extends Term{

    @Column(name="product")
    private String product;

    @Column(name="amount")
    private int amount;

    public BaseTerm() {
    }

    public BaseTerm(String product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    /**
     * check the term of the product discount
     * @param list - product to buy and their amount
     * @param product - product of the result discount of term
     * @param amount - amount of product to get discount of
     * @return
     */
    @Override
    public boolean checkTerm(HashMap<Product, Integer> list, String product, int amount) {
        for(Product p: list.keySet()){
            if(p.getName().equals(this.product)) {
                if (this.product.equals(product))
                    return list.get(p)>=amount+this.amount;
                return list.get(p) >= this.amount;
            }
        }
        return false;
    }

    @Override
    public boolean isValid() {
        return product!=null&&amount>0;
    }

    @Override
    public Set<String> getProducts() {
        HashSet<String> products=new HashSet<>();
        products.add(product);
        return products;
    }
}
