package Domain;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.time.LocalDateTime;
import java.util.*;

public class Store {

    private String name; //unique
    private PurchesPolicy purchesPolicy;
    private DiscountPolicy discount;
    private HashMap<String, Product> products;
    private HashMap<String, Category> categoryList;
    private List<Request> requests;
    private HashMap<String, Permission> permissions;
    private SupplySystem supplySystem;
    private PaymentSystem paymentSystem;
    private List<Purchase> purchases;

    public Store(String name, PurchesPolicy purchesPolicy, DiscountPolicy discount,
                 Permission permission, SupplySystem supplySystem,
                 PaymentSystem paymentSystem) {
        this.name = name;
        this.purchesPolicy = purchesPolicy;
        this.discount = discount;
        this.permissions = new HashMap<>();
        this.permissions.put(permission.getOwner().getName(), permission);
        this.supplySystem = supplySystem;
        this.paymentSystem = paymentSystem;
        this.products=new HashMap<>();
        this.categoryList=new HashMap<>();
        this.requests= new LinkedList<>();
        this.purchases = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PurchesPolicy getPurchesPolicy() {
        return purchesPolicy;
    }

    public void setPurchesPolicy(PurchesPolicy purchesPolicy) {
        this.purchesPolicy = purchesPolicy;
    }

    public DiscountPolicy getDiscount() {
        return discount;
    }

    public HashMap<String, Product> getProducts() {
        return products;
    }

    public void setProducts(HashMap<String, Product> products) {
        this.products = products;
    }

    public HashMap<String, Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(HashMap<String, Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public HashMap<String, Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(HashMap<String, Permission> permissions) {
        this.permissions = permissions;
    }

    public SupplySystem getSupplySystem() {
        return supplySystem;
    }

    public void setSupplySystem(SupplySystem supplySystem) {
        this.supplySystem = supplySystem;
    }

    public PaymentSystem getPaymentSystem() {
        return paymentSystem;
    }

    public void setPaymentSystem(PaymentSystem paymentSystem) {
        this.paymentSystem = paymentSystem;
    }

    public void setDiscount(DiscountPolicy discount) {
        this.discount = discount;
    }

    /**
     *
     * @param productData details of product to add to store
     * @return if the product was added successfully
     */
    public boolean addProduct(ProductData productData) {
        if(products.containsKey(productData.getProductName()))
            return false;
        String categoryName=productData.getCategory();
        if(!categoryList.containsKey(categoryName)){
            categoryList.put(categoryName,new Category(categoryName));
        }
        Product product=new Product(productData,categoryList.get(categoryName));
        products.put(productData.getProductName(),product);
        return true;
    }

    /**
     * remove product from the store
     * @param productName
     * @return  if the product had been removed
     */

    public boolean removeProduct(String productName) {
        if(!products.containsKey(productName))
            return false;
        products.get(productName).getCategory().removeProduct(productName);
        products.remove(productName);
        return true;
    }

    /**
     * edit product in the store
     * @param productData
     * @return if the product was edited successfully
     */
    public boolean editProduct(ProductData productData) {
        if(!products.containsKey(productData.getProductName()))
            return false;
        Product old=products.get(productData.getProductName());
        String categoryName=productData.getCategory();
        if(!categoryList.containsKey(categoryName)){
            categoryList.put(categoryName,new Category(categoryName));
        }
        old.edit(productData,categoryList.get(categoryName));
        return true;
    }

    public boolean addRequest(Request addRequest) {
        if(addRequest==null)
            return false;
        return requests.add(addRequest);
    }

    /**
     * use case 2.7.4 - add product to cart
     * use case 3.3 - write review
     * return a product of the store
     * @param productName - the name of the product
     * @return - thr product if exist, null if not
     */
    public Product getProduct(String productName) {
        return products.get(productName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return Objects.equals(name, store.name) &&
                Objects.equals(purchesPolicy, store.purchesPolicy) &&
                Objects.equals(discount, store.discount) &&
                Objects.equals(products, store.products) &&
                Objects.equals(categoryList, store.categoryList) &&
                Objects.equals(requests, store.requests) &&
                Objects.equals(permissions, store.permissions) &&
                Objects.equals(supplySystem, store.supplySystem) &&
                Objects.equals(paymentSystem, store.paymentSystem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, purchesPolicy, discount, products, categoryList, requests, permissions, supplySystem, paymentSystem);
    }

    /**
     * use case 3.3 write review
     * @param review - the review
     * @return ture if the review add to store, otherwise false;
     */
    public boolean addReview(Review review) {
        if(review==null)
            return false;
        Product p = products.get(review.getProductName());
        if(p==null)
            return false;
        p.addReview(review);
        return true;
    }

    /**
     * use case 2.8 - but cart
     * the function check if product available in the store
     * @param list - the products
     * @return true if the products is available, otherwise
     */
    public boolean isAvailableProducts(HashMap<Product, Integer> list) {
        for(Product product: list.keySet()) {
            int amount = list.get(product);
            Product real = products.get(product.getName());
            if(real==null)
                return false;
            if(real.getAmount()<amount)
                return false;
        }
        if(!purchesPolicy.stands(list))
            return false;
        return true;
    }

    /**
     * use case 2.8 - purchase cart
     * the function calc the price of the products after discount
     * pre-condition: all products in list are available.
     * @param list - the list of products to buy and there amount
     * @return
     */
    public double getPriceForBasket(HashMap<Product, Integer> list) {
        return discount.stands(list);
    }

    /**
     * use case 7 - payment system
     * use case 2.8 - purchase cart
     * the function check if the external system is connected
     * @return true if the external system is connected, otherwise false.
     */
    public boolean isAvailablePurchese() {
        return paymentSystem.isConnected();
    }

    /**
     * use case 8 - deliver system
     * use case 2.8 - purchase cart
     * the function check if the external system is connected
     * @return true if the external system is connected, otherwise false.
     */
    public boolean isAvailableDelivery() {
        return this.supplySystem.isConnected();
    }

    /**
     * use case 2.8 - purchase cart
     * the function check if the external system is connected
     * @return true if the external system is connected, otherwise false.
     */
    public Purchase purches(PaymentData paymentData, DeliveryData deliveryData) {
        if(!paymentSystem.pay(paymentData)) {
            return null;
        }
        if(!supplySystem.deliver(deliveryData)){
            paymentSystem.cancel(paymentData);
            return null;
        }
        reduceAmount(deliveryData.getProducts());
        return savePurchase(paymentData.getName(),deliveryData.getProducts());
    }
    /**
     * use case 2.8 - purchase cart
     * the function reduce products bought from store
     * reduce the amount of products that someone buy
     * @param productsToRemove - the products that someone buy
     */
    private void reduceAmount(List<ProductData> productsToRemove) {
        for (ProductData product: productsToRemove) {
            Product productInStore = this.products.get(product.getProductName());
            productInStore.setAmount(productInStore.getAmount() - product.getAmount());
        }
    }

    /**
     * use case 2.8 - purchase cart
     * the function save the
     * @param buyer - the name of the buyyer
     * @param products - the products
     * @return the Purchase that added
     */
    private Purchase savePurchase(String buyer, List<ProductData> products) {
        LocalDateTime date = LocalDateTime.now();
        Purchase purchase = new Purchase(name,buyer,products,date);
        this.purchases.add(purchase);
        return purchase;
    }

}
