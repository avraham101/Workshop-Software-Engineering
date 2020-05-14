package DataAPI;

public class ProductIdData {
    String productName;
    String storeName;
    Integer amount;

    public ProductIdData(String productName, String storeName, Integer amount) {
        this.productName = productName;
        this.storeName = storeName;
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public Integer getAmount() {
        return amount;
    }
}
