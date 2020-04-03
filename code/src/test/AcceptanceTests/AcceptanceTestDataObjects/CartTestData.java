package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.ArrayList;
import java.util.List;

public class CartTestData {
    private List<BasketTestData> baskets;

    public CartTestData(){
        this.baskets = new ArrayList<>();
    }

    public CartTestData(List<BasketTestData> baskets){
        this.baskets = baskets;
    }

    public void addBasketToCart(BasketTestData basket){
        this.baskets.add(basket);
    }

    public void addBasketsToCart(List<BasketTestData> baskets){
        this.baskets.addAll(baskets);
    }

    public List<BasketTestData> getBaskets() {
        return baskets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartTestData that = (CartTestData) o;

        return baskets != null ? baskets.equals(that.baskets) : that.baskets == null;
    }

    public BasketTestData getBasket(String storeName) {
        for (BasketTestData basket : baskets)
            if(basket.getStoreName().equals(storeName))
                return basket;
        return null;
    }
    public double getTotalAmount(){
        double amount=0;
        for (BasketTestData bd: baskets) {
            amount+=bd.getTotalAmount();
        }
        return amount;
    }

    public boolean isEmpty(){
        for (BasketTestData bd: baskets) {
            if(!bd.isEmpty())
                return false;

        }
        return true;
    }
}
