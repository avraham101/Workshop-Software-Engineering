package DataAPI;

public class ReviewData {
    String storeName;
    String productName;
    String content;

    public ReviewData(String storeName, String productName, String content) {
        this.storeName = storeName;
        this.productName = productName;
        this.content = content;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
