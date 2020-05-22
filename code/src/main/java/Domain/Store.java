package Domain;

import DataAPI.*;
import Domain.Discount.Discount;
import Domain.Notification.BuyNotification;
import Domain.PurchasePolicy.ComposePolicys.AndPolicy;
import Domain.PurchasePolicy.PurchasePolicy;
import Domain.Notification.Notification;
import Persitent.DaoHolders.StoreDaoHolder;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Entity
@Table(name="store")
public class Store {

    @Id
    @Column(name="storename")
    private String name;

    @Column(name="description")
    private String description;

    @OneToOne(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="policy_in_store",
            joinColumns ={@JoinColumn(name = "store", referencedColumnName="storename")},
            inverseJoinColumns={@JoinColumn(name="pol_id", referencedColumnName="pol_id")}
    )
    private PurchasePolicy purchasePolicy;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @MapKey(name = "id")
    @JoinTable(name="discount_in_store",
            joinColumns ={@JoinColumn(name = "store", referencedColumnName="storename")},
            inverseJoinColumns={@JoinColumn(name="discount_id", referencedColumnName="id")}
    )
    private Map<Integer,Discount> discountPolicy;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @MapKey(name = "name")
    @JoinColumn(name="storeName",referencedColumnName = "storename",insertable=false,updatable = false)
    private Map<String, Product> products;

