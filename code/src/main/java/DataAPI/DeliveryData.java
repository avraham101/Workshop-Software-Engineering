package DataAPI;

import java.util.List;

public class DeliveryData {

    private String address;
    private String country;
    private List<ProductData> products;

    public DeliveryData(String address, String country, List<ProductData> products) {
        this.address = address;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    // ============================ getters & setters ============================ //
}
