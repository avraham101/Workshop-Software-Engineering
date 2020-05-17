package Domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="category")
public class Category {

    @Id
    @Column(name="name")
    private String name; //unique name

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Product> products;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
        products=new ArrayList<>();
    }

    // ============================ getters & setters ============================ //

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    // ============================ getters & setters ============================ //

    /**
     * use case 4.1.1 - add product to store
     * the function add pro
     * @param product
     * @return
     */
    public boolean addProduct (Product product){
        return products.add(product);
    }

    /**
     * use case 4.1.2 - remove product from store
     * @param productName - the product name
     */
    public void removeProduct(String productName) {
        Product toRemove=null;
        for(Product p: products)
            if(p.getName().equals(productName))
                toRemove=p;
            products.remove(toRemove);
    }
}
