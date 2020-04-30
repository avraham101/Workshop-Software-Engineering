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

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
