package Data;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import DataAPI.StoreData;
import Domain.*;
import Data.Data;
import Domain.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TestData {
    private HashMap<Data, Subscribe> users;
    private HashMap<Data, ProductData> productsData;
    private HashMap<Data, StoreData> stores;
    private HashMap<Data, List<Discount>> discounts;
    private HashMap<Data, HashMap<ProductData, Integer>> basket;
    private HashMap<Data, Filter> filters;
    private HashMap<Data, Review> reviwes;

    public TestData() {
        setUpUsers();
        setUpDiscountData();
        setUpProductData();
        setUpStoreData();
        setUpBasketData();
        setUpFilters();
        setUpReviews();
    }

    private void setUpUsers() {
        users = new HashMap<>();
        users.put(Data.NULL, new Subscribe(null, null));
        users.put(Data.ADMIN,new Subscribe("Admin","Admin"));
        users.put(Data.VALID,new Subscribe("Yuval","Sabag"));
        users.put(Data.NULL_NAME, new Subscribe(null, "Admin"));
        users.put(Data.NULL_PASSWORD, new Subscribe("Admin", null));
        users.put(Data.WRONG_NAME, new Subscribe("","Changed_Password"));
        users.put(Data.WRONG_PASSWORD, new Subscribe("Admin",""));
    }

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

    private void setUpProductData(){
        productsData = new HashMap<>();
        //change data type to enum
        productsData.put(Data.VALID,new ProductData("peanuts","Store","category"
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL, null);
        productsData.put(Data.NULL_STORE,new ProductData("peanuts",null,"category"
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL_NAME,new ProductData(null,"Store","category"
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.WRONG_STORE,new ProductData("peanuts","$storeBBB$","category"
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL_CATEGORY,new ProductData("peanuts","Store",null
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL_DISCOUNT, new ProductData("peanuts","Store","category"
                ,null,null,1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.ZERO,new ProductData("peanuts","Store","category"
                ,null,new ArrayList<Discount>(),0,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NEGATIVE_AMOUNT,new ProductData("peanuts","Store","category"
                ,null,new ArrayList<Discount>(),-1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NEGATIVE_PRICE,new ProductData("peanuts","Store","category"
                ,null,new ArrayList<Discount>(),1,-10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL_PURCHASE,new ProductData("peanuts","Store","category"
                ,null,new ArrayList<Discount>(),1,10, null));
        productsData.put(Data.WRONG_DISCOUNT,new ProductData("peanuts","Store","category"
                ,null,discounts.get(Data.NULL_DISCOUNT),1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NEGATIVE_PERCENTAGE,new ProductData("peanuts","Store","category"
                ,null,discounts.get(Data.NEGATIVE_PERCENTAGE),1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.OVER_100_PERCENTAGE,new ProductData("peanuts","Store","category"
                ,null,discounts.get(Data.OVER_100_PERCENTAGE),1,10, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.SAME_NAME,new ProductData("peanuts","store","category1"
                ,null,new ArrayList<Discount>(),12,101, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.NULL_PRODUCT,new ProductData(null,null,null
                ,null,null,1,10, null));
        productsData.put(Data.EDIT,new ProductData("peanuts","Store","categoryYuval"
                ,null,new ArrayList<Discount>(),3,11, PurchaseTypeData.IMMEDDIATE));
        productsData.put(Data.WRONG_NAME,new ProductData("peanuts1","Store","category"
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));

    }

    private void setUpStoreData() {
        stores = new HashMap<>();
        stores.put(Data.VALID,new StoreData("Store",new PurchesPolicy(), new DiscountPolicy()));
        stores.put(Data.NULL, null);
        stores.put(Data.NULL_NAME, new StoreData(null,new PurchesPolicy(), new DiscountPolicy()));
        stores.put(Data.NULL_PURCHASE, new StoreData("Store",null, new DiscountPolicy()));
        stores.put(Data.NULL_DISCOUNT, new StoreData("Store",new PurchesPolicy(), null));
    }

    private void setUpBasketData() {
        basket = new HashMap<Data, HashMap<ProductData, Integer>>();
        HashMap <ProductData, Integer> productsInBasket = new HashMap<>();
        productsInBasket.put(productsData.get(Data.VALID), 100);
        basket.put(Data.VALID, productsInBasket);
    }

    public HashMap<ProductData, Integer> getProductsInBasket(Data data) {
        return basket.get(data);
    }

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

    private void setUpReviews() {
        reviwes = new HashMap<>();
        Subscribe subscribe = getSubscribe(Data.VALID);
        ProductData productData = getProduct(Data.VALID);
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
        productData = getProduct(Data.WRONG_STORE);
        reviwes.put(Data.WRONG_STORE,new Review(subscribe.getName(), productData.getStoreName(),
                productData.getProductName(),"Review"));
        productData = getProduct(Data.WRONG_NAME);
        reviwes.put(Data.WRONG_PRODUCT,new Review(subscribe.getName(), productData.getStoreName(),
                productData.getProductName(),"Review"));

    }

    public Subscribe getSubscribe(Data data) {
        return users.get(data);
    }

    public ProductData getProductData(Data productCase){
        return productsData.get(productCase);
    }

    public Product makeProduct(ProductData productData) {
        if(productData == null)
            return null;
        return new Product(productData,new Category(productData.getCategory()));
    }

    public StoreData getStore(Data data) {
        return stores.get(data);
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
