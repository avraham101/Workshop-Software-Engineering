package Domain;

import java.util.HashMap;
public class PurchasePolicy {

    /**
     * use case 2.8 - purchase cart
     * the function check if the policy stands on here terms
     * @param products - the product to purchase
     * @return true if the policy stants on , otherwise false
     */
    public boolean stands(HashMap<Product, Integer> products) {
        return true;
    }
}
