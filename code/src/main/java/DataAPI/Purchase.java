package DataAPI;

import DataAPI.ProductData;
import Domain.ProductPeristentData;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="purchase")
public class Purchase implements Serializable {

    @Id
    @Column(name="storeName")
    private String storeName;

    @Id
    @Column(name="buyer")
    private String buyer;

    @OneToMany(cascade=CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="purchases_and_products",
            joinColumns ={@JoinColumn(name = "date", referencedColumnName="date"),
                    @JoinColumn(name = "buyer", referencedColumnName="buyer"),
                    @JoinColumn(name = "storeName", referencedColumnName="storeName")},
            inverseJoinColumns={@JoinColumn(name="id", referencedColumnName="id")}
    )
    private List<ProductPeristentData> product;

    @Id
    @Column(name="date")
    private LocalDateTime date;

    @Column(name="price")
    private double price;

    public Purchase(String storeName, String buyer, List<ProductData> product) {
        this.storeName = storeName;
        this.buyer = buyer;
        this.product=new ArrayList<>();
        for(ProductData p:product)
            this.product.add(new ProductPeristentData(p));
        this.date = LocalDateTime.now();
        this.price=0;
    }

    public Purchase() {
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

    public List<ProductPeristentData> getProduct() {
        return product;
    }

    public void setPrice(double price) {
        this.price=price;
    }

    public double getPrice() {
        return price;
    }
    // ============================ getters & setters ============================ //


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Double.compare(purchase.price, price) == 0 &&
                Objects.equals(storeName, purchase.storeName) &&
                Objects.equals(buyer, purchase.buyer) &&
                Objects.equals(date, purchase.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeName, buyer, date, price);
    }
}
