package Data;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import DataAPI.StoreData;
import Domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestData {
    private HashMap<Data, Subscribe> users;
    private HashMap<Data, ProductData> productsData;
    private HashMap<Data, StoreData> stores;
    private HashMap<Data, List<Discount>> discounts;
    private HashMap<Data, HashMap<ProductData, Integer>> basket;


    public TestData() {
        setUpUsers();
        setUpDiscountData();
        setUpProductData();
        setUpStoreData();
        setUpBasketData();

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

    public Subscribe getSubscribe(Data data) {
        return users.get(data);
    }

    public ProductData getProductData(Data productCase){
        return productsData.get(productCase);
    }

    public Product getProuduct(Data data) {
        ProductData productData = getProductData(data);
        if(productData==null)
            return null;
        return new Product(productData,new Category(productData.getCategory()));
    }

    public StoreData getStore(Data data) {
        return stores.get(data);
    }

    public List<Discount> getDiscounts(Data data){
        return  discounts.get(data);
    }
}
