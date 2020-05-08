package Domain;

import DataAPI.ProductData;

import java.time.LocalDateTime;
import java.util.List;

public class Purchase {

    private String storeName;
    private String buyer;
    private List<ProductData> product;
    private LocalDateTime date;

    public Purchase(String storeName, String buyer, List<ProductData> product) {
        this.storeName = storeName;
        this.buyer = buyer;
        this.product = product;
        this.date = LocalDateTime.now();
    }

    // ============================ getters & setters ============================ //

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public List<ProductData> getProduct() {
        return product;
    }

    public void setProduct(List<ProductData> product) {
        this.product = product;
    }

    // ============================ getters & setters ============================ //

}
