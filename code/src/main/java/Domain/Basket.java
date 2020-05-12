package Domain;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.Purchase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class Basket {

    private Store store; // the store of the basket
    private HashMap<Product, Integer> products; // key is the product and the value is the amount of the product in thr basket
    private double price;

    /**
     * constructor
     * @param store - the store of the basket
     */
    public Basket(Store store) {
        this.store = store;
        this.products = new HashMap<>();
        this.price=0;
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
     * use case 2.7.4
     * add product to the basket
     * @param product - the product to add
     * @param amount - the amount to add
     * @return - true if added, false if not
     */
    public boolean addProduct(Product product, int amount) {
        for(Product p:products.keySet()){
            if(p.getName().equals(product.getName()))
                return false;
        }
        products.put(product,amount);
        return true;
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

    public HashMap<Product, Integer> getProducts() {
        return products;
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
     * use case 2.8 - purchase cart
     * the function reserveCart the basket
     */
    public boolean reservedBasket() {
        return store.reserveProducts(products);
    }

    /**
     * use case 2.8 - cancel purchase cart
     * the function return all basket product to the store
     * assumption:  can't call this function with out calling reserveCart first.
     */
    public void cancel() {
        for(Product p: products.keySet()) {
            int amount = products.get(p);
            store.restoreAmount(p, amount);
        }
    }

    /**
     * use case 2.8 - buy cart
     * the function updated Delivery Data and Payment Data
     * @param paymentData the payment data
     * @param deliveryData the delivery data
     */
    public boolean buy(PaymentData paymentData, DeliveryData deliveryData) {
        if(!store.getPurchasePolicy().standInPolicy(paymentData,deliveryData.getCountry(),products))
            return false;
        double price = paymentData.getTotalPrice();
        List<ProductData> list = deliveryData.getProducts();
        //update delivery data
        for(Product p: products.keySet()) {
            p.setAmount(products.get(p));
            list.add(new ProductData(p, store.getName()));
        }
        //set the price to be after discounts
        this.price=store.calculatePrice(productsClone());
        paymentData.setTotalPrice(price+store.calculatePrice(productsClone()));
        return true;
    }

    private HashMap<Product, Integer> productsClone() {
        HashMap<Product,Integer> productAmountsClone=new HashMap<>();
        for(Product p:products.keySet())
            productAmountsClone.put(p.clone(),products.get(p));
        return productAmountsClone;
    }

    /**
     * use case 2.8 - buy cart
     * @param buyer - the name of the buyer
     * @return the purchase bought
     */
    public Purchase savePurchase(String buyer) {
        String storeName = this.store.getName();
        List<ProductData> list = new LinkedList<>();
        for(Product p:products.keySet()) {
            int amount =  products.get(p);
            ProductData temp = new ProductData(p,storeName);
            temp.setAmount(amount);
            list.add(temp);
        }
        Purchase purchase = new Purchase(storeName,buyer,list);
        purchase.setPrice(this.price);
        store.savePurchase(purchase);
        return purchase;
    }
}



