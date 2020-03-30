package Domain;

import DataAPI.ProductData;

import java.util.Date;

public class Purchase {
    private String storeName;
    private ProductData product;
    //TODO add more data off product
    private Date date;

    public Purchase(String storeName,ProductData product, Date date) {
        this.storeName = storeName;
        this.product = product;
        this.date = date;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public ProductData getProduct() {
        return product;
    }

    public void setProduct(ProductData product) {
        this.product = product;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
