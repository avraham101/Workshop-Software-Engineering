import java.util.Date;

public class Purchase {
    private String storeName;
    private String productName;
    //TODO add more data off product
    private Date date;

    public Purchase(String storeName, String productName, Date date) {
        this.storeName = storeName;
        this.productName = productName;
        this.date = date;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
