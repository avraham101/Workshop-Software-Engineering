package Domain;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String name; //unique name
    private List<Product> products;

    public Category(String name) {
        this.name = name;
        products=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public boolean addProduct (Product product){
        return products.add(product);
    }

    public void removeProduct(Product product) {
        products.remove(product);
    }
}
