package Domain;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import Persitent.DaoHolders.ProductDaoHolder;
import Persitent.DaoInterfaces.IReviewDao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Entity
@Table(name="product")
public class Product implements Serializable {

    public Product() {
        daos = new ProductDaoHolder();
    }

    @Id
    @Column(name="storeName",nullable = false,updatable = false)
    private String store;

    @Id
    @Column(name="productName",nullable = false)
    private String name;

    @Column(name="amount",nullable = false)
    private int amount;

    @Column(name="price",nullable = false)
    private double price;

    @ManyToOne(cascade=CascadeType.ALL,fetch =FetchType.EAGER)
    @JoinColumn(name="purchaseType",referencedColumnName = "purchaseType")
    private PurchaseType purchaseType;

    @ManyToOne(cascade=CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(name="category_product",
            joinColumns ={@JoinColumn(name = "product", referencedColumnName="productName"),
                    @JoinColumn(name="store", referencedColumnName="storeName")},
                    inverseJoinColumns={@JoinColumn(name="category", referencedColumnName="name")}
    )
    private Category category;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumns({
                    @JoinColumn(name="store",referencedColumnName = "storeName"),
                    @JoinColumn(name="productName",referencedColumnName = "productName")
    })
    private List<Review> reviews;

    @Transient
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Transient
    private final ProductDaoHolder daos;

    public Product(ProductData productData, Category category) {
        this.name = productData.getProductName();
        this.purchaseType = createPurchaseType(productData.getPurchaseType());
        this.category = category;
        category.addProduct(this);
        this.amount=productData.getAmount();
        this.price=productData.getPrice();
        this.reviews=new ArrayList<>();
        this.store=productData.getStoreName();
        daos = new ProductDaoHolder();
    }

    public Product(String name, String store){
        this.name=name;
        this.store=store;
        daos = new ProductDaoHolder();
    }

    /**
     * only copy the primitives
     * @param other - the other product we copy
     */
    public Product(Product other) {
        this.name = other.name;
        this.amount = other.getAmount();
        this.price = other.price;
        this.purchaseType = new PurchaseType();
        this.category = new Category(other.category.getName());
        this.reviews = new LinkedList<>();
        this.store=other.getStore();
        daos = new ProductDaoHolder();
    }

    /**
     * use case 4.1.3 - edit product
     * clone function for edit product
     * @param productData
     * parameters to get data from
     */
    public void edit(ProductData productData) {
        getWriteLock().lock();
        this.amount=productData.getAmount();
        this.price=productData.getPrice();
        this.category = this.category.getName().equals(productData.getCategory()) ?
                category : new Category(productData.getCategory());
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

    public void setPurchaseType(PurchaseType purchaseType) {
        if(purchaseType!=null)
            this.purchaseType = purchaseType;
    }

    public Category getCategory() { return category; }

    public void setCategory(Category category) {
        if(category!=null)
            this.category = category;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public int getAmount() {
        return amount;
    }

    public synchronized void setAmount(int amount) {
        if(amount>=0)
            this.amount=amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if(price>=0)
            this.price = price;
    }

    public String getStore() {
        return store;
    }

    public ReentrantReadWriteLock.ReadLock getReadLock() {
        return lock.readLock();
    }

    public ReentrantReadWriteLock.WriteLock getWriteLock(){
        return lock.writeLock();
    }

    // ============================ getters & setters ============================ //

    public boolean equal(ProductData product) {
        return amount == product.getAmount() &&
                product.getPrice()==price &&
                name.equals(product.getProductName());
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
    public boolean addReview(Review review) {
        getWriteLock().lock();
        this.reviews.add(review);
        getWriteLock().unlock();

        IReviewDao reviewDao = daos.getReviewDao();
        return reviewDao.addReview(review);
    }

    /**
     * use case 3.3 - write review
     * this function add the review to the product
     * @param review - the review
     */
    public boolean removeReview(Review review) {
        getWriteLock().lock();
        this.reviews.remove(review);
        getWriteLock().lock();
        IReviewDao reviewDao = daos.getReviewDao();
       return reviewDao.remove(review.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return amount == product.amount &&
                Double.compare(product.price, price) == 0 &&
                Objects.equals(store, product.store) &&
                Objects.equals(name, product.name) &&
                Objects.equals(purchaseType, product.purchaseType) &&
                Objects.equals(category, product.category) &&
                Objects.equals(reviews, product.reviews);
    }

}