    @OneToMany(cascade=CascadeType.PERSIST,fetch = FetchType.EAGER)
    @MapKey(name = "name")
    @JoinTable(name="categories_in_store",
            joinColumns ={@JoinColumn(name = "store", referencedColumnName="storename")},
            inverseJoinColumns={@JoinColumn(name="category", referencedColumnName="name")}
    )
    private Map<String, Category> categoryList;



    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @MapKey(name = "id")
    @JoinColumn(name="store",referencedColumnName = "storeName",updatable = false)
    private Map<Integer, Request> requests;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @MapKeyColumn(name = "owner")
    @JoinColumn(name="store",referencedColumnName = "storeName",updatable = false)
    private Map<String, Permission> permissions;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="storeName",referencedColumnName = "storeName",updatable = false)
    private List<Purchase> purchases;

    @Transient
    private final StoreDaoHolder daos;

    public Store(String name,Permission permission,String description) {
        this.name = name;
        this.description=description;
        this.purchasePolicy = new AndPolicy(new ArrayList<>());
        this.discountPolicy=new ConcurrentHashMap<>();
        this.permissions = new ConcurrentHashMap<>();
        this.permissions.put(permission.getOwner().getName(), permission);
        this.products=new ConcurrentHashMap<>();
        this.categoryList=new ConcurrentHashMap<>();
        this.requests= new ConcurrentHashMap<>();
        this.purchases = new LinkedList<>();
        daos = new StoreDaoHolder();
    }

    public Store() {
        daos = new StoreDaoHolder();
    }

    // ============================ getters & setters ============================ //

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public PurchasePolicy getPurchasePolicy() {
        return purchasePolicy;
    }

    public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
        if(purchasePolicy!=null)
            this.purchasePolicy = purchasePolicy;
    }

    public Map<Integer, Discount> getDiscount() {
        return discountPolicy;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public Map<String, Category> getCategoryList() {
        return categoryList;
    }

    public Map<Integer, Request> getRequests() {
        return requests;
    }


    public Map<String, Permission> getPermissions() {
        return permissions;
    }

    //make permissions concurrent
    public void initPermissions(){
        this.requests=new ConcurrentHashMap<>(this.requests);
        this.categoryList=new ConcurrentHashMap<>(this.categoryList);
        this.products=new ConcurrentHashMap<>(products);
        this.discountPolicy=new ConcurrentHashMap<>(discountPolicy);
        if(!(this.permissions instanceof ConcurrentHashMap)) {
            this.permissions = new ConcurrentHashMap<>(this.permissions);
            for (Permission p : this.permissions.values()){
                p.getOwner().initPermissions();
                p.getStore().initPermissions();
            }
        }
    }

    /**
     * use case 4.10
     * @return
     */
    public List<Purchase> getPurchases() {
        List<Purchase> purchaseList;
        synchronized (purchases){
            purchaseList = new ArrayList<>(purchases);
        }
        return purchaseList;
    }

    // ============================ getters & setters ============================ //

    /**
     * use case 2.4.2 - show the products of a given store
     * @return the list of products in store.
     */
    public List<ProductData> viewProductInStore() {
        List<ProductData> data = new LinkedList<>();
        Set<String> keys = products.keySet();
        for (String key : keys) {
            Product product = products.get(key);
            //synchronized product from delete
            if(product!=null) {
                product.getReadLock().lock();
                ProductData productData = new ProductData(product, name);
                data.add(productData);
                product.getReadLock().unlock();
            }
        }
        return data;
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
                Objects.equals(purchasePolicy, store.purchasePolicy) &&
                Objects.equals(discountPolicy, store.discountPolicy) &&
                Objects.equals(products, store.products) &&
                Objects.equals(categoryList, store.categoryList) &&
                Objects.equals(requests, store.requests) &&
                Objects.equals(permissions, store.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, purchasePolicy, discountPolicy, products, categoryList, requests, permissions);
    }


    /**
     * use case 2.8 - calculate the price of a basket
     * @param list - the products to calc from basket
     * @return calculate price of the products after discounts
     */
    public double calculatePrice(Map<String, ProductInCart> list) {
        HashMap<Product, Integer> products = getSpecificProducts(list);
        double price=0;
        for(int discountId:discountPolicy.keySet()){
            Discount discount=discountPolicy.get(discountId);
            if (discount.checkTerm(products)){
                discount.calculateDiscount(products);
            }
        }
        for(Product p:products.keySet()){
            int amount =  products.get(p);
            price += amount * p.getPrice();
        }
        return price;

    }

    /**
     * use case 2.8 - reserveCart cart
     * @param otherProducts - the products to remove from store
     * @return true if succeeded, otherwise false.
     */
    public boolean reserveProducts(Collection<ProductInCart> otherProducts) {
        boolean output = true;
        List<ProductInCart> productsReserved = new LinkedList<>();
        for(ProductInCart productInCart: otherProducts) {
            Product real = this.products.get(productInCart.getProductName());
            if(real!=null) {
                int amount = productInCart.getAmount();
                real.getWriteLock().lock();
                if(amount<=real.getAmount()) {
                    productsReserved.add(productInCart);
                    real.setAmount(real.getAmount() - amount);
                    real.getWriteLock().unlock();
                }
                else {
                    output = false;
                    real.getWriteLock().unlock();
                    break;
                }
            }
            else {
               output = false;
            }
        }
        if(!output) {
            restoreReservedProducts(productsReserved);
        }
        return output;
    }

    /**
     * use case 2.8 -reserveCart cart
     * @param restores - the list of reserved
     */
    private void restoreReservedProducts(List<ProductInCart> restores) {
        for (ProductInCart product: restores) {
            restoreAmount(product);
        }
    }

    /**
     * use case 2.8 -reserveCart cart
     * this function restore the amount of the product
     * @param other - the other product to return to the store
     */
    public void restoreAmount(ProductInCart other) {
        Product real = products.get(other.getProductName());
        if(real!=null) {
            real.getWriteLock().lock();
            real.setAmount(real.getAmount() + other.getAmount());
            real.getWriteLock().unlock();
        }
    }

    /**
     * use case 2.8 - purchase cart
     * the function savePurchases the
     * @param purchase - The purchase to save
     * @return the Purchase that added
     */
    public void savePurchase(Purchase purchase) {
        synchronized (purchases) {
            this.purchases.add(purchase);
        }
    }

    /**
     * use case 2.8: Buy Cart
     * the function check the policy of the store
     * @param paymentData - the payment data
     * @param country - the country of the delivery
     * @param list - the products in the basket
     * @return true if succeed
     */
    public boolean policyCheck(PaymentData paymentData, String country, Map<String, ProductInCart> list) {
        HashMap<Product, Integer> hashMap = getSpecificProducts(list);
        return getPurchasePolicy().standInPolicy(paymentData,country,hashMap);
    }

    /**
     * use case 3.3 write review
     * @param review - the review
     * @return if the review added or not,
     */
    public boolean addReview(Review review) {
        Product p = products.get(review.getProductName());
        if(p==null) //Store as the product
            return false;
        p.addReview(review);
        return true;
    }

    /**
     * use case 3.3 wire review
     * the function remove review
     * @param review - the review to remove
     */
    public void removeReview(Review review) {
        Product p = products.get(review.getProductName());
        if(p!=null) {
            p.removeReview(review);
        }
    }

    /**
     * use case 3.5 - add request to store
     * @param addRequest - request to be added
     * @return
     */
    public synchronized boolean addRequest(Request addRequest) {
        if(addRequest==null)
            return false;
        requests.put(addRequest.getId(), addRequest);
        return true;
    }

    /**
     * use case 4.1.1 - add product to store
     * @param productData details of product to add to store
     * @return if the product was added successfully
     */
    public Response<Boolean> addProduct(ProductData productData) {
        String categoryName=productData.getCategory();
        if(categoryName==null)
            return new Response<>(false, OpCode.Invalid_Product);
        if(!categoryList.containsKey(categoryName)){
            categoryList.put(categoryName,new Category(categoryName));
        }
        Product product=new Product(productData,categoryList.get(categoryName));
        boolean result=products.putIfAbsent(productData.getProductName(),product)==null;
        if(result) {
            if(daos.getProductDao().addProduct(product))
                return new Response<>(true, OpCode.Success);
            else
                return new Response<>(false, OpCode.DB_Down);

        }
        return new Response<>(false,OpCode.Already_Exists);
    }

    /**
     * use case 4.1.2 - remove product from store
     * remove product from the store
     * @param productName
     * @return  if the product had been removed
     */
    public Response<Boolean> removeProduct(String productName) {
        Product product=products.get(productName);
        if(product!=null) {
            product.getWriteLock().lock();
            product.getCategory().removeProduct(productName);
            products.remove(productName);
            product.getWriteLock().unlock();
            return new Response<>(true,OpCode.Success);
        }
        return new Response<>(false,OpCode.Invalid_Product);
    }

    /**
     * use case 4.1.3 - edit product in store
     * edit product in the store
     * @param productData
     * @return if the product was edited successfully
     */
    public Response<Boolean> editProduct(ProductData productData) {
        Product old=products.get(productData.getProductName());
        if(old==null)
            return new Response<>(false,OpCode.Invalid_Product);
        String categoryName=productData.getCategory();
        if(!categoryList.containsKey(categoryName)){
            categoryList.put(categoryName,new Category(categoryName));
        }
        old.edit(productData,categoryList.get(categoryName));
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 4.2.1.1 - add discount to store
     * @param discount - discount to add to the store
     * @return
     */
    public Response<Boolean> addDiscount(Discount discount) {
        if(!checkProducts(discount))
            return new Response<>(false,OpCode.Invalid_Product);
        discountPolicy.putIfAbsent(discount.getId(),discount);
        return new Response<>(true,OpCode.Success);
    }

    private boolean checkProducts(Discount discount) {
        Set<String> productsInDiscount=discount.getProducts();
        for(String product:productsInDiscount)
            if(!this.products.containsKey(product))
                return false;
        return true;
    }

    /**
     * use case 4.2.1.2 - delete discount from store
     * @param discountId - discount to delete from the store
     * @return if the removing was successfull
     */
    public Response<Boolean> deleteDiscount(int discountId) {
        if(discountPolicy.remove(discountId)!=null)
            return new Response<>(true, OpCode.Success);
        return new Response<>(false,OpCode.Not_Found);
    }

    /**
     * use case 4.2.2 - update the store purchase policy
     * @param policy - the policy to update to
     * @return - true if updated, false if not
     */
    public Response<Boolean> addPolicy(PurchasePolicy policy) {
        if(!checkProducts(policy))
            return new Response<>(false,OpCode.Invalid_Product);
        this.purchasePolicy = policy;
        return new Response<>(true,OpCode.Success);
    }

    private boolean checkProducts(PurchasePolicy policy) {
        List<String> productsInPolicy = policy.getProducts();
        for(String product:productsInPolicy)
            if(!this.products.containsKey(product))
                return false;
        return true;
    }


    public void sendManagersNotifications(List<ProductData> productData) {
        for(String manager: permissions.keySet()){
            Notification notification=new BuyNotification(productData,OpCode.Buy_Product);
            permissions.get(manager).getOwner().sendNotification(notification);
        }
    }

    /**
     * get hash map of specific real products and their amount in a basket
     * @param list - hash map of products in cart
     * @return - real products and their amount in a basket
     */
    private HashMap<Product, Integer> getSpecificProducts(Map<String, ProductInCart> list) {
        HashMap<Product, Integer> output = new HashMap<>();
        for (ProductInCart product: list.values()) {
            Product realProduct = this.products.get(product.getProductName());
            if (realProduct == null)
                return null;
            int amount = product.getAmount();
            output.put(realProduct, amount);
        }
        return output;
    }
}
