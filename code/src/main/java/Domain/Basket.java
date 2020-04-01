package Domain;

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

    /**
     * delete product from basket
     * @param productName - the product to remove
     * @return - true if removed, false if not
     */
    public boolean deleteProduct(String productName) {
        boolean result = false;
        for (Product product: this.getProducts()) {
            if (product.getName().equals(productName)) {
                this.getProducts().remove(product);
                result = true;
            }
        }
        return result;
    }

    /**
     * edit new amount of a product in the basket
     * @param productName - the product to change it amount
     * @param newAmount - the amount to change to
     * @return - true if succeeded, false if not
     */
    public boolean editAmount(String productName, int newAmount) {
        boolean result = false;
        for (Product product: this.getProducts()) {
            if (product.getName().equals(productName)) {
                product.setAmount(newAmount);
                result = true;
            }
        }
        return result;
    }

}
