package Domain;

import java.util.HashMap;

public class Basket {
    private Store store;
    private HashMap<Product, Integer> products; // key is the product and the value is the amount of the product in thr basket

    /**
     * constructor
     * @param store - the store of the basket
     */
    public Basket(Store store) {
        this.store = store;
        this.products = new HashMap<>();
    }

    /**
     * getters and setters
     */
    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public HashMap<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(HashMap<Product, Integer> products) {
        this.products = products;
    }

    /**
     * delete product from basket
     * @param productName - the product to remove
     * @return - true if removed, false if not
     */
    public boolean deleteProduct(String productName) {
        boolean result = false;
        Product productToRemove = getProductByName(productName);
        if (productToRemove != null) {
            products.remove(productToRemove);
            result = true;
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
        Product productToEdit = getProductByName(productName);
        if (productToEdit != null) {
            this.products.replace(productToEdit, newAmount);
            result = true;
        }
        return result;
    }

    /**
     * add product to the basket
     * @param product - the product to add
     * @param amount - the amount to add
     * @return - true if added, false if not
     */
    public boolean addProduct(Product product, int amount) {
        if (products.containsKey(product)) {
            products.put(product, product.getAmount() + amount);
        }
        else {
            products.put(product,amount);
        }
        return true; // TODO - need to check policy in the the next version
    }

    /**
     * get product by it's name
     * @param productName - the name of the product
     * @return - the product with the name given
     */
    private Product getProductByName(String productName) {
        Product productToReturn = null;
        for (Product product : products.keySet()) {
            if (product.getName().equals(productName)) {
                productToReturn = product;
            }
        }
        return productToReturn;
    }
}
