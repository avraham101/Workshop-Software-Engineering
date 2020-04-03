package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.HashMap;
import java.util.Map;

public class BasketTestData {
    private String storeName;
    private HashMap<ProductTestData,Integer> productsAndAmountInBasket;

    public BasketTestData(String storeName){
        this.storeName = storeName;
        this.productsAndAmountInBasket = new HashMap<>();
    }

    public void addProductToBasket(ProductTestData product, int amount){
        this.productsAndAmountInBasket.put(product,amount);
    }

    public void addProductsToBasket(HashMap<ProductTestData,Integer> productsAndAmounts){
        this.productsAndAmountInBasket.putAll(productsAndAmounts);
    }

    public String getStoreName() {
        return storeName;
    }

    public HashMap<ProductTestData,Integer> getProductsAndAmountInBasket() {
        return productsAndAmountInBasket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasketTestData that = (BasketTestData) o;

        if (storeName != null ? !storeName.equals(that.storeName) : that.storeName != null) return false;
        return productsAndAmountInBasket != null ? productsAndAmountInBasket.equals(that.productsAndAmountInBasket) : that.productsAndAmountInBasket == null;
    }

    public double getTotalAmount (){
        double amount=0;
        for (Map.Entry<ProductTestData,Integer> entry: productsAndAmountInBasket.entrySet()) {
            amount += entry.getKey().getPrice()*entry.getValue();
        }
        return amount;
    }
    public boolean isEmpty(){
        return productsAndAmountInBasket.isEmpty();
    }
}
