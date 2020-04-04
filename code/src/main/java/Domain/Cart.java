package Domain;

import DataAPI.PaymentData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Cart {
    private HashMap<String,Basket> baskets; // key is the store name and the value is the basket of the store

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

    /**
     * use case - 2.8 buy cart
     * the function buy this cart
     * @param paymentData - the payment info
     * @param addresToDeliver  - the address to shift
     * @return ture if the cart bought, otherwise false
     */
    public List<Purchase> buy(PaymentData paymentData, String addresToDeliver) {
        if(!availableCart(paymentData,addresToDeliver))
            return null;
        List<Purchase> recives = new LinkedList<>();
        for(Basket basket: baskets.values()) {
            Purchase purchase = basket.buy();
            if(purchase!=null) {
                recives.add(purchase);
                baskets.remove(basket.getStore().getName());
            }
        }
        return recives;
    }

    /**
     * use case 2.8 - but cart
     * the function check that all products is available
     * @return true is available, otherwise false
     */
    private boolean availableCart(PaymentData paymentData, String addresToDeliver) {
        for(Basket basket: baskets.values()) {
            if(!basket.available(paymentData, addresToDeliver))
                return false;
        }
        return true;
    }

}
