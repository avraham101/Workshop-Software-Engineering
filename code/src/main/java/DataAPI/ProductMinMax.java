package DataAPI;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="product_min_max")
public class ProductMinMax implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="policy_id")
    private Integer policyId;
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_name")
    private String productName;
    @Column(name="max")
    private int max; // max amount of product
    @Column(name="min")
    private int min; // min amount of product

    public ProductMinMax(int max, int min) {
        this.max = max;
        this.min = min;
    }
    public ProductMinMax(String product, int max, int min) {
        this.productName=product;
        this.max = max;
        this.min = min;
    }

    public ProductMinMax() {
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    private String id;

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
