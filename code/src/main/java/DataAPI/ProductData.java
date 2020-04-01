package DataAPI;

import Domain.Discount;
import Domain.Product;
import Domain.Review;

import java.util.List;

public class ProductData {
    private String productName;
    private int amount;
    private double price;
    private PurchaseTypeData purchaseType;
    private String storeName;
    private String category;
    private List<Review> reviews;
    private List<Discount> discount;

    public ProductData(String productName, String storeName, String category, List<Review> reviews, List<Discount> discount,int amount,double price,PurchaseTypeData purchaseType) {
        this.productName = productName;
        this.storeName = storeName;
        this.category = category;
        this.reviews = reviews;
        this.discount = discount;
        this.price=price;
        this.amount=amount;
        this.purchaseType=purchaseType;
    }

    public ProductData(Product product, String storeName) {
        this.productName = product.getName();
        this.storeName = storeName;
        this.category = product.getCategory().getName();
        this.reviews = product.getReviews();
        this.discount = product.getDiscount();
        this.price = product.getPrice();
        this.amount = product.getAmount();
        this.purchaseType = product.getPurchaseType().getPurchaseTypeData();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Discount> getDiscount() {
        return discount;
    }

    public void setDiscount(List<Discount> discount) {
        this.discount = discount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public PurchaseTypeData getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(PurchaseTypeData purchaseType) {
        this.purchaseType = purchaseType;
    }
}
