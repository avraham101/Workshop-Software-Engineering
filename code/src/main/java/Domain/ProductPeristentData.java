package Domain;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import DataAPI.ReviewData;

import java.util.List;

public class ProductPeristentData {

    private String productName;
    private String category;
    private String store;
    private int amount;
    private double price;
    private String purchaseType;


    public ProductPeristentData(ProductData p) {
        this.productName=p.getProductName();
        this.category=p.getCategory();
        this.amount=p.getAmount();
        this.price=p.getPrice();
        this.purchaseType=p.getPurchaseType().name();
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public String getPurchaseType() {
        return purchaseType;
    }

    public double getPrice() {
        return price;
    }

    public String getStore() {
        return store;
    }
}
