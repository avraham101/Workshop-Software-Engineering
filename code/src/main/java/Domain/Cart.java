package Domain;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.Purchase;
import Persitent.PurchaseDao;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "cart")
public class Cart implements Serializable {

    @Id
    @Column(name = "username")
    private String buyer;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade = CascadeType.ALL)
    @MapKeyColumn(name="storename")
    @JoinColumn(name="username", referencedColumnName = "username", updatable = false)
    private Map<String,Basket> baskets; // key is the store name and the value is the basket of the store

    @Transient
    private final PurchaseDao purchasesDao;


    public Cart(){
        baskets = new HashMap<>();
        purchasesDao = new PurchaseDao();
    }

    public String getBuyer() {
        return buyer;
    }

    public Cart(String buyer){
        this.buyer = buyer;
        baskets = new HashMap<>();
        purchasesDao = new PurchaseDao();
    }

    public Map<String, Basket> getBaskets() {
        return baskets;
    }

    /**
     * the function return a basket
     * @param storeName - the store name of the basket
     * @return - the basket of the store, if not found then null
     */
    public Basket getBasket(String storeName) {
        for (Basket basket: this.getBaskets().values()) {
            if (basket.getStore().getName().compareTo(storeName)==0) {
                return basket;
            }
        }
        return null;
    }

    /**
     * use case 2.7 - add product
     * @param store - the store to add to cart
     * @param product - the product to add
     * @param amount - the amount of the prduct
     * @return true is succeed
     */
    public boolean addProduct(Store store, Product product, int amount) {
        Basket basket = baskets.get(store.getName());
        if (basket == null) {
            basket = new Basket(store,this.buyer);
            baskets.put(store.getName(),basket);
        }
        return basket.addProduct(product, amount);
    }

    /**
     * use case - 2.8 reserveCart cart
     * the function reserveCart this cart
     * @return ture if the cart bought, otherwise false
     */
    public boolean reserveCart() {
        //check if cart is not empty
        if(baskets.values().isEmpty()) {
            return false;
        }
        List<Basket> reservesBaskets = new LinkedList<>();
        for(Basket basket: baskets.values()) {
            if(!basket.reservedBasket()) {
                cancleReserve(reservesBaskets);
                return false;
            }
            reservesBaskets.add(basket);
        }
        return true;
    }

    /**
     * use case 2.8 reserveCart cart
     * @param reserved - all the baskets that reservied there items and need to return to the store
     */
    private void cancleReserve(List<Basket> reserved)  {
        for (Basket basket:reserved) {
            basket.cancel();
        }
    }

    /**
     * use case 2.8 - buy cart
     * the function updated Delivery Data and Payment Data
     * @param paymentData the payment data
     * @param deliveryData the delivery data
     */
    public boolean buy(PaymentData paymentData, DeliveryData deliveryData) {
        for(Basket b:baskets.values()){
            if(!b.buy(paymentData,deliveryData))
                return false;
        }
        return true;
    }

    /**
     * use case 2.8 - buy cart
     * the function cancel a cart
     */
    public void cancel() {
        for(Basket b:baskets.values()){
            b.cancel();
        }
    }

    /**
     * use case 2.8 -buy cart
     * the function create the purchase
     * @param buyer - the name of the buyer
     * @return list of purchase
     */
    public List<Purchase> savePurchases(String buyer) {
        List<Purchase> purchases = new LinkedList<>();
        for(Basket b:baskets.values()){
            Purchase p=b.savePurchase(buyer);
            if(purchasesDao.add(p))
                purchases.add(p);
        }
        return purchases;
    }
}
