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

    public void setBaskets(HashMap<String, Basket> baskets) {
        this.baskets = baskets;
    }
}
