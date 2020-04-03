package Domain;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.*;
import Systems.PaymentSystem.*;
import Systems.SupplySystem.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

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
        loggerSystem.getEventLogger().logp(Level.INFO, "Logic Manager", "register", "register: {0} {1}",
                new Object[] { userName, password });
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
        return productData.getProductName()!=null &&productData.getCategory()!=null
                && validDiscounts(productData.getDiscount()) && productData.getPrice()>0
                && productData.getAmount()>0 && productData.getPurchaseType()!=null;
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

    /**
     * edit product in store if exist
     * @param productData the product details to edit
     * @return if the product was updated successfully
     */

    /**
     * edit product in store if exist
     * @param productData the product details to edit
     * @return if the product was updated successfully
     */

    /**
     * use case 2.5 - Search product in store
     * @param filter - the filter chosen
     * @return - list of products after filer and sorter.
     */
    public List<ProductData> viewSpecificProducts(Filter filter) {
        if(!validFilter(filter))
            return new LinkedList<>();
        List<ProductData> productsData = new LinkedList<>();
        for(String storeName: stores.keySet()) {
            productsData.addAll(viewProductsInStore(storeName)); //use case 2.4.2
        }
        productsData = searchProducts(productsData,filter.getSearch(),filter.getValue());
        productsData = filterProducts(productsData,filter);
        return productsData;
    }

    /**
     * use case 2.5 - Search product
     * @param filter - the filter to check
     * @return true if filter is valid, otherwise false
     */
    private boolean validFilter(Filter filter) {
        return filter!=null && filter.getSearch()!=null && filter.getValue()!=null
                && filter.getMinPrice() >= 0  && filter.getMaxPrice()>=0 && filter.getCategory()!=null;
    }

    /**
     * use case 2.5 - Search product in store
     * pre-conditions: the products, search, value is Valid
     * @param search - the search chosen
     * @return - list of products after filer and sorter.
     */
    private List<ProductData> searchProducts(List<ProductData> products, Search search, String value) {
        if(search == Search.NONE) {
            return products;
        }
        List<ProductData> output = new LinkedList<>();
        int distance = 2;
        for(ProductData product:products) {
            switch (search) {
                case CATEGORY:
                    if(product.getCategory().compareTo(value)==0)
                        output.add(product);
                    break;
                case PRODUCT_NAME:
                    if(product.getProductName().compareTo(value)==0)
                        output.add(product);
                    break;
                case KEY_WORD:
                    String productName = product.getProductName();
                    if(Utils.editDistDP(productName,value,productName.length(),value.length())<= distance)
                        output.add(product);
                    break;
            }
        }
        return output;
    }

    /**
     * use case 2.5 - Search product in store
     * pre-conditions: the products, search, value is Valid
     * @param filter - the filter chosen
     * @return - list of products after filer and sorter.
     */
    private List<ProductData> filterProducts(List<ProductData> products, Filter filter) {
        List<ProductData> productData = new LinkedList<>();
        for(ProductData p: products) {
            //filter by min price
            if(filter.getMinPrice() <= p.getPrice()) {
                //filter by max price
                if(filter.getMaxPrice() >= p.getPrice()) {
                    //filter by category
                    if(filter.getCategory().isEmpty()) { //empty means dont filter by categroy
                        productData.add(p);
                    }
                    else if(filter.getCategory().compareTo(p.getCategory())==0) {
                        productData.add(p);
                    }
                }
            }
        }
        return productData;
    }

    /**
     * use case 4.1.3 - edit product
     * @param productData the product to be edited to
     * @return if the product was edited successfully
     */

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

    /**
     * use case 3.5 - write request on store
     * @param storeName name of store to write request to
     * @param content the content of the request
     * @return
     */

    public boolean addRequest(String storeName, String content) {

        Store dest = null;
        if (stores.containsKey(storeName))
            dest = stores.get(storeName);

        if (content!= null & dest != null) {
            Request request = current.addRequest(storeName, content);
            if (request == null)
                return false;
            else {
                dest.addRequest(request);
                return true;
            }
        }
        return false;
    }

    /**
     * use case 4.3
     * @param storeName the name of the store to be manager of
     * @param userName the user to be manager of the store
     * @return
     */
    public boolean manageOwner(String storeName, String userName) {
        return addManager(storeName,userName);
        //TODO
    }

    /**
     * use case 4.5 -add manager
     * @param storeName name of store to be manager of
     * @param userName
     * @return if the manager was added successfully
     */
    public boolean addManager(String userName, String storeName) {
        if(!users.containsKey(userName)||!stores.containsKey(storeName))
            return false;
        return current.addManager(users.get(userName),storeName);
    }

    /**
     * use case 4.9.1 -view Store Request
     * @param storeName name of store to view request.
     * @return if the current user is manager or owner of the store the list , else empty list.
     */
    public List<Request> viewStoreRequest(String storeName) {
        List<Request> requests = new LinkedList<>();
        if(stores.containsKey(storeName))
            requests = current.viewRequest(storeName);
        return requests;
    }

    /**
     * use case 4.9.2 -replay to Request
     * @param storeName
     * @param requestID
     * @param content
     * @return true if replay, false else
     */
    public Request replayRequest(String storeName, int requestID, String content) {
        if (content!=null && stores.containsKey(storeName))
            return (current.replayToRequest(storeName, requestID, content)) ;
        return null;
    }
}
