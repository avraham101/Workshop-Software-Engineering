package DataAPI;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="product_min_max")
public class ProductMinMax implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pr_min_max_id")
    private Integer id;

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

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer policyId) {
        this.id = policyId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
