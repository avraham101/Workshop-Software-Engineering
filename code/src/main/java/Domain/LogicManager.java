package Domain;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class LogicManager {
    private HashMap<String, Subscribe> users;
    private HashMap<String, Store> stores;
    private HashSystem hashSystem;
    private PaymentSystem paymentSystem;
    private SupplySystem supplySystem;
    private LoggerSystem loggerSystem;
    private User current;

    public LogicManager(HashMap<String, Subscribe> users, HashMap<String, Store> stores, User current) {
        this.users = users;
        this.stores = stores;
        this.current = current;
        try {
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
            paymentSystem = new ProxyPayment();
            supplySystem = new ProxySupply();
            /**
             * use case 1.1
             */
            if(!paymentSystem.connect()) {
                throw new Exception("Payment System Crashed");
            }
            if(!supplySystem.connect()) {
                throw new Exception("Supply System Crashed");
            }
        } catch (Exception e) {
            System.exit(1);
        }
    }

    public LogicManager() {
        users = new HashMap<>();
        stores = new HashMap<>();
        try {
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
            /**
             * use case 1.1
             */
            if(!paymentSystem.connect()) {
                throw new Exception("Payment System Crashed");
            }
            if(!supplySystem.connect()) {
                throw new Exception("Supply System Crashed");
            }
        } catch (Exception e) {
            System.exit(1);
        }
        current = new User();
    }

    /**
     * use case 2.2 - Register
     * @param userName - the user Name
     * @param password - the user password
     * @return true if the register complete, otherwise false
     */
    public boolean register(String userName, String password) {
        //TODO add to logger
        if(!validName(userName) || !validPassword(password)) {
            return false;
        }
        if(!users.containsKey(userName)){
            try {
                password = hashSystem.encrypt(password);
                Subscribe subscribe =null;
                if(users.isEmpty())
                    subscribe = new Admin(userName, password);
                else
                    subscribe = new Subscribe(userName, password);
                users.put(userName,subscribe);
                return true;
            } catch (NoSuchAlgorithmException e) {
                //TODO add to logger
            }
        }
        return false;
    }

    /***
     * use case 2.3 - Login
     * @param userName - the user Name
     * @param password - the user password
     * @return true if the user is logged to the system, otherwise false
     */
    public boolean login(String userName, String password) {
        //TODO add logger
        if(!validName(userName) || !validPassword(password)) {
            return false;
        }
        if (users.containsKey(userName)) {
            try {
                password = hashSystem.encrypt(password);
                Subscribe subscribe = users.get(userName);
                if (subscribe.getPassword().compareTo(password) == 0) {
                    return current.login(subscribe);
                }
            } catch (NoSuchAlgorithmException e) {
                //TODO add to logger
            }
        }
        return false;
    }

    /**
     * this function check valid name
     * @param name - the name to check
     * @return true valid name, otherwise false
     */
    private boolean validName(String name) {
        return name!=null && !name.isEmpty();
    }

    /**
     * this function check valid password
     * @param password - the password to check
     * @return true valid password, otherwise false
     */
    private boolean validPassword(String password) {
        return password!=null && !password.isEmpty();
    }

    /**
     * use case 3.1 - Logout
     * @return true if the user logout
     */
    public boolean logout() {
        //TODO add logger
        return current.logout();
    }

    /**
     * use case 3.2 - Open Store
     * @param storeDetails - the details of the the store
     * @return true if can open store, otherwise false.
     */
    public boolean openStore(StoreData storeDetails) {
        //TODO add logger
        if(!validStoreDetails(storeDetails))
            return false;
        if(stores.containsKey(storeDetails.getName()))
            return false;
        Store store = current.openStore(storeDetails,paymentSystem, supplySystem);
        if(store != null) {
            stores.put(store.getName(),store);
            return true;
        }
        return false;
    }

    /**
     * The fucntion check if storeData is valid
     * @param storeData - the store data to check
     * @return true the store data is ok, otherwise false
     */
    private boolean validStoreDetails(StoreData storeData) {
        return storeData!=null && storeData.getName() != null && storeData.getDiscountPolicy()!=null &&
                storeData.getPurchesPolicy()!=null;
    }

    /**
     * use case 4.1.1 add product to store
     * @param productData -the details of the product
     * @return true if the product was added, false otherwise
     */
    public boolean addProductToStore(ProductData productData) {
        //TODO logger
        if(productData==null)
            return false;
        if(!stores.containsKey(productData.getStoreName()))
            return false;
        if(validProduct(productData))
            return current.addProductToStore(productData);
        return false;
    }

    /**
     * check if the data of the product has valid content
     * @param productData data of the product to check
     * @return true if the details of the product are valid
     */

    private boolean validProduct(ProductData productData) {
        return productData.getProductName()!=null &&productData.getCategory()!=null&& validDiscounts(productData.getDiscount())
                &&productData.getPrice()>0&&productData.getAmount()>0&&productData.getPurchaseType()!=null;
    }

    /**
     * use 2.4.1 - show the details about every store
     * @return - details of all the stores data
     */
    public List<StoreData> viewStores() {
        List<StoreData> data = new LinkedList<>();
        for (String storeName: stores.keySet()) {
            Store store = stores.get(storeName);
            StoreData storeData = new StoreData(store.getName(),store.getPurchesPolicy(),
                                                store.getDiscount());
            data.add(storeData);
        }
        return data;
    }

    /**
     * use case 2.4.2 - show the products of a given store
     * @param storeName - the store that owns the products
     * @return - list of ProductData of the products in the store
     */
    public List<ProductData> viewProductsInStore(String storeName) {
        List<ProductData> data = new LinkedList<>();
        Store store = stores.get(storeName);
        Set<String> keys = store.getProducts().keySet();
        for (String key: keys) {
            Product product = store.getProducts().get(key);
            ProductData productData = new ProductData(product, storeName);
            data.add(productData);
        }
        return data;
    }

    /**
     * check if dicounts of product are valid
     * @param discounts of the product
     * @return if the discounts are not null and between 0 to 100
     */

    private boolean validDiscounts(List<Discount> discounts) {
        if(discounts==null)
            return false;
        for(Discount discount :discounts ){
            if(!validDiscount(discount))
                return false;
        }
        return true;
    }

    /**
     * check if dicounts of product are valid
     * @param discount of the product
     * @return if the discount is not null and between 0 to 100
     */

    private boolean validDiscount(Discount discount) {
        return discount!=null&&discount.getPercentage()>0&&discount.getPercentage()<100;
    }

    /**
     * remove a product from store if exist
     * @param storeName name of the store to remove the product from
     * @param productName name of product to be removed
     * @return if the product was removed
     */
    public boolean removeProductFromStore(String storeName, String productName) {
        //TODO Logger
        if(!stores.containsKey(storeName))
            return false;
        return current.removeProductFromStore(storeName,productName);
    }

    public boolean editProductFromStore(ProductData productData) {
        //TODO logger
        if(productData==null)
            return false;
        if(!stores.containsKey(productData.getStoreName()))
            return false;
        if(validProduct(productData))
            return current.editProductFromStore(productData);
        return false;
    }
}
