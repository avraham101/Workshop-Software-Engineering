package Domain;

import DataAPI.*;
import Systems.*;
import Systems.PaymentSystem.*;
import Systems.SupplySystem.*;
import Utils.Utils;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LogicManager {
    //TODO check all classes
    private ConcurrentHashMap<String, Subscribe> subscribes;
    private ConcurrentHashMap<String, Store> stores;
    private ConcurrentHashMap<Integer,User> connectedUsers;
    private AtomicInteger usersIdCounter;
    private HashSystem hashSystem;
    private PaymentSystem paymentSystem;
    private SupplySystem supplySystem;
    private LoggerSystem loggerSystem;
    private AtomicInteger requestIdGenerator;
    private final Object openStoreLocker=new Object();

    /**
     * test constructor, mock systems
     * @param userName
     * @param password
     * @param subscribes
     * @param stores
     * @throws Exception
     */
    public LogicManager(String userName, String password, ConcurrentHashMap<String, Subscribe> subscribes, ConcurrentHashMap<String, Store> stores,
                        ConcurrentHashMap<Integer,User> connectedUsers,PaymentSystem paymentSystem,SupplySystem supplySystem) throws Exception {
        this.subscribes = subscribes;
        this.stores = stores;
        this.connectedUsers =connectedUsers;
        usersIdCounter=new AtomicInteger(0);
        requestIdGenerator = new AtomicInteger(0);
        try {
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
            this.paymentSystem = paymentSystem;
            this.supplySystem = supplySystem;
            //TODO add write to logger when exception
            if(!paymentSystem.connect()) {
                loggerSystem.writeError("Logic manager", "constructor",
                        "Fail connection to payment system",new Object[]{userName});
                throw new Exception("Payment System Crashed");
            }
            if(!supplySystem.connect()) {
                loggerSystem.writeError("Logic manager", "constructor",
                        "Fail connection to supply system",new Object[]{userName});
                throw new Exception("Supply System Crashed");
            }
            if(subscribes.isEmpty()&&!register(userName,password)) {
                loggerSystem.writeError("Logic manager", "constructor",
                        "Fail register",new Object[]{userName});
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
        subscribes = new ConcurrentHashMap<>();
        this.stores = new ConcurrentHashMap<>();
        usersIdCounter=new AtomicInteger(0);
        requestIdGenerator = new AtomicInteger(0);
        this.connectedUsers =new ConcurrentHashMap<>();
        try {
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
            loggerSystem.writeEvent("Logic Manager","constructor",
                    "Initialize the system", new Object[]{userName});
            paymentSystem = new ProxyPayment();
            supplySystem = new ProxySupply();
            if(!paymentSystem.connect()) {
                loggerSystem.writeError("Logic manager", "constructor",
                        "Fail connection to payment system",new Object[]{userName});
                throw new Exception("Payment System Crashed");
            }
            if(!supplySystem.connect()) {
                loggerSystem.writeError("Logic manager", "constructor",
                        "Fail connection to supply system",new Object[]{userName});
                throw new Exception("Supply System Crashed");
            }
            if(subscribes.isEmpty()&&!register(userName,password)) {
                loggerSystem.writeError("Logic manager", "constructor",
                        "Fail register",new Object[]{userName});
                throw new Exception("Admin Register Crashed");
            }
        } catch (Exception e) {
            if (loggerSystem != null){
                loggerSystem.writeError("Logic manager", "constructor",
                        "System crashed",new Object[]{userName});
            }
            throw new Exception("System crashed");
        }
    }

    /**
     * test constructor moc systems
     * @param userName
     * @param password
     * @param paymentSystem
     * @param supplySystem
     * @throws Exception
     */
    public LogicManager(String userName, String password, PaymentSystem paymentSystem, SupplySystem supplySystem) throws Exception {
        subscribes = new ConcurrentHashMap<>();
        stores = new ConcurrentHashMap<>();
        this.connectedUsers =new ConcurrentHashMap<>();
        usersIdCounter=new AtomicInteger(0);
        requestIdGenerator = new AtomicInteger(0);
        try {
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
            loggerSystem.writeEvent("Logic Manager","constructor",
                    "Initialize the system", new Object[]{userName});
            this.paymentSystem = paymentSystem;
            this.supplySystem = supplySystem;
            if(!this.paymentSystem.connect()) {
                loggerSystem.writeError("Logic manager", "constructor",
                        "Fail connection to payment system",new Object[]{userName});
                throw new Exception("Payment System Crashed");
            }
            if(!this.supplySystem.connect()) {
                loggerSystem.writeError("Logic manager", "constructor",
                        "Fail connection to supply system",new Object[]{userName});
                throw new Exception("Supply System Crashed");
            }
            if(subscribes.isEmpty()&&!register(userName,password)) {
                loggerSystem.writeError("Logic manager", "constructor",
                        "Fail register",new Object[]{userName});
                throw new Exception("Admin Register Crashed");
            }
        } catch (Exception e) {
            if (loggerSystem != null){
                loggerSystem.writeError("Logic manager", "constructor",
                        "System crashed",new Object[]{userName});
            }
            throw new Exception("System crashed");
        }
    }

    /**
     * hand shake for connecting to system
     * @return
     */
    public int connectToSystem() {
        int newId=usersIdCounter.getAndIncrement();
        connectedUsers.put(newId,new User());
        return newId;
    }

    /**
     * use case 2.2 - Register
     * @param userName - the user Name
     * @param password - the user password
     * @return true if the register complete,otherwise false
     */
    public boolean register(String userName, String password) {
        loggerSystem.writeEvent("LogicManager","register","the function register user",
                new Object[] {userName});
        if(!validName(userName) || !validPassword(password)) {
            return false;
        }
        Subscribe subscribe =null;
        try {
            password = hashSystem.encrypt(password);
        } catch (NoSuchAlgorithmException e) {
            loggerSystem.writeError("Logic manager", "register",
                    "Fail register the user",new Object[]{userName, password});
            return false;
        }

        if(this.subscribes.isEmpty())
            subscribe = new Admin(userName, password);
        else
            subscribe = new Subscribe(userName, password);
        return this.subscribes.putIfAbsent(userName,subscribe)==null;
    }

    /***
     * use case 2.3 - Login
     *
     * @param id
     * @param userName - the user Name
     * @param password - the user password
     * @return true if the user is logged to the system, otherwise false
     */
    public boolean login(int id, String userName, String password) {
        loggerSystem.writeEvent("LogicManager","login",
                "login a user", new Object[] {userName});
        if(!validName(userName) || !validPassword(password)) {
            return false;
        }
        Subscribe subscribe = this.subscribes.get(userName);
        User user= connectedUsers.get(id);
        if(subscribe!=null&&subscribe.getSessionNumber().compareAndSet(-1,id)){
            try {
                password = hashSystem.encrypt(password);
                if (subscribe.getPassword().compareTo(password) == 0) {
                    return user.login(subscribe);
                }
            } catch (NoSuchAlgorithmException e) {
                loggerSystem.writeError("Logic manager", "login",
                        "Fail to login the user",new Object[]{userName, password});
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
        loggerSystem.writeEvent("LogicManager","viewStores",
                "view the details of the stores in the system", new Object[] {});
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
        loggerSystem.writeEvent("LogicManager","viewProductsInStore",
                "view the details of the stores in the system", new Object[] {storeName});
        if(storeName==null)
            return null;
        Store store = stores.get(storeName);
        if(store!=null) {
            return store.viewProductInStore();
        }
        return null;
    }

    /**
     * check if discounts of product are valid
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
     * check if discounts of product are valid
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
        loggerSystem.writeEvent("LogicManager","viewSpecificProducts",
                "view products after a filter", new Object[] {filter});
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
        loggerSystem.writeEvent("LogicManager","searchProducts",
                "search products in store after filter and sorter", new Object[] {products, search, value});
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
        loggerSystem.writeEvent("LogicManager","filterProducts",
                "search products in store after filter", new Object[] {products, filter});
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
     * use case 2.7.1 - watch cart details
     * @return - the cart details
     * @param id
     */
    public CartData watchCartDetails(int id) {
        loggerSystem.writeEvent("LogicManager","watchCartDetails",
                "view the user cart data", new Object[] {});
        User current = connectedUsers.get(id);
        return current.watchCartDetatils();
    }

    /**
     * use case 2.7.2 - delete product from cart
     * delete product from the cart
     * @param productName - the product to remove
     * @param storeName - the store that sale this product
     * @return - true if the delete work, false if not
     */
    public boolean deleteFromCart(int id,String productName,String storeName){
        loggerSystem.writeEvent("LogicManager","deleteFromCart",
                "delete product from the user cart", new Object[] {productName, storeName});
        User current=connectedUsers.get(id);
        return current.deleteFromCart(productName,storeName);
    }

    /**
     * use case 2.7.3 - edit amount of product
     * @param productName - the product to edit it's amount
     * @param storeName - the store of the product
     * @param newAmount - the new amount
     * @return - true if succeeded, false if not
     */
    public boolean editProductInCart(int id,String productName,String storeName,int newAmount) {
        loggerSystem.writeEvent("LogicManager","editProductInCart",
                "edit the amount of a product in the cart", new Object[] {productName, storeName, newAmount});
        User current=connectedUsers.get(id);
        return current.editProductInCart(productName,storeName, newAmount);
    }

    /**
     * use case 2.7.4 - add product to the cart
     * @param productName - the product to add
     * @param storeName - the store of the product
     * @param amount - the amount of the product that need to add to the cart
     * @return - true if added, false if not
     */
    public boolean addProductToCart(int id,String productName, String storeName, int amount) {
        loggerSystem.writeEvent("LogicManager","addProductToCart",
                "add a product to the cart", new Object[] {productName, storeName, amount});
        boolean result = false;
        Store store = null;
        User current=connectedUsers.get(id);
        if (storeName != null)
            store = stores.get(storeName);
        if (store != null) {
            Product product = store.getProduct(productName);
            if (product != null && amount > 0 && amount <= product.getAmount()) {
                product = product.clone();
                result = current.addProductToCart(store, product, amount);
            }
        }
        return result;
    }

    /**
     * use case 2.8 - purchase cart
     * @param id - the id
     * @param paymentData - the payment data of this purchase
     * @param addresToDeliver - the address do Deliver the purchase
     * @return true is the purchase succeeded, otherwise false
     */
    public boolean purchaseCart(int id, PaymentData paymentData, String addresToDeliver) {
        loggerSystem.writeEvent("LogicManager","purchaseCart",
                "reserveCart the products in the cart", new Object[] {paymentData, addresToDeliver});
        //1) user get
        User current = connectedUsers.get(id);
        //2) validation check
        if (!validPaymentData(paymentData))
            return false;
        if (addresToDeliver == null || addresToDeliver.isEmpty())
            return false;
        //3) sumUp cart - updated PeymentData, DeliveryData
        boolean reserved = current.reservedCart();
        if(!reserved) {
            return false;
        }
        DeliveryData deliveryData = new DeliveryData(addresToDeliver, new LinkedList<>());
        current.buyCart(paymentData, deliveryData);
        //4) external systems
        boolean payedAndDelivered = externalSystemsBuy(id,paymentData,deliveryData);
        if(!payedAndDelivered) {
            return false;
        }
        //5) update the purchase for both store and user (synchronized)
        current.savePurchase(paymentData.getName());
        return true;
    }

    /**
     * use case 2.8 - purchase cart
     * the function check if payment data is valid
     * @param paymentData - the payment data
     * @return true if the payment is valid, otherwise false
     */
    private boolean validPaymentData(PaymentData paymentData) {
        if(paymentData==null)
            return false;
        String name = paymentData.getName();
        String address = paymentData.getAddress();
        String card = paymentData.getCreditCard();
        return name!=null && !name.isEmpty() && address!=null && !address.isEmpty() && card!=null && !card.isEmpty();
    }

    /**
     * use case 2.8 - buy cart from external systems
     * @param id - the id of user
     * @param paymentData - the payment data
     * @param deliveryData - the delivery data
     * @return true if worked, otherwise false.
     */
    private boolean externalSystemsBuy(int id, PaymentData paymentData, DeliveryData deliveryData) {
        User current = connectedUsers.get(id);
        if(!paymentSystem.pay(paymentData)) {
            loggerSystem.writeError("Logic Manger","purchaseCart","Payment System Crashed",
                    new Object[] {id});
            current.cancelCart();
            return false;
        }
        if(!supplySystem.deliver(deliveryData)) {
            loggerSystem.writeError("Logic Manger","purchaseCart","Delivery System Crashed",
                    new Object[] {id});
            if(!paymentSystem.cancel(paymentData)) {
                loggerSystem.writeError("Logic Manger","purchaseCart",
                        "Payment System Crashed", new Object[] {id});
            }
            current.cancelCart();
            return false;
        }
        return true;
    }

    /**
     * use case 3.1 - Logout
     * @return true if the user logout
     * @param id
     */
    public boolean logout(int id) {
        loggerSystem.writeEvent("LogicManager","logout",
                "a user logout from the system", new Object[] {});
        User current=connectedUsers.get(id);
        return current.logout();
    }

    /**
     * use case 3.2 - Open Store
     * @param id - the number of the session that is connected
     * @param storeDetails - the details of the the store
     * @return true if can open store, otherwise false.
     */
    public boolean openStore(int id, StoreData storeDetails) {
        loggerSystem.writeEvent("LogicManager","openStore",
                "open new store", new Object[] {storeDetails});
        if(!validStoreDetails(storeDetails))
            return false;
        User current=connectedUsers.get(id);
        //prevent making two stores with the same name
        synchronized (openStoreLocker) {
            if (stores.containsKey(storeDetails.getName()))
                return false;
            Store store = current.openStore(storeDetails);
            if(store != null) {
                stores.put(store.getName(),store);
                return true;
            }
        }
        return false;
    }

    /**
     * The function check if storeData is valid
     * @param storeData - the store data to check
     * @return true the store data is ok, otherwise false
     */
    private boolean validStoreDetails(StoreData storeData) {
        return storeData!=null && storeData.getName() != null && storeData.getDiscountPolicy()!=null &&
                storeData.getPurchasePolicy()!=null;
    }


    /**
     * use case 3.3 - write review
     * @param id
     * @param storeName - the store name
     * @param productName - the product name
     * @param content - the content name
     * @return true if the review added, otherwise false.
     */
    public boolean addReview(int id, String storeName, String productName, String content) {
        loggerSystem.writeEvent("LogicManager","addReview",
                "add a review for the product", new Object[] {storeName, productName, content});
        if(!validReview(storeName,productName,content))
            return false;
        User current=connectedUsers.get(id);
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
     * @return true if succeeded to add request to store
     */
    public boolean addRequest(int id,String storeName, String content) {
        loggerSystem.writeEvent("LogicManager","addRequest",
                "add a request to the store", new Object[] {storeName, content});
        if (storeName == null || content == null || !stores.containsKey(storeName))
            return false;
        Store dest = stores.get(storeName);
        User current = connectedUsers.get(id);
        int requestId = requestIdGenerator.incrementAndGet(); // generate request number sync
        Request request = current.addRequest(requestId, storeName, content);
        if (request == null) {
            return false;
        }
        dest.addRequest(request);
        return true;
    }

    /**
     * use case 3.7 - watch purchase history
     * the function return the purchase list
     * @return the purchase list
     */
    public List<Purchase> watchMyPurchaseHistory(int id) {
        loggerSystem.writeEvent("LogicManager","watchMyPurchaseHistory",
                "user view his purchase history", new Object[] {});
        User current=connectedUsers.get(id);
        return current.watchMyPurchaseHistory();
    }

    /**
     * use case 4.1.1 - add product to store
     * @param productData -the details of the product
     * @return true if the product was added, false otherwise
     */
    public boolean addProductToStore(int id,ProductData productData) {
        loggerSystem.writeEvent("LogicManager","addProductToStore",
                "add a product to store", new Object[] {productData});
        User current=connectedUsers.get(id);
        if(productData==null)
            return false;
        if(!validProduct(productData))
            return false;
        if(stores.containsKey(productData.getStoreName()))
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
     * use case 4.1.2 - remove a product from store if exist
     * @param storeName name of the store to remove the product from
     * @param productName name of product to be removed
     * @return if the product was removed
     */
    public boolean removeProductFromStore(int id,String storeName, String productName) {
        loggerSystem.writeEvent("LogicManager","addProductToStore",
                "remove a product to store", new Object[] {storeName, productName});
        User current=connectedUsers.get(id);
        if(!stores.containsKey(storeName))
            return false;
        return current.removeProductFromStore(storeName,productName);
    }

    /**
     * use case 4.1.3 - edit product
     * @param productData the product to be edited to
     * @return if the product was edited successfully
     */
    public boolean editProductFromStore(int id,ProductData productData) {
        loggerSystem.writeEvent("LogicManager","editProductFromStore",
                "edit the product amount in the store", new Object[] {productData});
        User current=connectedUsers.get(id);
        if(productData==null)
            return false;
        if(!stores.containsKey(productData.getStoreName()))
            return false;
        if(validProduct(productData))
            return current.editProductFromStore(productData);
        return false;
    }

    /**
     * use case 4.3 - manage owner
     * @param storeName the name of the store to be manager of
     * @param userName the user to be manager of the store
     * @return
     */
    public boolean manageOwner(int id,String storeName, String userName) {
        loggerSystem.writeEvent("LogicManager","manageOwner",
                "store owner add a owner to the store", new Object[] {storeName, userName});
        if(!subscribes.containsKey(userName)||!stores.containsKey(storeName))
            return false;
        User current=connectedUsers.get(id);
        addManager(id,userName,storeName);
        List<PermissionType> types=new ArrayList<>();
        types.add(PermissionType.OWNER);
        return current.addPermissions(types,storeName,userName);
    }

    /**
     * use case 4.5 - add manager
     * @param storeName name of store to be manager of
     * @param userName
     * @return if the manager was added successfully
     */
    public boolean addManager(int id,String userName, String storeName) {
        loggerSystem.writeEvent("LogicManager","addManager",
                "store owner add a manager to the store", new Object[] {storeName, userName});
        if(!subscribes.containsKey(userName)||!stores.containsKey(storeName))
            return false;
        User current=connectedUsers.get(id);
        return current.addManager(subscribes.get(userName),storeName);
    }

    /**
     * use case 4.6.1 - add permissions
     * @param permissions permissions to add
     * @param storeName -the store to add permissions to
     * @param userName user to add permissions to
     * @return
     */
    public boolean addPermissions(int id,List<PermissionType> permissions, String storeName, String userName) {
        loggerSystem.writeEvent("LogicManager","addPermissions",
                "store owner add a manager's permissions", new Object[] {permissions, storeName, userName});
        if(!validList(permissions))
            return false;
        if (!subscribes.containsKey(userName) || !stores.containsKey(storeName))
            return false;
        User current=connectedUsers.get(id);
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
    public boolean removePermissions(int id,List<PermissionType> permissions, String storeName, String userName) {
        loggerSystem.writeEvent("LogicManager","removePermissions",
                "store owner remove manager's permission", new Object[] {permissions, storeName, userName});
        if(!validList(permissions))
            return false;
        if (!subscribes.containsKey(userName) || !stores.containsKey(storeName))
            return false;
        User current=connectedUsers.get(id);
        return current.removePermissions(permissions, storeName, userName);
    }

    /**
     * use case 4.7 - remove manager
     * remove the manager and the managers he removed
     * @param userName of the user to be removed
     * @param storeName of the store to remove the manager from
     * @return if the manager was removed
     */
    public boolean removeManager(int id,String userName, String storeName) {
        loggerSystem.writeEvent("LogicManager","removeManager",
                "store owner remove manager", new Object[] {storeName, userName});
        if (!subscribes.containsKey(userName) || !stores.containsKey(storeName))
            return false;
        User current=connectedUsers.get(id);
        return current.removeManager(userName,storeName);
    }

    /**
     * use case 4.9.1 - view Store Request
     *
     * @param id
     * @param storeName name of store to view request.
     * @return if the current user is manager or owner of the store the list , else empty list.
     */
    public List<Request> viewStoreRequest(int id, String storeName) {
        loggerSystem.writeEvent("LogicManager","viewStoreRequest",
                "store owner view the requests of the store", new Object[] {storeName});
        User current=connectedUsers.get(id);
        List<Request> requests = new LinkedList<>();
        if(storeName != null && stores.containsKey(storeName))
            requests = current.viewRequest(storeName);
        return requests;
    }

    /**
     * use case 4.9.2 - replay to Request
     * @param id
     * @param storeName
     * @param requestID
     * @param content
     * @return true if replay, false else
     */
    public Request replayRequest(int id, String storeName, int requestID, String content) {
        loggerSystem.writeEvent("LogicManager","viewStoreRequest",
                "store owner view the requests of the store", new Object[] {storeName});
        User current=connectedUsers.get(id);
        if (storeName!=null && stores.containsKey(storeName))
            return (current.replayToRequest(storeName, requestID, content)) ;
        return null;
    }

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     * @param id
     * @param userName - the user that own the purchases
     * @return - list of purchases that of the user
     */
    public List<Purchase> watchUserPurchasesHistory(int id, String userName) {
        loggerSystem.writeEvent("LogicManager","watchUserPurchasesHistory",
                "admin watch a user purchase history", new Object[] {userName});
        User current=connectedUsers.get(id);
        Subscribe sub = this.subscribes.get(userName);
        if(sub==null)
            return null;
        if (current.canWatchUserHistory()) {
            return sub.getPurchases();
        }
        return null;
    }

    /**
     * use case 6.4.2 - admin watch history purchases of some user
     * use case 4.10 - watch Store History by store owner
     * @param id
     * @param storeName - the name of the store that own the purchases
     * @return - list of purchases that of the store
     */
    public List<Purchase> watchStorePurchasesHistory(int id, String storeName) {
        loggerSystem.writeEvent("LogicManager","watchStorePurchasesHistory",
                "admin watch a store purchase history", new Object[] {storeName});
        User current=connectedUsers.get(id);
        if(!stores.containsKey(storeName))
            return null;
        if (current.canWatchStoreHistory(storeName)) {
            Store store = this.stores.get(storeName);
            return store.getPurchases();
        }
        return null;
    }

}
