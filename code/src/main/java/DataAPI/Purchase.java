package DataAPI;

import DataAPI.ProductData;

import java.time.LocalDateTime;
import java.util.List;

public class Purchase {

    private String storeName;
    private String buyer;
    private List<ProductData> product;
    private LocalDateTime date;
    private double price;

    public Purchase(String storeName, String buyer, List<ProductData> product) {
        this.storeName = storeName;
        this.buyer = buyer;
        this.product = product;
        this.date = LocalDateTime.now();
        this.price=0;
    }

    // ============================ getters & setters ============================ //

    public String getStoreName() {
        return storeName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getBuyer() {
        return buyer;
    }

    public List<ProductData> getProduct() {
        return product;
    }

    public void setPrice(double price) {
        this.price=price;
    }

    public double getPrice() {
        return price;
    }
    // ============================ getters & setters ============================ //

}
