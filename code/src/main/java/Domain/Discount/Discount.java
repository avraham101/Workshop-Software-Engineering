package Domain.Discount;

import Domain.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="discount")
public abstract class Discount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    protected Integer id;
    public abstract void calculateDiscount(HashMap<Product, Integer> list);
    public abstract boolean checkTerm(HashMap<Product, Integer> list);
    public abstract boolean isValid();
    public abstract Set<String> getProducts();

    public Integer getId() {
        return id;
    }
}
