package Domain.Discount.Term;

import Domain.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="term")
public abstract class Term implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    protected int id;

    public abstract boolean checkTerm(HashMap<Product, Integer> list, String product, int amount);

    public abstract boolean isValid();

    public abstract Set<String> getProducts();
}
