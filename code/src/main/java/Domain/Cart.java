package Domain;

import java.util.HashMap;

public class Cart {
    private HashMap<String,Basket> baskets;

    public Cart() {
        baskets=new HashMap<>();
    }

    public HashMap<String, Basket> getBaskets() {
        return baskets;
    }

    /**
     * return a basket
     * @param storeName - the store name of the basket
     * @return - the basket of the store, if not found then null
     */
    public Basket getBasket(String storeName) {
        for (Basket basket: this.getBaskets().values()) {
            if (basket.getStore().getName().equals(storeName)) {
                return basket;
            }
        }
        return null;
    }

    public void setBaskets(HashMap<String, Basket> baskets) {
        this.baskets = baskets;
    }
}
