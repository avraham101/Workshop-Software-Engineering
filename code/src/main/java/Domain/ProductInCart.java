package Domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="products_in_baskets")
public class ProductInCart implements Serializable {

    @Id
    @Column(name="buyer")
    private String buyer;

    @Id
    @Column(name="storename")
    private String storeName;

    @Id
    @Column(name="productname")
    private String productName;

    @Column(name="amount")
    private int amount;
    @Column(name="price")
    private double price;

    public ProductInCart(){ }

    public ProductInCart(String buyer, String storeName, String productName, int amount, double price) {
        this.buyer = buyer;
        this.storeName = storeName;
        this.productName = productName;
        this.amount = amount;
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getProductName() {
        return productName;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
