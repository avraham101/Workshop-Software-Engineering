package Domain;

import DataAPI.*;
import Domain.Discount.Discount;
import Domain.Discount.Term.Term;
import Domain.PurchasePolicy.PurchasePolicy;
import Systems.HashSystem;
import Systems.LoggerSystem;
import Systems.PaymentSystem.PaymentSystem;
import Systems.PaymentSystem.ProxyPayment;
import Systems.SupplySystem.ProxySupply;
import Systems.SupplySystem.SupplySystem;
import Publisher.Publisher;
import Utils.Utils;
import Utils.InterfaceAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class LogicManager {
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
    private Gson gson;
    private Publisher publisher;
    private ConcurrentHashMap<LocalDate, Double> revenue; // revenue by date

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
        GsonBuilder builderDiscount = new GsonBuilder();
        builderDiscount.registerTypeAdapter(Discount.class, new InterfaceAdapter());
        builderDiscount.registerTypeAdapter(PurchasePolicy.class,new InterfaceAdapter());
        builderDiscount.registerTypeAdapter(Term.class,new InterfaceAdapter());
        gson = builderDiscount.create();
        this.connectedUsers =connectedUsers;
        usersIdCounter=new AtomicInteger(0);
        requestIdGenerator = new AtomicInteger(0);
        revenue = new ConcurrentHashMap<>();
        try {
            hashSystem = new HashSystem();
            loggerSystem = new LoggerSystem();
            this.paymentSystem = paymentSystem;
            this.supplySystem = supplySystem;
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
            if(subscribes.isEmpty()&&!register(userName,password).getValue()) {
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
        this.revenue = new ConcurrentHashMap<>();
        GsonBuilder builderDiscount = new GsonBuilder();
        builderDiscount.registerTypeAdapter(Discount.class, new InterfaceAdapter());
        builderDiscount.registerTypeAdapter(PurchasePolicy.class,new InterfaceAdapter());
        builderDiscount.registerTypeAdapter(Term.class,new InterfaceAdapter());
        gson = builderDiscount.create();
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
            if(subscribes.isEmpty()&&!register(userName,password).getValue()) {
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
        this.revenue = new ConcurrentHashMap<>();
        requestIdGenerator = new AtomicInteger(0);
        GsonBuilder builderDiscount = new GsonBuilder();
        builderDiscount.registerTypeAdapter(Discount.class, new InterfaceAdapter());
        builderDiscount.registerTypeAdapter(PurchasePolicy.class,new InterfaceAdapter());
        builderDiscount.registerTypeAdapter(Term.class,new InterfaceAdapter());
        gson = builderDiscount.create();
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
            if(subscribes.isEmpty()&&!register(userName,password).getValue()) {
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
    public Response<Boolean> register(String userName, String password) {
        loggerSystem.writeEvent("LogicManager","register","the function register user",
                new Object[] {userName});
        if(!validName(userName) || !validPassword(password)) {
            return new Response<>(false, OpCode.Invalid_Register_Details);
        }
        Subscribe subscribe =null;
        try {
            password = hashSystem.encrypt(password);
        } catch (NoSuchAlgorithmException e) {
            loggerSystem.writeError("Logic manager", "register",
                    "Fail register the user",new Object[]{userName, password});
            return new Response<>(false, OpCode.Hash_Fail);
        }

        if(this.subscribes.isEmpty())
            subscribe = new Admin(userName, password);
        else
            subscribe = new Subscribe(userName, password);
        boolean output = this.subscribes.putIfAbsent(userName,subscribe)==null;
        subscribe.setPublisher(publisher);
        if(output==true)
            return new Response<>(output, OpCode.Success);
        return new Response<>(output,OpCode.User_Name_Already_Exist);
    }

    /***
     * use case 2.3 - Login
     *
     * @param id
     * @param userName - the user Name
     * @param password - the user password
     * @return true if the user is logged to the system, otherwise false
     */
    public Response<Boolean> login(int id, String userName, String password) {
        loggerSystem.writeEvent("LogicManager","login",
                "login a user", new Object[] {userName});
        if(!validName(userName) || !validPassword(password)) {
            return new Response<>(false, OpCode.Invalid_Login_Details);
        }
        Subscribe subscribe = this.subscribes.get(userName);
        User user= connectedUsers.get(id);
        if(subscribe!=null&&subscribe.getSessionNumber().compareAndSet(-1,id)){
            try {
                password = hashSystem.encrypt(password);
                if (subscribe.getPassword().compareTo(password) == 0) {
                    boolean output = user.login(subscribe);
                    return new Response<>(output, OpCode.Success);
                }
            } catch (NoSuchAlgorithmException e) {
                loggerSystem.writeError("Logic manager", "login",
                        "Fail to login the user",new Object[]{userName, password});
            }
        }

        return new Response<>(false,OpCode.User_Not_Found);
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
    public Response<List<StoreData>> viewStores() {
        loggerSystem.writeEvent("LogicManager","viewStores",
                "view the details of the stores in the system", new Object[] {});
        List<StoreData> data = new LinkedList<>();
        for (String storeName: stores.keySet()) {
            Store store = stores.get(storeName);
            StoreData storeData = new StoreData(store.getName(),store.getDescription());
            data.add(storeData);
        }
        return new Response<>(data,OpCode.Success);
    }

    /**
     * use case 2.4.2 - show the products of a given store
     * @param storeName - the store that owns the products
     * @return - list of ProductData of the products in the store
     */
    public Response<List<ProductData>> viewProductsInStore(String storeName) {
        loggerSystem.writeEvent("LogicManager","viewProductsInStore",
                "view the details of the stores in the system", new Object[] {storeName});
        if(storeName==null)
            return new Response<>(null,OpCode.Store_Not_Found);
        Store store = stores.get(storeName);
        if(store!=null) {
            List<ProductData> output = store.viewProductInStore();
            return new Response<>(output,OpCode.Success);
        }
        return new Response<>(null,OpCode.Store_Not_Found);
    }

    /**
     * use case 2.5 - Search product in store
     * @param filter - the filter chosen
     * @return - list of products after filer and sorter.
     */
    public Response<List<ProductData>> viewSpecificProducts(Filter filter) {
        loggerSystem.writeEvent("LogicManager","viewSpecificProducts",
                "view products after a filter", new Object[] {filter});
        if(!validFilter(filter)) {
            List<ProductData> output = new LinkedList<>();
            return new Response<>(output,OpCode.Not_Valid_Filter);
        }
        List<ProductData> productsData = searchProducts(filter.getSearch(),filter.getValue());
        productsData = filterProducts(productsData,filter);
        return new Response<>(productsData, OpCode.Success);
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
    private List<ProductData> searchProducts(Search search, String value) {
        loggerSystem.writeEvent("LogicManager","searchProducts",
                "search products in store after filter and sorter", new Object[] {search, value});
        List<ProductData> output = new LinkedList<>();
        int distance = 2;
        switch (search) {
            case NONE:
                output.addAll(searchNone());
                break;
            case CATEGORY:
                output.addAll(searchCategory(value,distance));
                break;
            case PRODUCT_NAME:
                output.addAll(searchProductName(value,distance));
                break;
            case KEY_WORD:
                output.addAll(searchKeyWord(value, distance));
                break;
        }
        return output;
    }

    /**
     * use case 2.5 - Search products in Store
     * Search None
     * @return the List of the products
     */
    private List<ProductData> searchNone() {
        List<ProductData> output = new LinkedList<>();
        for(Store store: stores.values()) {
            output.addAll(store.viewProductInStore());
        }
        return output;
    }

    /**
     * use case 2.5 - Search products in Store
     * Search Category
     * @return the List of the products
     */
    private List<ProductData> searchCategory(String value, int distance) {
        List<ProductData> output = new LinkedList<>();
        for(Store store: stores.values()) {
            for(Category category: store.getCategoryList().values()) {
                String categoryName = category.getName();
                if(Utils.editDistDP(categoryName,value,categoryName.length(),value.length())<= distance) {
                    for(Product p : category.getProducts()) {
                        output.add(new ProductData(p,store.getName()));
                    }
                }
            }
        }
        return output;
    }

    /**
     * use case 2.5 - Search products in Store
     * Search Product Name
     * @return the List of the products
     */
    private List<ProductData> searchProductName(String value, int distance) {
        List<ProductData> output = new LinkedList<>();
        for(Store store: stores.values()) {
            for(ProductData product: store.viewProductInStore()) {
                String productName = product.getProductName();
                if(Utils.editDistDP(productName,value,productName.length(),value.length())<= distance) {
                    output.add(product);
                }
            }
        }
        return output;
    }

    /**
     * use case 2.5 - Search products in Store
     * Search KeyWord
     * @return the List of the products
     */
    private List<ProductData> searchKeyWord(String value, int distance) {
        List<ProductData> output = searchCategory(value, distance);
        List<ProductData> list = searchProductName(value,distance);
        for(ProductData other: list) {
            boolean found = false;
            for(ProductData product: output) {
                if(product.equals(other)) {
                    found = true;
                    break;
                }
            }
            if(!found)
                output.add(other);
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
        int distance = 2;
        for(ProductData p: products) {
            //filter by min price
            if(filter.getMinPrice() <= p.getPrice()) {
                //filter by max price
                if(filter.getMaxPrice() >= p.getPrice()) {
                    //filter by category
                    if(filter.getCategory().isEmpty()) { //empty means dont filter by category
                        productData.add(p);
                    }
                    else {
                        String value = p.getCategory();
                        String categoryName = filter.getCategory();
                        if(Utils.editDistDP(categoryName,value,categoryName.length(),value.length())<= distance) {
                            productData.add(p);
                        }
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
    public Response<CartData> watchCartDetails(int id) {
        loggerSystem.writeEvent("LogicManager","watchCartDetails",
                "view the user cart data", new Object[] {});
        User current = connectedUsers.get(id);
        CartData output = current.watchCartDetatils();
        return new Response<>(output,OpCode.Success);
    }

    /**
     * use case 2.7.2 - delete product from cart
     * delete product from the cart
     * @param productName - the product to remove
     * @param storeName - the store that sale this product
     * @return - true if the delete work, false if not
     */
    public Response<Boolean> deleteFromCart(int id,String productName,String storeName){
        loggerSystem.writeEvent("LogicManager","deleteFromCart",
                "delete product from the user cart", new Object[] {productName, storeName});
        User current=connectedUsers.get(id);
        boolean output = current.deleteFromCart(productName,storeName);
        if (output) {
            return new Response<>(true, OpCode.Success);
        }
        return new Response<>(false, OpCode.Invalid_Product);
    }

    /**
     * use case 2.7.3 - edit amount of product
     * @param productName - the product to edit it's amount
     * @param storeName - the store of the product
     * @param newAmount - the new amount
     * @return - true if succeeded, false if not
     */
    public Response<Boolean> editProductInCart(int id,String productName,String storeName,int newAmount) {
        loggerSystem.writeEvent("LogicManager","editProductInCart",
                "edit the amount of a product in the cart", new Object[] {productName, storeName, newAmount});
        User current=connectedUsers.get(id);
        boolean output = current.editProductInCart(productName,storeName, newAmount);

        if (output) {
            return new Response<>(true,OpCode.Success);
        }
        return new Response<>(false,OpCode.Invalid_Product);
    }

    /**
     * use case 2.7.4 - add product to the cart
     * @param productName - the product to add
     * @param storeName - the store of the product
     * @param amount - the amount of the product that need to add to the cart
     * @return - true if added, false if not
     */
    public Response<Boolean> addProductToCart(int id,String productName, String storeName, int amount) {
        loggerSystem.writeEvent("LogicManager","addProductToCart",
                "add a product to the cart", new Object[] {productName, storeName, amount});
        boolean result = false;
        Store store = null;
        User current=connectedUsers.get(id);
        if (storeName != null)
            store = stores.get(storeName);
        if(store==null)
            return new Response<>(false, OpCode.Store_Not_Found);
        Product product = store.getProduct(productName);
        if(product==null||amount <= 0 || amount > product.getAmount()){
            return new Response<>(false, OpCode.Not_Found);
        }
        product = product.clone();
        result = current.addProductToCart(store, product, amount);
        if (result) {
            return new Response<>(true, OpCode.Success);
        }
        return new Response<>(false, OpCode.Invalid_Product);
    }

    /**
     * use case 2.8 - purchase cart
     * @param id - the id
     * @param paymentData - the payment data of this purchase
     * @param addresToDeliver - the address do Deliver the purchase
     * @return true is the purchase succeeded, otherwise false
     */
    public Response<Boolean> purchaseCart(int id, String country, PaymentData paymentData, String addresToDeliver) {
        loggerSystem.writeEvent("LogicManager","purchaseCart",
                "reserveCart the products in the cart", new Object[] {paymentData, addresToDeliver});
        //1) user get
        User current = connectedUsers.get(id);
        //2) validation check
        if (!validPaymentData(paymentData))
            return new Response<>(false, OpCode.Invalid_Payment_Data);
        if (addresToDeliver == null || addresToDeliver.isEmpty() || country == null || country.isEmpty())
            return new Response<>(false, OpCode.Invalid_Delivery_Data);
        //3) sumUp cart - updated PaymentData, DeliveryData and check policy of store
        boolean reserved = current.reservedCart();
        if(!reserved) {
            return new Response<>(false, OpCode.Fail_Buy_Cart);
        }
        DeliveryData deliveryData = new DeliveryData(addresToDeliver, country, new LinkedList<>());
        return buyAndPay(id, paymentData, deliveryData);
    }

    /**
     * use case 2.8 - purchase cart
     * @param id - the id
     * @param paymentData - the payment data of this purchase
     * @param deliveryData - delivery details
     * @return true is the purchase succeeded, otherwise false
     */
    private Response<Boolean> buyAndPay(int id, PaymentData paymentData, DeliveryData deliveryData) {
        User current = connectedUsers.get(id);
        if(!current.buyCart(paymentData, deliveryData)){
            current.cancelCart();
            return new Response<>(false, OpCode.Not_Stands_In_Policy);
        }
        //4) external systems
        Response<Boolean> payedAndDelivered = externalSystemsBuy(id,paymentData,deliveryData);
        if(!payedAndDelivered.getValue()) {
            return payedAndDelivered;
        }
        //5) update the purchase for both store and user (synchronized)
        current.savePurchase(paymentData.getName());
        sendNotificationsToAllStoreManagers(deliveryData.getProducts());
        return new Response<>(true, OpCode.Success);
    }

    private void sendNotificationsToAllStoreManagers(List<ProductData> products) {
        HashMap <String,List<ProductData>> productsAndStores=new HashMap<>();
        for(ProductData p:products){
            if(!productsAndStores.containsKey(p.getStoreName()))
                productsAndStores.put(p.getStoreName(),new ArrayList<>());
            productsAndStores.get(p.getStoreName()).add(p);
        }
        for(String storeName:productsAndStores.keySet()){
            Store store=stores.get(storeName);
            store.sendManagersNotifications(productsAndStores.get(storeName));
        }
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
    private Response<Boolean> externalSystemsBuy(int id, PaymentData paymentData, DeliveryData deliveryData) {
        User current = connectedUsers.get(id);
        if(!paymentSystem.pay(paymentData)) {
            loggerSystem.writeError("Logic Manger","purchaseCart","Payment System Crashed",
                    new Object[] {id});
            current.cancelCart();
            return new Response<>(false, OpCode.Payment_Reject);
        }
        if(!supplySystem.deliver(deliveryData)) {
            loggerSystem.writeError("Logic Manger","purchaseCart","Delivery System Crashed",
                    new Object[] {id});
            if(!paymentSystem.cancel(paymentData)) {
                loggerSystem.writeError("Logic Manger","purchaseCart",
                        "Payment System Crashed", new Object[] {id});
            }
            current.cancelCart();
            return new Response<>(false, OpCode.Supply_Reject);
        }
        addToRevenue(paymentData.getTotalPrice());
        return new Response<>(true,OpCode.Success);
    }

    /**
     * add to the revenue the total price of a buy
     * @param totalRevenue - the total price of a buy
     */
    private void addToRevenue(double totalRevenue) {
        double todayRevenue = 0;
        if (revenue.containsKey(LocalDate.now()))
            todayRevenue = revenue.get(LocalDate.now());
        revenue.put(LocalDate.now(), todayRevenue + totalRevenue);
    }

    /**
     * use case 3.1 - Logout
     * @return true if the user logout
     * @param id
     */
    public Response<Boolean> logout(int id) {
        loggerSystem.writeEvent("LogicManager","logout",
                "a user logout from the system", new Object[] {});
        User current=connectedUsers.get(id);
        boolean output = current.logout();
        return new Response<>(output, OpCode.Success);
    }

    /**
     * use case 3.2 - Open Store
     * @param id - the number of the session that is connected
     * @param storeDetails - the details of the the store
     * @return true if can open store, otherwise false.
     */
    public Response<Boolean> openStore(int id, StoreData storeDetails) {
        loggerSystem.writeEvent("LogicManager","openStore",
                "open new store", new Object[] {storeDetails});
        if(!validStoreDetails(storeDetails))
            return new Response<>(false, OpCode.Invalid_Store_Details);
        User current=connectedUsers.get(id);
        //prevent making two stores with the same name
        synchronized (openStoreLocker) {
            if (stores.containsKey(storeDetails.getName()))
                return new Response<>(false, OpCode.Store_Not_Found);
            Store store = current.openStore(storeDetails);
            if(store != null) {
                stores.put(store.getName(),store);
                return new Response<>(true, OpCode.Success);
            }
        }
        return new Response<>(false, OpCode.Store_Doesnt_Exist);
    }

    /**
     * The function check if storeData is valid
     * @param storeData - the store data to check
     * @return true the store data is ok, otherwise false
     */
    private boolean validStoreDetails(StoreData storeData) {
        return storeData!=null && storeData.getName() != null && storeData.getDescription()!=null;
    }


    /**
     * use case 3.3 - write review
     * @param id
     * @param storeName - the store name
     * @param productName - the product name
     * @param content - the content name
     * @return true if the review added, otherwise false.
     */
    public Response<Boolean> addReview(int id, String storeName, String productName, String content) {
        loggerSystem.writeEvent("LogicManager","addReview",
                "add a review for the product", new Object[] {storeName, productName, content});
        if(!validReview(storeName,productName,content))
            return new Response<>(false,OpCode.Invalid_Review);
        User current=connectedUsers.get(id);
        Store store = stores.get(storeName);
        if(store==null) {
            return new Response<>(false,OpCode.Store_Not_Found);
        }
        Review review = new Review(current.getUserName(),storeName,productName,content);
        boolean resultStore = store.addReview(review);
        boolean resultUser = current.addReview(review);
        if(!resultStore && !resultUser)
            return new Response<>(false,OpCode.Cant_Add_Review);
        else if(!resultStore) {
            current.removeReview(review);
            return new Response<>(false,OpCode.Cant_Add_Review);
        }
        else if(!resultUser) {
            store.removeReview(review);
            return new Response<>(false,OpCode.Cant_Add_Review);
        }
        return new Response<>(true,OpCode.Success);
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
    public Response<Boolean> addRequest(int id,String storeName, String content) {
        loggerSystem.writeEvent("LogicManager","addRequest",
                "add a request to the store", new Object[] {storeName, content});
        if (storeName == null || content == null || !stores.containsKey(storeName))
            return new Response<>(false,OpCode.Invalid_Request);
        Store dest = stores.get(storeName);
        User current = connectedUsers.get(id);
        int requestId = requestIdGenerator.incrementAndGet(); // generate request number sync
        Request request = current.addRequest(requestId, storeName, content);
        if (request == null) {
            return new Response<>(false,OpCode.Null_Request);
        }
        dest.addRequest(request);
        return new Response<>(true,OpCode.Success);
    }

    /**
     * use case 3.7 - watch purchase history
     * the function return the purchase list
     * @return the purchase list
     */
    public Response<List<Purchase>> watchMyPurchaseHistory(int id) {
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
    public Response<Boolean> addProductToStore(int id,ProductData productData) {
        loggerSystem.writeEvent("LogicManager","addProductToStore",
                "add a product to store", new Object[] {productData});
        User current=connectedUsers.get(id);
        if(productData==null)
            return new Response<>(false,OpCode.Invalid_Product);
        if(!validProduct(productData))
            return new Response<>(false,OpCode.Invalid_Product);
        if(stores.containsKey(productData.getStoreName()))
            return current.addProductToStore(productData);
        return new Response<>(false,OpCode.Store_Not_Found);
    }

    /**
     * check if the data of the product has valid content
     * @param productData data of the product to check
     * @return true if the details of the product are valid
     */
    private boolean validProduct(ProductData productData) {
        return productData.getProductName()!=null &&productData.getCategory()!=null && productData.getPrice()>0
                && productData.getAmount()>0 && productData.getPurchaseType()!=null;
    }

    /**
     * use case 4.1.2 - remove a product from store if exist
     * @param storeName name of the store to remove the product from
     * @param productName name of product to be removed
     * @return if the product was removed
     */
    public Response<Boolean> removeProductFromStore(int id,String storeName, String productName) {
        loggerSystem.writeEvent("LogicManager","addProductToStore",
                "remove a product to store", new Object[] {storeName, productName});
        User current=connectedUsers.get(id);
        if(!stores.containsKey(storeName))
            return new Response<>(false,OpCode.Store_Not_Found);
        return current.removeProductFromStore(storeName,productName);
    }

    /**
     * use case 4.1.3 - edit product
     * @param productData the product to be edited to
     * @return if the product was edited successfully
     */
    public Response<Boolean> editProductFromStore(int id,ProductData productData) {
        loggerSystem.writeEvent("LogicManager","editProductFromStore",
                "edit the product amount in the store", new Object[] {productData});
        User current=connectedUsers.get(id);
        if(productData==null)
            return new Response<>(false,OpCode.Invalid_Product);
        if(!stores.containsKey(productData.getStoreName()))
            return new Response<>(false,OpCode.Store_Not_Found);
        if(validProduct(productData))
            return current.editProductFromStore(productData);
        return new Response<>(false,OpCode.Invalid_Product);
    }

    /**
     * 4.2.1.1 - add discount
     * @param id
     * @param discountData - data of the new discount to add
     * @param storeName - name of the store to add the discount to
     */
    public Response<Boolean> addDiscount(int id, String discountData, String storeName) {
        loggerSystem.writeEvent("LogicManager","addDiscountToStore",
                "add discount to the store", new Object[] {discountData,storeName});
        User current=connectedUsers.get(id);
        Store store=stores.get(storeName);
        if(store==null)
            return new Response<>(false,OpCode.Store_Not_Found);
        Discount discount=makeDiscountFromData(discountData);
        if(discount==null)
            return new Response<>(false,OpCode.Invalid_Discount);
        return current.addDiscountToStore(storeName,discount);
    }

    public Discount makeDiscountFromData(String discountData){
        try {
            Discount d = gson.fromJson(discountData, Discount.class);
            if (d != null && d.isValid())
                return d;
        }
        catch (Exception ignored){
            this.loggerSystem.writeError("LogicManager",
                    "makeDiscountFromData",ignored.getMessage(),
                    new Object[]{discountData});
        }
        return null;
    }

    /**
     * 4.2.1.2 - remove discount
     * @param id
     * @param discountId - id of the discount ro delete
     * @param storeName - name of the store to remove the discount from
     */
    public Response<Boolean> deleteDiscountFromStore(int id, int discountId, String storeName){
        loggerSystem.writeEvent("LogicManager","removeDiscountToStore",
                "remove discount from the store", new Object[] {discountId,storeName});
        User current=connectedUsers.get(id);
        Store store=stores.get(storeName);
        if(store==null)
            return new Response<>(false,OpCode.Store_Not_Found);
        return current.deleteDiscountFromStore(discountId,storeName);
    }

    /**
     * 4.2.1.3 - view discounts
     * @param storeName - name of the store to get the discounts from
     */
    public Response<HashMap<Integer,String>> viewDiscounts(String storeName){
        loggerSystem.writeEvent("LogicManager","viewDiscountsOfStore",
                "view discount of the store", new Object[] {storeName});
        Store store=stores.get(storeName);
        if(store==null)
            return new Response<>(null,OpCode.Store_Not_Found);
        HashMap<Integer, Discount> discounts = new HashMap<>(store.getDiscount());
        HashMap<Integer,String> response=new HashMap<>();
        for(int id:discounts.keySet()){
            response.put(id, gson.toJson(discounts.get(id),Discount.class));
        }
        return new Response<>(response,OpCode.Success);
    }

    /**
     * 4.2.2.1 - update policy
     */
    private PurchasePolicy makePolicyFromData(String policyData){
        try {
            PurchasePolicy policy = gson.fromJson(policyData, PurchasePolicy.class);
            if (policy != null && policy.isValid())
                return policy;
        }
        catch (Exception e){
            this.loggerSystem.writeError("LogicManager",
                    "makePolicyFromData",e.getMessage(),
                    new Object[]{policyData});
        }
        return null;
    }

    /**
     * use case 4.2.2.1 - update policy
     * @param id - the id of the user
     * @param policyData - the data of the policy to add
     * @param storeName - the name of the store to add the policy
     * @return - true if added, false if not
     */
    public Response<Boolean> updatePolicy(int id, String policyData, String storeName) {
        loggerSystem.writeEvent("LogicManager","updatePolicy",
                "update the policy of the store", new Object[] {policyData,storeName});
        User current=connectedUsers.get(id);
        Store store=stores.get(storeName);
        if(store == null)
            return new Response<>(false,OpCode.Store_Not_Found);
        PurchasePolicy policy = makePolicyFromData(policyData);
        if(policy==null)
            return new Response<>(false,OpCode.Invalid_Policy);
        return current.updateStorePolicy(storeName, policy);
    }

    /**
     * 4.2.2.2 - view policy
     * @param storeName - name of the store to get the policy from
     */
    public Response<String> viewPolicy(String storeName){
        loggerSystem.writeEvent("LogicManager","viewPolicy",
                "view the policy of the store", new Object[] {storeName});
        Store store=stores.get(storeName);
        if(store==null)
            return new Response<>(null,OpCode.Store_Not_Found);
        PurchasePolicy policy = store.getPurchasePolicy();

        String output = gson.toJson(policy,PurchasePolicy.class);
        return new Response<>(output,OpCode.Success);
    }

    /**
     * use case 4.3 - manage owner
     * @param storeName the name of the store to be manager of
     * @param userName the user to be manager of the store
     * @return
     */
    public Response<Boolean> manageOwner(int id,String storeName, String userName) {
        loggerSystem.writeEvent("LogicManager","manageOwner",
                "store owner add a owner to the store", new Object[] {storeName, userName});
        if(!subscribes.containsKey(userName))
            return new Response<>(false,OpCode.User_Not_Found);
        if(!stores.containsKey(storeName))
            return new Response<>(false,OpCode.Store_Not_Found);
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
    public Response<Boolean> addManager(int id,String userName, String storeName) {
        loggerSystem.writeEvent("LogicManager","addManager",
                "store owner add a manager to the store", new Object[] {storeName, userName});
        if(!subscribes.containsKey(userName))
            return new Response<>(false,OpCode.User_Not_Found);
        if(!stores.containsKey(storeName))
            return new Response<>(false,OpCode.Store_Not_Found);
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
    public Response<Boolean> addPermissions(int id,List<PermissionType> permissions, String storeName, String userName) {
        loggerSystem.writeEvent("LogicManager","addPermissions",
                "store owner add a manager's permissions", new Object[] {permissions, storeName, userName});
        if(!validList(permissions))
            return new Response<>(false,OpCode.Invalid_Permissions);
        if(!subscribes.containsKey(userName))
            return new Response<>(false,OpCode.User_Not_Found);
        if(!stores.containsKey(storeName))
            return new Response<>(false,OpCode.Store_Not_Found);
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
    public Response<Boolean> removePermissions(int id,List<PermissionType> permissions, String storeName, String userName) {
        loggerSystem.writeEvent("LogicManager","removePermissions",
                "store owner remove manager's permission", new Object[] {permissions, storeName, userName});
        if(!validList(permissions))
            return new Response<>(false,OpCode.Invalid_Permissions);
        if(!subscribes.containsKey(userName))
            return new Response<>(false,OpCode.User_Not_Found);
        if(!stores.containsKey(storeName))
            return new Response<>(false,OpCode.Store_Not_Found);
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
    public Response<Boolean> removeManager(int id,String userName, String storeName) {
        loggerSystem.writeEvent("LogicManager","removeManager",
                "store owner remove manager", new Object[] {storeName, userName});
        if(!subscribes.containsKey(userName))
            return new Response<>(false,OpCode.User_Not_Found);
        if(!stores.containsKey(storeName))
            return new Response<>(false,OpCode.Store_Not_Found);
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
    public Response<List<RequestData>> viewStoreRequest(int id, String storeName) {
        loggerSystem.writeEvent("LogicManager","viewStoreRequest",
                "store owner view the requests of the store", new Object[] {storeName});
        User current=connectedUsers.get(id);
        List<RequestData> requestDatas = new LinkedList<>();;
        if(storeName != null && stores.containsKey(storeName)) {
            List<Request> requests = current.viewRequest(storeName);
            if(requests!=null){
                for(Request r:requests)
                    requestDatas.add(new RequestData(r));
            }
            return new Response<>(requestDatas,OpCode.Success);
        }
        return new Response<>(requestDatas,OpCode.Store_Not_Found);
    }

    /**
     * use case 4.9.2 - replay to Request
     * @param id
     * @param storeName
     * @param requestID
     * @param content
     * @return true if replay, false else
     */
    public Response<RequestData> replayRequest(int id, String storeName, int requestID, String content) {
        loggerSystem.writeEvent("LogicManager","viewStoreRequest",
                "store owner view the requests of the store", new Object[] {storeName});
        User current=connectedUsers.get(id);
        if (storeName!=null && stores.containsKey(storeName)){
            Response<Request> response=current.replayToRequest(storeName, requestID, content) ;
            Request request=response.getValue();
            if(request!=null){
                Notification<Request> notification=new Notification<>(request,OpCode.Reply_Request);
                subscribes.get(request.getSenderName()).sendNotification(notification);
                return new Response<RequestData>(new RequestData(request),response.getReason());
            }
            return new Response<RequestData>(null,response.getReason());
        }

        return new Response<>(null,OpCode.Store_Not_Found);
    }

    /**
     * use case 6.4.1 - admin watch history purchases of some user
     * @param id
     * @param userName - the user that own the purchases
     * @return - list of purchases that of the user
     */
    public Response<List<Purchase>> watchUserPurchasesHistory(int id, String userName) {
        loggerSystem.writeEvent("LogicManager","watchUserPurchasesHistory",
                "admin watch a user purchase history", new Object[] {userName});
        User current=connectedUsers.get(id);
        Subscribe sub = this.subscribes.get(userName);
        if(sub==null)
            return new Response<>(null,OpCode.User_Not_Found);
        if (current.canWatchUserHistory()) {
            return new Response<>(sub.getPurchases(),OpCode.Success);
        }
        return new Response<>(null,OpCode.Dont_Have_Permission);
    }

    /**
     * use case 6.4.2 - admin watch history purchases of some user
     * use case 4.10 - watch Store History by store owner
     * @param id
     * @param storeName - the name of the store that own the purchases
     * @return - list of purchases that of the store
     */
    public Response<List<Purchase>> watchStorePurchasesHistory(int id, String storeName) {
        loggerSystem.writeEvent("LogicManager","watchStorePurchasesHistory",
                "admin watch a store purchase history", new Object[] {storeName});
        User current=connectedUsers.get(id);
        Store store = this.stores.get(storeName);
        if(store==null)
            return new Response<>(null,OpCode.Store_Not_Found);
        if (current.canWatchStoreHistory(storeName))
            return new Response<>(store.getPurchases(),OpCode.Success);
        return new Response<>(null,OpCode.Dont_Have_Permission);
    }

    /**
     * get the stores a user manage
     * @param id user's id
     * @return list of stores managed by user,
     * if user does not manage store return null
     */
    public Response<List<StoreData>> getStoresManagedByUser(int id){
        User user = connectedUsers.get(id);
        Response<List<StoreData>> response = new Response<>(null,OpCode.No_Stores_To_Manage);
        if(user==null)
            return response;
        List<Store> managedStores = user.getMyManagedStores();
        List<StoreData> storesData = new ArrayList<>();
        if(managedStores ==null){
            return response;
        }
        else{
            for (Store store: managedStores) {
                storesData.add(new StoreData(store.getName(),store.getDescription()));

            }
            response.setReason(OpCode.Success);
            response.setValue(storesData);

        }
        return response;
    }

    /**
     * get the permission of a user by a store
     * @param id user's id
     * @param storeName store name
     * @return list of user's permissions for given store
     * if regular manager -> empty list
     * not a manager -> null
     */
    public Response<Set<StorePermissionType>> getPermissionsForStore(int id, String storeName) {
        User user = connectedUsers.get(id);
        Response<Set<StorePermissionType>> response = new Response<>(null,OpCode.Dont_Have_Permission);
        if(user==null)
            return response;
        Set<StorePermissionType> userStorePermissions = user.getPermissionsForStore(storeName);
        if(userStorePermissions!=null){
            response.setValue(userStorePermissions);
            response.setReason(OpCode.Success);
        }
        return response;


    }


    /**
     * return all the managers of a specific store
     * @return managers of specific store
     */
    public Response<List<String>> getManagersOfStore(String storeName) {
        Store store=stores.get(storeName);
        if(store==null)
            return new Response<>(null,OpCode.Store_Not_Found);
        List<String> managers=new ArrayList<>(store.getPermissions().keySet());
        return new Response<>(managers,OpCode.Success);
    }

    /**
     * return all the managers of a specific store that user with id managed
     * @return managers of specific store
     */
    public Response<List<String>> getManagersOfStoreUserManaged(int id,String storeName){
        Store store=stores.get(storeName);
        User current=connectedUsers.get(id);
        if(store==null)
            return new Response<>(null,OpCode.Store_Not_Found);
        return current.getManagersOfStoreUserManaged(storeName);
    }

    /**
     * get all the subscribes users
     * @return
     */
    public Response<List<String>> getAllUsers(int id) {
        User current=connectedUsers.get(id);
        if (!current.canWatchUserHistory()) {
            return new Response<>(new LinkedList<>(),OpCode.NOT_ADMIN);
        }
        List<String> users = new LinkedList<>(this.subscribes.keySet());
        return new Response<>(users,OpCode.Success);
    }

    public void setPublisher(Publisher pub) {
        for(String s: this.subscribes.keySet()) {
            Subscribe sub = this.subscribes.get(s);
            sub.setPublisher(pub);
        }
        this.publisher=pub;
    }

    public void deleteReceivedNotifications(int id, List<Integer> notificationsId) {
        User current=connectedUsers.get(id);
        current.deleteReceivedNotifications(notificationsId);

    }

    public Response<Boolean> getMyNotification(int id) {
        User current = connectedUsers.get(id);
        Boolean rep = current.sendMyNotification();
        Response<Boolean> response = new Response<>(rep,OpCode.Not_Login);
        if(rep){
            response.setReason(OpCode.Success);
        }
        return response;

    }

    /**
     * get the revenue of the trading system by date
     * @param id - the id of the user
     * @param date - the date
     * @return - the revenue on this date
     */
    //TODO - call in the service
    public Response<Double> getRevenueByDate(int id, LocalDate date) {
        User current = connectedUsers.get(id);
        if (current != null && current.canWatchUserHistory()) {
            if (revenue.containsKey(date))
                return new Response<>(revenue.get(date), OpCode.Success);
            else
                return new Response<>(0.0, OpCode.Not_Found);
        }
        return new Response<>(0.0,OpCode.NOT_ADMIN);
    }

    /**
     * get the revenue of the trading system today
     * @param id - the id of the user
     * @return - the revenue today
     */
    public Response<Double> getRevenueToday(int id) {

        User current = connectedUsers.get(id);
        if (current != null && current.canWatchUserHistory()) {
            LocalDate date = LocalDate.now();
            if (revenue.containsKey(date))
                return new Response<>(revenue.get(date), OpCode.Success);
            else
                return new Response<>(0.0, OpCode.Not_Found);
        }
        return new Response<>(0.0,OpCode.NOT_ADMIN);
    }
}
