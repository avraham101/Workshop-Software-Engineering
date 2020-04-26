package DataAPI;

import java.util.List;

public class DeliveryData {

    private String address;
    //TODO add country
    private List<ProductData> products;

    public DeliveryData(String address, List<ProductData> products) {
        this.address = address;
        this.products = products;
    }

    // ============================ getters & setters ============================ //

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) { this.address = address; }

    public List<ProductData> getProducts() {
        return products;
    }

    public void setProducts(List<ProductData> products) {
        this.products = products;
    }

    // ============================ getters & setters ============================ //
}
