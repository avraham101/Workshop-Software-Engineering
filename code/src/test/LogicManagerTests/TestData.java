package LogicManagerTests;

import DataAPI.ProductData;
import DataAPI.PurchaseTypeData;
import Domain.Discount;

import java.util.ArrayList;
import java.util.HashMap;

public class TestData {
    private HashMap<String, ProductData> products;

    public TestData() {
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
        products.put("sameName",new ProductData("peanuts","store","category1"
                ,null,new ArrayList<Discount>(),12,101, PurchaseTypeData.IMMEDDIATE));
    }

    public ProductData getProduct(String productCase){
        return products.get(productCase);
    }
}
