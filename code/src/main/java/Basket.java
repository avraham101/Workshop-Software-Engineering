import java.util.List;

public class Basket {
    private Store store;
    private List<Product> products;

    public Basket(Store store, List<Product> products) {
        this.store = store;
        this.products = products;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
