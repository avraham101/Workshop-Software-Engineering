package DataAPI;

import Domain.Product;
import Domain.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductData {

    private String productName;
    private String storeName;
    private String category;
    private List<ReviewData> reviews;
    private int amount;
    private double price;
    private PurchaseTypeData purchaseType;

    public ProductData(String productName, String storeName, String category, List<Review> reviews, int amount, double price, PurchaseTypeData purchaseType) {
        this.productName = productName;
        this.storeName = storeName;
        this.category = category;
        this.reviews=new ArrayList<>();
        if(reviews!=null) {
            for (Review r : reviews)
                this.reviews.add(new ReviewData(r));
        }
        this.amount = amount;
        this.price = price;
        this.purchaseType = purchaseType;
    }

    public ProductData(Product product, String storeName) {
        this.productName = product.getName();
        this.storeName = storeName;
        this.category = product.getCategory().getName();
        this.reviews = new ArrayList<>();
        if(product.getReviews()!=null) {
            for (Review r : product.getReviews())
                this.reviews.add(new ReviewData(r));
        }
        this.price = product.getPrice();
        this.amount = product.getAmount();
        this.purchaseType = product.getPurchaseType().getPurchaseTypeData();
    }

    // ============================ getters & setters ============================ //

    public String getProductName() {
        return productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getCategory() {
        return category;
    }

    public List<ReviewData> getReviews() {
        return reviews;
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

    public PurchaseTypeData getPurchaseType() {
        return purchaseType;
    }


    // ============================ getters & setters ============================ //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductData that = (ProductData) o;
        return amount == that.amount &&
                Double.compare(that.price, price) == 0 &&
                productName.equals(that.productName) &&
                purchaseType.equals(that.purchaseType) &&
                storeName.equals(that.storeName) &&
                category.equals(that.category); //&&
                //Objects.equals(reviews, that.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, amount, price, purchaseType, storeName, category, reviews);
    }
}
