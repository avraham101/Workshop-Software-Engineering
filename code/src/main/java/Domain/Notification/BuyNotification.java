package Domain.Notification;

import DataAPI.OpCode;
import DataAPI.ProductData;
import Domain.ProductPeristentData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="buy_notification")
    public class BuyNotification extends Notification<List<ProductPeristentData>> {

    @OneToMany(cascade = CascadeType.ALL,fetch= FetchType.EAGER)
    @JoinTable(name="productdata_inside_notification",
            joinColumns =@JoinColumn(name = "id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="product_id", referencedColumnName="id")
    )
    private List<ProductPeristentData> product;

    public BuyNotification(List<ProductData> products, OpCode reason) {
        super(reason);
        this.product=new ArrayList<>();
        for(ProductData p: products)
            this.product.add(new ProductPeristentData(p));
    }

    public BuyNotification() {
    }

    @Override
    public List<ProductPeristentData> getValue() {
        return product;
    }

}
