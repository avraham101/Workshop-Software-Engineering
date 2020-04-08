package AcceptanceTests.AcceptanceTestDataObjects;

import java.util.List;

public class ProductTestData {
    private String productName;
    private String storeName;
    private int amountInStore;
    private double price;
    private String category;
    private List<DiscountTestData> discounts;
    private List<ReviewTestData> reviews;

    public ProductTestData(String productName, String storeName, int amountInStore, double price, String category,
                           List<ReviewTestData> reviews, List<DiscountTestData> discounts) {
        this.productName = productName;
        this.storeName = storeName;
        this.amountInStore = amountInStore;
        this.price = price;
        this.category = category;
        this.reviews = reviews;
        this.discounts = discounts;
    }

    public List<DiscountTestData> getDiscounts() {
        return discounts;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductTestData that = (ProductTestData) o;

        if (productName != null ? !productName.equals(that.productName) : that.productName != null) return false;
        return storeName != null ? storeName.equals(that.storeName) : that.storeName == null;
    }

}
