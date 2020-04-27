package Domain.Discount.Term;

import Domain.Product;

import java.util.HashMap;

public class BaseTerm implements Term{
    private String product;
    private int amount;

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
}
