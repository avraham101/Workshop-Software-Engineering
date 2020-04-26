package Domain;

import java.util.HashMap;

public class DiscountPolicy {

    /**
     * use case 2.8 - purchase cart
     * the function check if the policy stands on here terms
     * @param list - the list of the products to purchase
     * @return the sum of the products
     */
    public double stands(HashMap<Product, Integer> list) {
        double sum =0;
        for(Product p:list.keySet()) {
            int amount = list.get(p);
            //sum += p.getDiscountPrice() * amount;
        }
        return sum;
    }
}
