import java.util.ArrayList;
import java.util.List;

public class Category {

    private String name; //unique name
    private List<Product> products;

    public Category(String name,Product product) {
        this.name = name;
        products=new ArrayList<>();
        products.add(product);
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

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
