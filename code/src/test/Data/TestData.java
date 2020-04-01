package Data;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import DataAPI.StoreData;
import Domain.Discount;
import Domain.DiscountPolicy;
import Domain.PurchesPolicy;

import Data.Data;
import Domain.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestData {
    private HashMap<Data, Subscribe> users;
    private HashMap<Data, ProductData> products;
    private HashMap<Data, StoreData> stores;
    private HashMap<Data, List<Discount>> discounts;

    public TestData() {
        setUpUsers();
        setUpDiscountData();
        setUpProductData();
        setUpStoreData();

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
        products = new HashMap<>();
        //change data type to enum
        products.put(Data.VALID,new ProductData("peanuts","Store","category"
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.EDIT,new ProductData("peanuts","Store","categoryYuval"
                ,null,new ArrayList<Discount>(),3,11, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.NULL_NAME,new ProductData(null,"Store","category"
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.WRONG_STORE,new ProductData("peanuts","$storeBBB$","category"
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.NULL_CATEGORY,new ProductData("peanuts","Store",null
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.NULL_DISCOUNT, new ProductData("peanuts","Store","category"
                ,null,null,1,10, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.NEGATIVE_AMOUNT,new ProductData("peanuts","Store","category"
                ,null,new ArrayList<Discount>(),-1,10, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.NEGATIVE_PRICE,new ProductData("peanuts","Store","category"
                ,null,new ArrayList<Discount>(),1,-10, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.NULL_PURCHASE,new ProductData("peanuts","Store","category"
                ,null,new ArrayList<Discount>(),1,10, null));
        products.put(Data.WRONG_DISCOUNT,new ProductData("peanuts","Store","category"
                ,null,discounts.get(Data.NULL_DISCOUNT),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.NEGATIVE_PERCENTAGE,new ProductData("peanuts","Store","category"
                ,null,discounts.get(Data.NEGATIVE_PERCENTAGE),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.OVER_100_PERCENTAGE,new ProductData("peanuts","Store","category"
                ,null,discounts.get(Data.OVER_100_PERCENTAGE),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.SAME_NAME,new ProductData("peanuts","store","category1"
                ,null,new ArrayList<Discount>(),12,101, PurchaseTypeData.IMMEDDIATE));
        products.put(Data.WRONG_NAME,new ProductData("peanuts1","Store","category"
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

    public Subscribe getSubscribe(Data data) {
        return users.get(data);
    }

    public ProductData getProduct(Data productCase){
        return products.get(productCase);
    }

    public StoreData getStore(Data data) {
        return stores.get(data);
    }

    public List<Discount> getDiscounts(Data data){
        return  discounts.get(data);
    }
}
