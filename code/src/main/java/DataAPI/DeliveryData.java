package DataAPI;

import java.util.List;

public class DeliveryData {

    private String address;
    private String country;
    private List<ProductData> products;

    //new fields
    private String name;
    private Integer transactionId;
    private String city;
    private int zip;


    public DeliveryData(String address, String country, List<ProductData> products,String name,String city,int zip) {
        this.address = address;
        this.country = country;
        this.products = products;
        this.name=name;
        this.city=city;
        this.zip=zip;
    }

    // ============================ getters & setters ============================ //

    public String getAddress() {
        return address;
    }


    public List<ProductData> getProducts() {
        return products;
    }


    public String getCountry() {
        return country;
    }

    public String getName() {
        return name;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getCity() {
        return city;
    }

    public int getZip() {
        return zip;
    }

    // ============================ getters & setters ============================ //
}
