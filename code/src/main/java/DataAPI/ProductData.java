package DataAPI;

import Domain.Discount;
import Domain.Product;
import Domain.Review;

import java.util.List;
import java.util.Objects;

public class ProductData {
    private String productName;
    private String storeName;
    private String category;
    private List<Review> reviews;
    private List<Discount> discount;
    private int amount;
    private double price;
    private double priceAfterDiscount;
    private PurchaseTypeData purchaseType;

    public ProductData(String productName, String storeName, String category, List<Review> reviews,
                       List<Discount> discount, int amount, double price, double priceAfterDiscount,
                       PurchaseTypeData purchaseType) {
        this.productName = productName;
        this.storeName = storeName;
        this.category = category;
        this.reviews = reviews;
        this.discount = discount;
        this.amount = amount;
        this.price = price;
        this.priceAfterDiscount = priceAfterDiscount;
        this.purchaseType = purchaseType;
    }

    public ProductData(String productName, String storeName, String category, List<Review> reviews,
                       List<Discount> discount, int amount, double price, PurchaseTypeData purchaseType) {
        this.productName = productName;
        this.storeName = storeName;
        this.category = category;
        this.reviews = reviews;
        this.discount = discount;
        this.price=price;
        this.priceAfterDiscount = this.price;
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
        this.priceAfterDiscount = product.getDiscountPrice();
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

    public double getPriceAfterDiscount() {
        return priceAfterDiscount;
    }

    public void setPriceAfterDiscount(double priceAfterDiscount) {
        this.priceAfterDiscount = priceAfterDiscount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductData that = (ProductData) o;
        return amount == that.amount &&
                Double.compare(that.price, price) == 0 &&
                Objects.equals(productName, that.productName) &&
                purchaseType == that.purchaseType &&
                Objects.equals(storeName, that.storeName) &&
                Objects.equals(category, that.category) &&
                Objects.equals(reviews, that.reviews) &&
                Objects.equals(discount, that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, amount, price, purchaseType, storeName, category, reviews, discount);
    }
}
