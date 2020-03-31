package LogicManagerTests;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import DataAPI.StoreData;
import Domain.Discount;
import Domain.DiscountPolicy;
import Domain.PurchesPolicy;

import java.util.ArrayList;
import java.util.HashMap;

public class TestData {
    private HashMap<String, ProductData> products;
    private HashMap<Data, StoreData> stores;

    public TestData() {
        products = new HashMap<>();
        //change data type to enum
        products.put("valid",new ProductData("peanuts","store","category"
        ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put("nullName",new ProductData(null,"store","category"
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put("wrongStore",new ProductData("peanuts","$storeBBB$","category"
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put("nullCategory",new ProductData("peanuts","store",null
                ,null,new ArrayList<Discount>(),1,10, PurchaseTypeData.IMMEDDIATE));
        products.put("nullDiscount",new ProductData("peanuts","store","category"
                ,null,null,1,10, PurchaseTypeData.IMMEDDIATE));
        products.put("negativeAmount",new ProductData("peanuts","store","category"
                ,null,new ArrayList<Discount>(),-1,10, PurchaseTypeData.IMMEDDIATE));
        products.put("negativePrice",new ProductData("peanuts","store","category"
                ,null,new ArrayList<Discount>(),1,-10, PurchaseTypeData.IMMEDDIATE));
        products.put("nullPurchase",new ProductData("peanuts","store","category"
                ,null,new ArrayList<Discount>(),1,10, null));
        setUpStoreData();
    }

    private void setUpStoreData() {
        stores = new HashMap<>();
        stores.put(Data.VALID,new StoreData("Store",new PurchesPolicy(), new DiscountPolicy()));
        stores.put(Data.NULL_NAME, new StoreData(null,new PurchesPolicy(), new DiscountPolicy()));
        stores.put(Data.NULL_PURCHASE, new StoreData("Store",null, new DiscountPolicy()));
        stores.put(Data.NULL_DISCOUNT, new StoreData("Store",new PurchesPolicy(), null));
    }

    public ProductData getProduct(String productCase){
        return products.get(productCase);
    }

    public StoreData getStore(Data data) {
        return stores.get(data);
    }
}
