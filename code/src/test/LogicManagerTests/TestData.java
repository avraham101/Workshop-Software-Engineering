package LogicManagerTests;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import DataAPI.StoreData;
import Domain.Discount;
import Domain.DiscountPolicy;
import Domain.PurchesPolicy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TestData {
    private HashMap<Data, ProductData> products;
    private HashMap<Data, StoreData> stores;

    public TestData() {
        products = new HashMap<>();
        //change data type to enum
        products.put(Data.VALID,new ProductData("peanuts","Store","category"
        ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
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
        setUpStoreData();
    }

    private void setUpStoreData() {
        stores = new HashMap<>();
        stores.put(Data.VALID,new StoreData("Store",new PurchesPolicy(), new DiscountPolicy()));
        stores.put(Data.NULL_NAME, new StoreData(null,new PurchesPolicy(), new DiscountPolicy()));
        stores.put(Data.NULL_PURCHASE, new StoreData("Store",null, new DiscountPolicy()));
        stores.put(Data.NULL_DISCOUNT, new StoreData("Store",new PurchesPolicy(), null));
        products.put(Data.SAME_NAME,new ProductData("peanuts","store","category1"
                ,null,new ArrayList<Discount>(),12,101, PurchaseTypeData.IMMEDDIATE));
    }

    public ProductData getProduct(Data productCase){
        return products.get(productCase);
    }

    public StoreData getStore(Data data) {
        return stores.get(data);
    }
}
