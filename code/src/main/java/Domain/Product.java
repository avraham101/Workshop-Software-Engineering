package Domain;

import DataAPI.ProductData;
import DataAPI.PurchaseType;
import DataAPI.PurchaseTypeData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Product {
    private String name; //unique
    private AtomicInteger amount;
    private double price;
    private PurchaseType purchaseType;
    private Category category;
    private List<Review> reviews;
    private ReentrantReadWriteLock lock;

    public Product(ProductData productData, Category category) {
        this.name = productData.getProductName();
        this.purchaseType = createPurchaseType(productData.getPurchaseType());
        this.category = category;
        category.addProduct(this);
        this.amount=new AtomicInteger(productData.getAmount());
        this.price=productData.getPrice();
        this.reviews=new ArrayList<>();
        lock=new ReentrantReadWriteLock();
    }

    /**
     * only copy the primitives
     * @param other - the other product we copy
     */
    public Product(Product other) {
        this.name = other.name;
        this.amount = new AtomicInteger(other.getAmount());
        this.price = other.price;
        this.purchaseType = new PurchaseType();
        this.category = new Category(other.category.getName());
        this.reviews = new LinkedList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    /**
     * use case 4.1.3 - edit product
     * clone function for edit product
     * @param productData
     * @param category
     * parameters to get data from
     */
    public void edit(ProductData productData, Category category) {
        getWriteLock().lock();
        this.purchaseType = createPurchaseType(productData.getPurchaseType());
        category.removeProduct(name);
        this.category = category;
        category.addProduct(this);
        this.amount=new AtomicInteger(productData.getAmount());
        this.price=productData.getPrice();
        getWriteLock().unlock();
    }

    /**
     * @param purchaseType
     * @return
     */
    private PurchaseType createPurchaseType(PurchaseTypeData purchaseType) {
        if(purchaseType==PurchaseTypeData.IMMEDDIATE)
            return new PurchaseType();//immediate purchase type we have only one type
        return null;
    }

    // ============================ getters & setters ============================ //

    public String getName() {
        return name;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    public Category getCategory() { return category; }

    public List<Review> getReviews() {
        return reviews;
    }

    public int getAmount() {
        return amount.get();
    }

    public void setAmount(int amount) {
        if(amount>=0)
            this.amount.set(amount);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price>=0)
            this.price = price;
    }

    public ReentrantReadWriteLock.ReadLock getReadLock() {
        return lock.readLock();
    }

    public ReentrantReadWriteLock.WriteLock getWriteLock(){
        return lock.writeLock();
    }

    // ============================ getters & setters ============================ //

    public boolean equal(ProductData product) {
        return amount.get() == product.getAmount() &&
                product.getPrice()==price &&
                name.equals(product.getProductName()) &&
                category.getName().equals(product.getCategory());
    }

    public Product clone() {
        Product temp = new Product(this);
        return temp;
    }

    /**
     * use case 3.3 - write review
     * this function add the review to the product
     * @param review - the review
     */
    public void addReview(Review review) {
        getWriteLock().lock();
        this.reviews.add(review);
        getWriteLock().unlock();
    }

    /**
     * use case 3.3 - write review
     * this function add the review to the product
     * @param review - the review
     */
    public void removeReview(Review review) {
        getWriteLock().lock();
        this.reviews.remove(review);
        getWriteLock().lock();
    }
}
