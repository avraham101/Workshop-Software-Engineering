package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.List;

public class ProductTestData {
    private String productName;
    private int amountInStore;
    private double price;
    private String category;
    private List<ReviewTestData> reviews;

    public ProductTestData(String productName, int amountInStore, double price, String category, List<ReviewTestData> reviews) {
        this.productName = productName;
        this.amountInStore = amountInStore;
        this.price = price;
        this.category = category;
        this.reviews = reviews;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAmountInStore() {
        return amountInStore;
    }

    public void setAmountInStore(int amountInStore) {
        this.amountInStore = amountInStore;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ReviewTestData> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewTestData> reviews) {
        this.reviews = reviews;
    }
}
