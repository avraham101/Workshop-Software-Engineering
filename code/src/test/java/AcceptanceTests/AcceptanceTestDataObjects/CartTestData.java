package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.*;

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

    public HashMap<ProductTestData,Integer> getProductsAndAmountsInCart(){
        HashMap<ProductTestData,Integer> productsAndAmount = new HashMap<>();

        for(BasketTestData basket : baskets)
            for(Map.Entry<ProductTestData,Integer> prodAndAmount : basket.getProductsAndAmountInBasket().entrySet())
                productsAndAmount.put(prodAndAmount.getKey(),prodAndAmount.getValue());

        return productsAndAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartTestData that = (CartTestData) o;

        HashSet<BasketTestData> basketsSet = new HashSet<BasketTestData>(baskets);
        HashSet<BasketTestData> thatBasketsSet = new HashSet<BasketTestData>(that.baskets);

        return baskets != null ? basketsSet.equals(thatBasketsSet) : that.baskets == null;
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

    public List<PurchaseTestData> makePurchasesTestData() {
        Date date = new Date();
        List<PurchaseTestData> purchases = new ArrayList<>();

        for(BasketTestData basketTestData : baskets)
                purchases.add(new PurchaseTestData(basketTestData.getProductsAndAmountInBasket(),date,basketTestData.getTotalAmount()));
        return purchases;
    }
}
