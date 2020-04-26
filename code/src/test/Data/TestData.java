package Data;

import DataAPI.*;
import Domain.*;
import Domain.PurchasePolicy.*;

import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestData {
    private HashMap<Data, Subscribe> users;
    private HashMap<Data, ProductData> productsData;
    private HashMap<Data, StoreData> stores;
    private HashMap<Data, List<Discount>> discounts;
    private HashMap<Data, HashMap<ProductData, Integer>> basket;
    private HashMap<Data, Filter> filters;
    private HashMap<Data, Request> requests;
    private HashMap<Data, Review> reviwes;
    private List<PermissionType> permissionTypeList;
    private HashMap<Data, PaymentData> paymentData;
    private HashMap<Data, DeliveryData> deliveryData;
    private HashMap<Data, Integer> ids;
    private HashMap<Data, PurchasePolicy> purchasePolicy;

    /**
     * create data for tests
     */
    public TestData() {
        setUpIds();
        setUpUsers();
        setUpDiscountData();
        setUpProductData();
        setUpStoreData();
        setUpBasketData();
        setUpFilters();
        setUpRequests();
        setUpReviews();
        setUpPaymentData();
        setUpDeliveryData();
        setUpPermmisionTypes();
        setUpPurchasePolicy();
    }

    /**
     * set up data of ids
     */
    private void setUpIds() {
        ids=new HashMap<>();
        ids.put(Data.ADMIN,0);
        ids.put(Data.VALID,1);
        ids.put(Data.VALID2,2);
    }

    /**
     * set up data of permissions
     */
    private void setUpPermmisionTypes() {
        permissionTypeList=new ArrayList<>();
        permissionTypeList.add(PermissionType.ADD_MANAGER);
        permissionTypeList.add(PermissionType.ADD_OWNER);
    }

    /**
     * set up data of users
     */
    private void setUpUsers() {
        users = new HashMap<>();
        users.put(Data.NULL, new Subscribe(null, null));
        users.put(Data.ADMIN,new Subscribe("Admin","Admin"));
        users.put(Data.VALID,new Subscribe("Yuval","Sabag"));
        users.put(Data.NULL_NAME, new Subscribe(null, "Admin"));
        users.put(Data.NULL_PASSWORD, new Subscribe("Admin", null));
        users.put(Data.WRONG_NAME, new Subscribe("","Changed_Password"));
        users.put(Data.WRONG_PASSWORD, new Subscribe("Yossi",""));
        users.put(Data.VALID2,new Subscribe("Niv","Shirazi"));
    }

    /**
     * set up data of discount
     */
    private void setUpDiscountData() {
        discounts=new HashMap<>();
        List<Discount> discountsListHasNull=new ArrayList<>();
        discountsListHasNull.add(null);
        List<Discount> discountListNegPercentage=new ArrayList<>();
        discountListNegPercentage.add(new Discount(-1));
        List<Discount> discountListOver100Percentage=new ArrayList<>();
        discountListOver100Percentage.add(new Discount(101));
        discounts.put(Data.NULL_DISCOUNT,discountsListHasNull);
        discounts.put(Data.NEGATIVE_PERCENTAGE,discountListNegPercentage);
        discounts.put(Data.OVER_100_PERCENTAGE,discountListOver100Percentage);
    }

    /**
     * set up data of products
     */
    private void setUpProductData(){
        productsData = new HashMap<>();
        //change data type to enum
        productsData.put(Data.VALID,new ProductData("peanuts","Store","category"
                ,null,1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL, null);
        productsData.put(Data.NULL_STORE,new ProductData("peanuts",null,"category"
                ,null,1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL_NAME,new ProductData(null,"Store","category"
                ,null,1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.WRONG_STORE,new ProductData("peanuts","$storeBBB$","category"
                ,null,1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL_CATEGORY,new ProductData("peanuts","Store",null
                ,null,1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.ZERO,new ProductData("peanuts","Store","category"
                ,null,0,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NEGATIVE_AMOUNT,new ProductData("peanuts","Store","category"
                ,null,-1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NEGATIVE_PRICE,new ProductData("peanuts","Store","category"
                ,null,1,-10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL_PURCHASE,new ProductData("peanuts","Store","category"
                ,null,1,10, null));
        productsData.put(Data.WRONG_DISCOUNT,new ProductData("peanuts","Store","category"
                ,null,1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NEGATIVE_PERCENTAGE,new ProductData("peanuts","Store","category"
                ,null,1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.OVER_100_PERCENTAGE,new ProductData("peanuts","Store","category"
                ,null,1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.SAME_NAME,new ProductData("peanuts","store","category1"
                ,null,12,101, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL_PRODUCT,new ProductData(null,null,null
                ,null,1,10, null));
        productsData.put(Data.EDIT,new ProductData("peanuts","Store","categoryYuval"
                ,null,3,11, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.WRONG_NAME,new ProductData("peanuts1","Store","category"
                ,null,1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.LARGE_AMOUNT,new ProductData("peanuts","Store","category"
                ,null,100,10, PurchaseTypeData.IMMEDDIATE));

    }

    /**
     * set up data of stores
     */
    private void setUpStoreData() {
        stores = new HashMap<>();
        stores.put(Data.VALID,new StoreData("Store",new PurchasePolicy1(), new DiscountPolicy()));
        stores.put(Data.NULL, null);
        stores.put(Data.NULL_NAME, new StoreData(null,new PurchasePolicy1(), new DiscountPolicy()));
        stores.put(Data.WRONG_STORE, new StoreData("None Store",new PurchasePolicy1(), new DiscountPolicy()));
        stores.put(Data.NULL_STORE, new StoreData(null,new PurchasePolicy1(), new DiscountPolicy()));
        stores.put(Data.NULL_PURCHASE, new StoreData("Store",null, new DiscountPolicy()));
        stores.put(Data.NULL_DISCOUNT, new StoreData("Store",new PurchasePolicy1(), null));
    }

    /**
     * set up data of baskets
     */
    private void setUpBasketData() {
        basket = new HashMap<Data, HashMap<ProductData, Integer>>();
        HashMap <ProductData, Integer> productsInBasket = new HashMap<>();
        productsInBasket.put(productsData.get(Data.VALID), 100);
        basket.put(Data.VALID, productsInBasket);
    }

    public HashMap<ProductData, Integer> getProductsInBasket(Data data) {
        return basket.get(data);
    }

    /**
     * set up data of filters
     */
    private void setUpFilters() {
        filters = new HashMap<>();
        ProductData p = getProductData(Data.VALID);
        filters.put(Data.NULL, null);
        filters.put(Data.VALID, new Filter(Search.NONE, p.getProductName(),p.getPrice(),
                p.getPrice(),p.getCategory()));
        filters.put(Data.WRONG_NAME, new Filter(Search.PRODUCT_NAME, "Not Product Name",p.getPrice(),
                p.getPrice(),p.getCategory()));
        filters.put(Data.WRONG_CATEGORY, new Filter(Search.CATEGORY, p.getProductName(),p.getPrice(),
                p.getPrice(),"Not Category !!"));
        filters.put(Data.WRONG_KEY_WORD, new Filter(Search.KEY_WORD, "Not Key Word!!",p.getPrice(),
                p.getPrice(),p.getCategory()));
        filters.put(Data.NULL_SEARCH, new Filter(null,p.getProductName(),0,0,""));
        filters.put(Data.NULL_VALUE, new Filter(Search.PRODUCT_NAME,null, 0, 0, ""));
        filters.put(Data.NEGATIVE_MIN, new Filter(Search.PRODUCT_NAME, p.getProductName(),-1,
                0,""));
        filters.put(Data.NEGATIVE_MAX, new Filter(Search.PRODUCT_NAME, p.getProductName(),0,
                -1,""));
        filters.put(Data.NULL_CATEGORY, new Filter(Search.PRODUCT_NAME, p.getProductName(),0,
                -1,null));
        filters.put(Data.FILTER_MIN, new Filter(Search.NONE,"",0,Double.MAX_VALUE,""));
        filters.put(Data.FILTER_MAX, new Filter(Search.NONE,"",0,Double.MAX_VALUE,""));
        filters.put(Data.FILTER_ALL_CATEGORIES, new Filter(Search.NONE,"",0,
                Double.MAX_VALUE,""));

    }

    /**
     * set up data of reviews
     */
    private void setUpReviews() {
        reviwes = new HashMap<>();
        Subscribe subscribe = getSubscribe(Data.VALID);
        ProductData productData = getProductData(Data.VALID);
        reviwes.put(Data.NULL, null);
        reviwes.put(Data.VALID,new Review(subscribe.getName(), productData.getStoreName(),
                productData.getProductName(),"Review"));
        reviwes.put(Data.NULL_STORE, new Review(subscribe.getName(),null, productData.getProductName(),
                "Review"));
        reviwes.put(Data.NULL_PRODUCT, new Review(subscribe.getName(), productData.getStoreName(),
                null,"Review"));
        reviwes.put(Data.NULL_CONTENT, new Review(subscribe.getName(), productData.getStoreName(),
                productData.getProductName(),null));
        reviwes.put(Data.EMPTY_CONTENT,new Review(subscribe.getName(), productData.getStoreName(),
                productData.getProductName(),""));
        productData = getProductData(Data.WRONG_STORE);
        reviwes.put(Data.WRONG_STORE,new Review(subscribe.getName(), productData.getStoreName(),
                productData.getProductName(),"Review"));
        productData = getProductData(Data.WRONG_NAME);
        reviwes.put(Data.WRONG_PRODUCT,new Review(subscribe.getName(), productData.getStoreName(),
                productData.getProductName(),"Review"));

    }

    /**
     * set up data of paymentData
     */
    private void setUpPaymentData() {
        paymentData = new HashMap<Data, PaymentData>();
        String userName = users.get(Data.VALID).getName();
        paymentData.put(Data.VALID, new PaymentData(userName, "Tapoz 3, Nevatim", 30, "4580"));
        paymentData.put(Data.NULL , null);
        paymentData.put(Data.NULL_ADDRESS ,new PaymentData(userName,null, 22, "4580"));
        paymentData.put(Data.EMPTY_ADDRESS ,new PaymentData(userName,"", 23, "4580"));
        paymentData.put(Data.NULL_PAYMENT ,new PaymentData(userName,"Tapoz 3, Nevatim", 24, null));
        paymentData.put(Data.EMPTY_PAYMENT ,new PaymentData(userName,"Tapoz 3, Nevatim", 25, ""));
        paymentData.put(Data.NULL_NAME ,new PaymentData(null,"Tapoz 3, Nevatim", 26, "4580"));
        paymentData.put(Data.EMPTY_NAME,new PaymentData("","Tapoz 3, Nevatim", 27, "4580"));
        paymentData.put(Data.UNDER_AGE,new PaymentData(userName,"Tapoz 3, Nevatim", 3, "4580"));


    }

    /**
     * set up data of deliveryData
     */
    private void setUpDeliveryData() {
        deliveryData = new HashMap<Data, DeliveryData>();
        List<ProductData> product = new LinkedList<>();
        List<ProductData> tooMuchProduct = new LinkedList<>();
        product.add(this.productsData.get(Data.VALID));
        tooMuchProduct.add((this.productsData.get(Data.LARGE_AMOUNT)));
        deliveryData.put(Data.VALID, new DeliveryData("Tapoz 3, Nevatim", "Israel", product));
        deliveryData.put(Data.EMPTY_ADDRESS, new DeliveryData("", "Israel", product));
        deliveryData.put(Data.NULL_ADDRESS, new DeliveryData(null, "Israel", product));
        deliveryData.put(Data.EMPTY_COUNTRY, new DeliveryData("Tapoz 3, Nevatim", "", product));
        deliveryData.put(Data.NULL_COUNTRY, new DeliveryData("Tapoz 3, Nevatim", null, product));
        deliveryData.put(Data.INVALID_COUNTRY, new DeliveryData("Tapoz 3, Nevatim", "Italy", product));
        deliveryData.put(Data.LARGE_AMOUNT, new DeliveryData("Tapoz 3, Nevatim", "Israel", tooMuchProduct));
    }

    /**
     * set up data of requests
     */
    private void setUpRequests(){
        requests = new HashMap<>();
        requests.put(Data.VALID, new Request(users.get(Data.VALID).getName(), stores.get(Data.VALID).getName(), "where is the milk in this store?", 1));
        requests.put(Data.WRONG_NAME, new Request(users.get(Data.WRONG_NAME).getName(), stores.get(Data.VALID).getName(), "where is the milk in this store?", 1));
        requests.put(Data.WRONG_STORE, new Request(users.get(Data.VALID).getName(), stores.get(Data.WRONG_STORE).getName(), "where is the milk in this store?", 1));
        requests.put(Data.NULL_NAME, new Request(users.get(Data.VALID).getName(), stores.get(Data.NULL_NAME).getName(), "where is the milk in this store?", 1));
        requests.put(Data.NULL_CONTENT, new Request(users.get(Data.VALID).getName(), stores.get(Data.VALID).getName(), null, 1));
        requests.put(Data.WRONG_ID, new Request(users.get(Data.VALID).getName(), stores.get(Data.VALID).getName(), "where is the milk in this store?", -1));
    }

    /**
     * set up data of purchase policy
     */
    private void setUpPurchasePolicy() {
        purchasePolicy = new HashMap<>();
        HashMap<String, Integer> amountPerProduct = new HashMap<>();
        List<String> countries = new LinkedList<>();
        countries.add("Israel");
        amountPerProduct.put(this.getProductData(Data.VALID).getProductName(),3);
        purchasePolicy.put(Data.VALID_BASKET_PURCHASE_POLICY, new BasketPurchasePolicy(10));
        purchasePolicy.put(Data.VALID_PRODUCT_PURCHASE_POLICY, new ProductPurchasePolicy(amountPerProduct));
        purchasePolicy.put(Data.VALID_SYSTEM_PURCHASE_POLICY, new SystemPurchasePolicy(18));
        purchasePolicy.put(Data.VALID_USER_PURCHASE_POLICY, new UserPurchasePolicy(countries));
    }

    // ============================ getters ============================ //

    public Subscribe getSubscribe(Data data) {
        return users.get(data);
    }

    public ProductData getProductData(Data productCase){
        return productsData.get(productCase);
    }

    public Product getRealProduct(Data data) {
        ProductData productData = getProductData(data);
        return new Product(productData,new Category(productData.getCategory()));
    }

    public StoreData getStore(Data data) {
        return stores.get(data);
    }

    public Store getRealStore(Data data) {
        StoreData storeData = getStore(data);
        Permission permission = new Permission(getSubscribe(Data.VALID));
        Store store = new Store(storeData.getName(),storeData.getPurchasePolicy1(),
                storeData.getDiscountPolicy(), permission);
        permission.setStore(store);
        store.addProduct(getProductData(Data.VALID));
        return store;
    }

    public List<Discount> getDiscounts(Data data){
        return  discounts.get(data);
    }

    public Filter getFilter(Data data) {
        return filters.get(data);
    }

    public Review getReview(Data data) {
        return reviwes.get(data);
    }

    public List<PermissionType> getPermissionTypeList() {
        return permissionTypeList;
    }

    public PaymentData getPaymentData(Data data) {
        return this.paymentData.get(data);
    }

    public DeliveryData getDeliveryData(Data data) {
        return this.deliveryData.get(data);
    }

    public int getId(Data data){
        return ids.get(data);
    }

    public Request getRequest(Data data) { return requests.get(data); }

    public PurchasePolicy getPurchasePolicy(Data data) {
        return purchasePolicy.get(data);
    }

    // ============================ getters ============================ //

    /**
     * use case 2.5 - search Products
     * The function compare 2 products data
     * @param that - that product data
     * @param other - other prodoct data
     * @return true if 2 product are equal, otherwise false
     */
    public boolean compareProductData(ProductData that, ProductData other) {
        return  that.getProductName().compareTo(other.getProductName()) == 0 &&
                that.getStoreName().compareTo(other.getStoreName()) == 0 &&
                that.getCategory().compareTo(other.getCategory()) == 0;
        //&& purchaseType == that.purchaseType;
    }

}
