package Domain;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.Purchase;
import Persitent.ProductInCartDao;
import Persitent.StoreDao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "basket")
public class Basket implements Serializable {

    @Transient
    private final ProductInCartDao productInCartDao;

    @Transient
    private final StoreDao storeDao;

    @Id
    @ManyToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="storename",referencedColumnName = "storename")
    private Store store; // the store of the basket

    @Id
    @Column(name = "username")
    private String buyer; // the buyer name

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @MapKey(name="productName")
    @JoinColumns({
            @JoinColumn(name="buyer", referencedColumnName = "username", updatable = false),
            @JoinColumn(name="storename", referencedColumnName = "storename", updatable = false)
    })
    private Map<String, ProductInCart> products; //key: Product Name value:Data

    @Column(name = "price")
    private double price;

    public Basket(){

        productInCartDao = new ProductInCartDao();
        storeDao = new StoreDao();
    }

    /**
     * constructor
     * @param store - the store of the basket
     */
    public Basket(Store store, String buyer) {
        this.store = store;
        this.buyer = buyer;
        this.products = new HashMap<>();
        this.price=0;
        productInCartDao = new ProductInCartDao();
        storeDao = new StoreDao();
    }

    /**
     * use case 2.7.2
     * delete product from basket
     * @param productName - the product to remove
     * @return - true if removed, false if not
     */
    public boolean deleteProduct(String productName) {
        store =storeDao.find(store.getName());
        boolean result = false;
        if (productName != null && this.products.get(productName)!=null) {
            ProductInCart key=new ProductInCart(buyer, store.getName(), productName);
            if (buyer==null||productInCartDao.remove(key)) {
                products.remove(productName);
                result = true;
            }
        }
        return result;
    }

    /**
     * use case 2.7.3
     * edit new amount of a product in the basket
     * @param productName - the product to change it amount
     * @param newAmount - the amount to change to
     * @return - true if succeeded, false if not
     */
    public boolean editAmount(String productName, int newAmount) {
        store =storeDao.find(store.getName());
        boolean result = false;
        ProductInCart productToEdit = this.products.get(productName);
        if (newAmount>0 && productToEdit != null) {
            productToEdit.setAmount(newAmount);
            if(buyer!=null){
                return productInCartDao.update(productToEdit);
            }
            result = true;
        }
        return result;
    }

    /**
     * use case 2.7.4
     * add product to the basket
     * @param product - the product to add
     * @param amount - the amount to add
     * @return - true if added, false if not
     */
    public boolean addProduct(Product product, int amount) {
        store =storeDao.find(store.getName());
        if(amount<0 || this.products.get(product.getName())!=null) {
            return false;
        }
        String productName = product.getName();
        String storeName = this.store.getName();
        ProductInCart productInCart = new ProductInCart(this.buyer, storeName, productName ,amount, product.getPrice());
        products.put(productName, productInCart);
        return true;
    }


    // ============================ getters & setters ============================ //

    public Store getStore() {
        return store;
    }

    public Map<String, ProductInCart> getProducts() {
        return products;
    }

    // ============================ getters & setters ============================ //


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Double.compare(basket.price, price) == 0 &&
                Objects.equals(store, basket.store) &&
                Objects.equals(buyer, basket.buyer) &&
                Objects.equals(products, basket.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(store, buyer, products, price);
    }

    /**
     * use case 2.8 - purchase cart
     * the function reserveCart the basket
     */
    public boolean reservedBasket() {
        return store.reserveProducts(this.products.values());
    }

    /**
     * use case 2.8 - cancel purchase cart
     * the function return all basket product to the store
     * assumption:  can't call this function with out calling reserveCart first.
     */
    public void cancel() {
        for(ProductInCart product: this.products.values()) {
            store.restoreAmount(product);
        }
    }

    /**
     * use case 2.8 - buy cart
     * the function updated Delivery Data and Payment Data
     * @param paymentData the payment data
     * @param deliveryData the delivery data
     */
    public boolean buy(PaymentData paymentData, DeliveryData deliveryData) {
        store =storeDao.find(store.getName());
        if(!store.policyCheck(paymentData,deliveryData.getCountry(),products))
            return false;
        List<ProductData> list = deliveryData.getProducts();
        //update delivery data
        for(ProductInCart productInCart: this.products.values()) {
            Product product = this.store.getProduct(productInCart.getProductName());
            String storeName = this.store.getName();
            ProductData productData = new ProductData(product, storeName);
            productData.setAmount(productInCart.getAmount());
            list.add(productData);
        }
        //set the price to be after discounts
        this.price=store.calculatePrice(this.products);
        double paymentPrice = paymentData.getTotalPrice();
        paymentData.setTotalPrice(paymentPrice+this.price);
        return true;
    }

    /**
     * use case 2.8 - buy cart
     * @param buyer - the name of the buyer
     * @return the purchase bought
     */
    public Purchase savePurchase(String buyer) {
        store =storeDao.find(store.getName());
        String storeName = this.store.getName();
        List<ProductData> list = new LinkedList<>();
        for(ProductInCart productInCart: this.products.values()) {
            Product productInStore = this.store.getProduct(productInCart.getProductName());
            ProductData productData = new ProductData(productInStore, this.store.getName());
            productData.setAmount(productInCart.getAmount());
            list.add(productData);
        }
        Purchase purchase = new Purchase(storeName,buyer,list);
        purchase.setPrice(this.price);
        store.savePurchase(purchase);
        return purchase;
    }
}



