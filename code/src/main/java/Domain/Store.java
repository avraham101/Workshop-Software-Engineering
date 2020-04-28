package Domain;

import DataAPI.ProductData;
import Domain.Discount.Discount;
import Domain.PurchasePolicy.ComposePolicys.AndPolicy;
import Domain.PurchasePolicy.PurchasePolicy;
import DataAPI.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Store {

    private String name; //unique
    private String description;
    private PurchasePolicy purchasePolicy;
    private AtomicInteger discountCounter;
    private ConcurrentHashMap<Integer,Discount> discountPolicy;
    private ConcurrentHashMap<String, Product> products;
    private ConcurrentHashMap<String, Category> categoryList;
    private ConcurrentHashMap<Integer, Request> requests;
    private ConcurrentHashMap<String, Permission> permissions;
    private List<Purchase> purchases;

    public Store(String name,Permission permission,String description) {
        this.name = name;
        this.description=description;
        this.purchasePolicy = new AndPolicy(new ArrayList<>());
        discountCounter=new AtomicInteger(0);
        this.discountPolicy=new ConcurrentHashMap<>();
        this.permissions = new ConcurrentHashMap<>();
        this.permissions.put(permission.getOwner().getName(), permission);
        this.products=new ConcurrentHashMap<>();
        this.categoryList=new ConcurrentHashMap<>();
        this.requests= new ConcurrentHashMap<>();
        this.purchases = new LinkedList<>();
    }

    // ============================ getters & setters ============================ //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PurchasePolicy getPurchasePolicy() {
        return purchasePolicy;
    }

    public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
        this.purchasePolicy = purchasePolicy;
    }

    public ConcurrentHashMap<Integer, Discount> getDiscount() {
        return discountPolicy;
    }

    public ConcurrentHashMap<String, Product> getProducts() {
        return products;
    }

    public void setProducts(ConcurrentHashMap<String, Product> products) {
        this.products = products;
    }

    public ConcurrentHashMap<String, Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ConcurrentHashMap<String, Category> categoryList) {
        this.categoryList = categoryList;
    }

    public ConcurrentHashMap<Integer, Request> getRequests() {
        return requests;
    }

    public void setRequests(ConcurrentHashMap<Integer, Request> requests) {
        this.requests = requests;
    }

    public ConcurrentHashMap<String, Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(ConcurrentHashMap<String, Permission> permissions) {
        this.permissions = permissions;
    }

    public void setDiscount(ConcurrentHashMap<Integer, Discount> discounts) {
        this.discountPolicy = discounts;
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
     * use case 2.8 - purchase cart
     * the function calc the price of the products after discount
     * pre-condition: all products in list are available.
     * @param list - the list of products to reserveCart and there amount
     * @return
     */
    //TODO check this
    //public double getPriceForBasket(HashMap<Product, Integer> list) {
    //  return discount.stands(list);
    //}

    /**
     * use case 2.8 - reserveCart cart
     * @param otherProducts - the products to remove from store
     * @return true if succeeded, otherwise false.
     */
    public boolean reserveProducts(HashMap<Product, Integer> otherProducts) {
        HashMap<Product, Integer> productsReserved = new HashMap<>();
        boolean output = true;
        //TODO add here policy
        for(Product other: otherProducts.keySet()) {
            int amount = otherProducts.get(other);
            Product real = products.get(other.getName());
            if(real!=null) {
                real.getWriteLock().lock();
                if(amount<=real.getAmount()) {
                    productsReserved.put(real,amount);
                    real.setAmount(real.getAmount() - amount);
                    //TODO call this policy of the store
                    //TODO call to discount of the store
                    real.getWriteLock().unlock();
                }
                else {
                    output = false;
                    real.getWriteLock().unlock();
                    break;
                }
            }
            else {
                return false;
            }

        }
        if(!output) {
            restoreReservedProducts(productsReserved);
            return false;
        }
        return true;
    }

    /**
     * use case 2.8 -reserveCart cart
     * @param restores - the hashMap of reserved
     */
    private void restoreReservedProducts(HashMap<Product, Integer> restores) {
        for(Product other: restores.keySet()) {
            int amont = restores.get(other);
            restoreAmount(other,amont);
        }
    }

    /**
     * use case 2.8 -reserveCart cart
     * this function restore the amount of the product
     * @param other - the other product to return to the store
     * @param amount - the amount to reserve
     */
    public void restoreAmount(Product other, int amount) {
        Product real = products.get(other.getName());
        if(real!=null) {
            real.getWriteLock().lock();
            real.setAmount(real.getAmount() + amount);
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
        if(result)
            return new Response<>(true,OpCode.Success);
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
        discountPolicy.putIfAbsent(discountCounter.getAndIncrement(),discount);
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
}
