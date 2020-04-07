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
