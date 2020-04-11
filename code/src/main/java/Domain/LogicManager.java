package Domain;

import DataAPI.*;
import Systems.*;
import Systems.PaymentSystem.*;
import Systems.SupplySystem.*;
import Utils.Utils;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class LogicManager {
    //TODO check all classes
    private HashMap<String, Subscribe> users;
    private HashMap<String, Store> stores;
    private HashSystem hashSystem;
    private PaymentSystem paymentSystem;
    private SupplySystem supplySystem;
    private LoggerSystem loggerSystem;
    private User current;

    public LogicManager(String userName, String password, HashMap<String, Subscribe> users, HashMap<String, Store> stores, User current) throws Exception {
        this.users = users;
        this.stores = stores;
        this.current = current;
        try {
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
            paymentSystem = new ProxyPayment();
            supplySystem = new ProxySupply();
            if(!paymentSystem.connect()) {
                throw new Exception("Payment System Crashed");
            }
            if(!supplySystem.connect()) {
                throw new Exception("Supply System Crashed");
            }
            if(!register(userName,password)) {
                throw new Exception("Admin Register Crashed");
            }
        } catch (Exception e) {
            throw new Exception("System crashed");
        }
    }

    /**
     * use case 1.1 - Init Trading System
     * @param userName - the user name
     * @param password - the user password
     * @throws Exception - system crashed exception
     */
    public LogicManager(String userName, String password) throws Exception {
        users = new HashMap<>();
        stores = new HashMap<>();
        try {
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
            paymentSystem = new ProxyPayment();
            supplySystem = new ProxySupply();
            if(!paymentSystem.connect()) {
                throw new Exception("Payment System Crashed");
            }
            if(!supplySystem.connect()) {
                throw new Exception("Supply System Crashed");
            }
            if(!register(userName,password)) {
                throw new Exception("Admin Register Crashed");
            }
        } catch (Exception e) {
            throw new Exception("System crashed");
        }
        current = new User();
    }

    /**
     * use case 2.2 - Register
     * @param userName - the user Name
     * @param password - the user password
     * @return true if the register complete,otherwise false
     */
    public boolean register(String userName, String password) {
        loggerSystem.writeEvent("LogicManager","register","the function register user",
                new Object[] {userName, password});
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
     * use 2.4.1 - show the details about every store
     * @return - details of all the stores data
     */
    public List<StoreData> viewStores() {
        //TODO add logger
        List<StoreData> data = new LinkedList<>();
        for (String storeName: stores.keySet()) {
            Store store = stores.get(storeName);
            StoreData storeData = new StoreData(store.getName(),store.getPurchasePolicy(),
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
        //TODO add logger
        List<ProductData> data = new LinkedList<>();
        Store store = stores.get(storeName);
        if(store!=null) {
            Set<String> keys = store.getProducts().keySet();
            for (String key : keys) {
                Product product = store.getProducts().get(key);
                ProductData productData = new ProductData(product, storeName);
                data.add(productData);
            }
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
     * use case 2.5 - Search product in store
     * @param filter - the filter chosen
     * @return - list of products after filer and sorter.
     */
    public List<ProductData> viewSpecificProducts(Filter filter) {
        //TODO add logger
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
     * use case 2.7.1 watch cart details
     * return the details about a cart
     * @return - the cart details
     */
    public CartData watchCartDetatils() {
        //TODO add logger
        return current.watchCartDetatils();
    }

    /**
     * use case 2.7.2
     * delete product from the cart
     * @param productName - the product to remove
     * @param storeName - the store that sale this product
     * @return - true if the delete work, false if not
     */
    public boolean deleteFromCart(String productName,String storeName){
        //TODO add logger
        return current.deleteFromCart(productName,storeName);
    }

    /**
     * use case 2.7.3 edit amount of product
     * @param productName - the product to edit it's amount
     * @param storeName - the store of the product
     * @param newAmount - the new amount
     * @return - true if succeeded, false if not
     */
    public boolean editProductInCart(String productName,String storeName,int newAmount) {
        //TODO add logger
        return current.editProductInCart(productName,storeName, newAmount);
    }

    /**
     * use case 2.7.4 - add product to the cart
     * @param productName - the product to add
     * @param storeName - the store of the product
     * @param amount - the amount of the product that need to add to the cart
     * @return - true if added, false if not
     */
    public boolean addProductToCart(String productName, String storeName, int amount) {
        //TODO add logger
        boolean result = false;
        Store store = stores.get(storeName);
        if (store != null) {
            Product product = store.getProduct(productName);
            if (product != null && amount > 0 && amount <= product.getAmount()) {
                result = current.addProductToCart(store, product, amount);
            }
        }
        return result;
    }

    /**
     * use case 2.8 - purchase cart
     * @param paymentData - the payment data of this purchase
     * @param addresToDeliver - the address do Deliver the purchase
     * @return true is the purchase succeeded, otherwise false
     */
    public boolean purchaseCart(PaymentData paymentData, String addresToDeliver) {
        //TODO add logger
        if (!validPaymentData(paymentData))
            return false;
        if (addresToDeliver == null || addresToDeliver.isEmpty())
            return false;
        return current.buyCart(paymentData, addresToDeliver);
    }

    /**
     * use case - 2.8
     * the function check if payment data is valid
     * @param paymentData - the payment data
     * @return true if the payment is valid, otherwise false
     */
    private boolean validPaymentData(PaymentData paymentData) {
        if(paymentData==null)
            return false;
        String address = paymentData.getAddress();
        String card = paymentData.getCreditCard();
        return paymentData.getName()!=null && !paymentData.getName().isEmpty() && address!=null && !address.isEmpty() && card!=null && !card.isEmpty();
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
                storeData.getPurchasePolicy()!=null;
    }


    /**
     * use case 3.3 - write review
     * @param productName - the product name
     * @param storeName - the store name
     * @param content - the content name
     * @return true if the review added, otherwise false.
     */
    public boolean addReview(String storeName,String productName, String content) {
        //TODO add logger
        if(!validReview(storeName,productName,content))
            return false;
        Store store = stores.get(storeName);
        if(store==null) {
            return false;
        }
        Review review = new Review(current.getUserName(),storeName,productName,content);
        boolean resultStore = store.addReview(review);
        boolean resultUser = current.addReview(review);
        if(!resultStore && !resultUser)
            return false;
        else if(!resultStore) {
            current.removeReview(review);
            return false;
        }
        else if(!resultUser) {
            store.removeReview(review);
            return false;
        }
        return true;
    }

    /**
     * use case 3.3 - write review
     * the function return if a valid correct
     * @param productName - the product name
     * @param storeName - the store name
     * @param content - the content name
     * @return true if the review is valid, otherwise false.
     */
    private boolean validReview(String storeName,String productName, String content) {
        return storeName!=null && productName!=null && content!=null &&
                !content.isEmpty();
    }


    /**
     * use case 3.5 - write request on store
     * @param storeName name of store to write request to
     * @param content the content of the request
     * @return
     */
    public boolean addRequest(String storeName, String content) {
        //TODO add logger
        if (content == null || !stores.containsKey(storeName)) return false;
        Store dest = stores.get(storeName);
        Request request = current.addRequest(storeName, content);
        if (request == null) return false;
        dest.addRequest(request);
        return true;
    }

    /**
     * use case 3.7 - watch purchase history
     * the function return the purchase list
     * @return the purchase list
     */
    public List<Purchase> watchMyPurchaseHistory() {
        //TODO add logger
        return current.watchMyPurchaseHistory();
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
     * use case 4.1.2: remove a product from store if exist
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
     * use case 4.3
     * @param storeName the name of the store to be manager of
     * @param userName the user to be manager of the store
     * @return
     */
    public boolean manageOwner(String storeName, String userName) {
        //TODO add logger
        if(!users.containsKey(userName)||!stores.containsKey(storeName))
            return false;
        addManager(userName,storeName);
        List<PermissionType> types=new ArrayList<>();
        types.add(PermissionType.OWNER);
        return current.addPermissions(types,storeName,userName);
    }

    /**
     * use case 4.5 -add manager
     * @param storeName name of store to be manager of
     * @param userName
     * @return if the manager was added successfully
     */
    public boolean addManager(String userName, String storeName) {
        //TODO add logger
        if(!users.containsKey(userName)||!stores.containsKey(storeName))
            return false;
        return current.addManager(users.get(userName),storeName);
    }

    /**
     * use case 4.6.1 - add permissions
     * @param permissions permmisions to add
     * @param storeName -the store to add permissions to
     * @param userName user to add permmisions to
     * @return
     */
    public boolean addPermissions(List<PermissionType> permissions, String storeName, String userName) {
        //TODO add logger
        if(!validList(permissions))
            return false;
        if (!users.containsKey(userName) || !stores.containsKey(storeName))
            return false;
        return current.addPermissions(permissions, storeName, userName);
    }

    //check if list is valid and contains no nulls

    private boolean validList(List<? extends Object> checkList) {
        if(checkList==null)
            return false;
        for(Object o : checkList)
            if(o==null)
                return false;
        return true;
    }

    /**
     * use case 4.6.2 - remove permission
     * @param permissions to be removed
     * @param storeName of the store to remove the permissions from
     * @param userName of the user to remove his permissions
     * @return if the permission were removed
     */

    public boolean removePermissions(List<PermissionType> permissions, String storeName, String userName) {
        //TODO add logger
        if(!validList(permissions))
            return false;
        if (!users.containsKey(userName) || !stores.containsKey(storeName))
            return false;
        return current.removePermissions(permissions, storeName, userName);
    }

    /**
     * use case 4.7 - remove manager
     * remove the manager and the managers he removed
     * @param userName of the user to be removed
     * @param storeName of the store to remove the manager from
     * @return if the manager was removed
     */
    public boolean removeManager(String userName, String storeName) {
        //TODO add logger
        if (!users.containsKey(userName) || !stores.containsKey(storeName))
            return false;
        return current.removeManager(userName,storeName);
    }

    /**
     * use case 4.9.1 -view Store Request
     * @param storeName name of store to view request.
     * @return if the current user is manager or owner of the store the list , else empty list.
     */
    public List<Request> viewStoreRequest(String storeName) {
        //TODO add logger
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
        //TODO add logger
        if (content!=null && stores.containsKey(storeName))
            return (current.replayToRequest(storeName, requestID, content)) ;
        return null;
    }

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     * @param userName - the user that own the purchases
     * @return - list of purchases that of the user
     */
    public List<Purchase> watchUserPurchasesHistory(String userName) {
        //TODO add logger
        if(!users.containsKey(userName))
            return null;
        if (current.canWatchUserHistory()) {
            Subscribe user = this.users.get(userName);
            return user.getPurchases();
        }
        return null;
    }

    /**
     * use case 6.4.2 - admin watch history purchases of some user
     * use case 4.10 - watch Store History by store owner
     * @param storeName - the name of the store that own the purchases
     * @return - list of purchases that of the store
     */
    public List<Purchase> watchStorePurchasesHistory(String storeName) {
        //TODO add logger
        if(!stores.containsKey(storeName))
            return null;
        if (current.canWatchStoreHistory(storeName)) {
            Store store = this.stores.get(storeName);
            return store.getPurchases();
        }
        return null;
    }

}
