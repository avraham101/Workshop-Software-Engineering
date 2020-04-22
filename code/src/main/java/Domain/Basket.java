package Domain;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class Basket {

    private Store store; // the store of the basket
    private HashMap<Product, Integer> products; // key is the product and the value is the amount of the product in thr basket
    private PaymentData paymentData;
    private DeliveryData deliveryData;

    /**
     * constructor
     * @param store - the store of the basket
     */
    public Basket(Store store) {
        this.store = store;
        this.products = new HashMap<>();
    }

    /**
     * use case 2.7.2
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
     * use case 2.7.3
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
     * use case 2.7.1
     * add product to the basket
     * @param product - the product to add
     * @param amount - the amount to add
     * @return - true if added, false if not
     */
    public boolean addProduct(Product product, int amount) {
        products.put(product,amount);
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

    // ============================ getters & setters ============================ //

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

    // ============================ getters & setters ============================ //

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Basket basket = (Basket) o;
        return Objects.equals(store, basket.store) &&
                Objects.equals(products, basket.products);
    }


    /**
     * check if the basket is available for buying
     * @param paymentData - the payment data
     * @param addresToDeliver - the address to deliver to
     * @return - true if available, false if not
     */
    public boolean available(PaymentData paymentData, String addresToDeliver) {
        //payment data updated
        double price = store.getPriceForBasket(products);
        paymentData = new PaymentData(paymentData.getName(),paymentData.getAddress(),paymentData.getCreditCard());
        paymentData.setTotalPrise(price);
        if(!store.isAvailableProducts(products))
            return false;
        //delivery data
        return isAvailableDelivery(addresToDeliver);
    }

    /**
     * use case 2.8 - purchase cart
     * the function check if the payment is available.
     * think of this like a receive for each purchase to store.
     * @param general - the general Payment Data
     * @return ture if the payment is available.
     */
    private boolean isAvailablePaymnet(PaymentData general) {
        double price = store.getPriceForBasket(products);
        paymentData = new PaymentData(general.getName(),general.getAddress(),general.getCreditCard());
        paymentData.setTotalPrise(price);
        return true;
    }

    /**
     * use case 2.8 - purchase cart
     * the function check if deliver is available.
     * think of this like a receive for each purchase to store.
     * @param genral - the general address
     * @return true if the deliver is available.
     */
    private boolean isAvailableDelivery(String genral) {
        List<ProductData> list = new LinkedList<>();
        for(Product p: products.keySet()) {
            ProductData productData = new ProductData(p,store.getName());
            productData.setAmount(products.get(p));
            list.add(productData);
        }
        deliveryData = new DeliveryData(genral,list);
        return true;
    }

    /**
     * use case 2.8 - purchase cart
     * the function buy the basket
     */
    public Purchase buy() {
        return store.purches(paymentData,deliveryData);
    }

}



