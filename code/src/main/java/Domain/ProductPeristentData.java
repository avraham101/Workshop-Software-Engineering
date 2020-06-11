package Domain;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import DataAPI.ReviewData;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="product_for_purchase")
public class ProductPeristentData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="product")
    private String productName;

    @Column(name="category")
    private String category;

    @Column(name="store")
    private String store;

    @Column(name="amount")
    private int amount;

    @Column(name="price")
    private double price;

    @Column(name="purchaseType")
    private String purchaseType;

    public ProductPeristentData(ProductData p) {
        this.productName=p.getProductName();
        this.category=p.getCategory();
        this.amount=p.getAmount();
        this.price=p.getPrice();
        this.purchaseType=p.getPurchaseType().name();
        this.store=p.getStoreName();
    }

    public ProductPeristentData() {
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
