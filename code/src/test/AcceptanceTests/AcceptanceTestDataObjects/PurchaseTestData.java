package AcceptanceTests.AcceptanceTestDataObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PurchaseTestData {
    private HashMap<ProductTestData,Integer> productsAndAmountInPurchase;
    private String purchaseDate;

    public PurchaseTestData(HashMap<ProductTestData,Integer> productsAndAmountInPurchase){
        this.productsAndAmountInPurchase = productsAndAmountInPurchase;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        this.purchaseDate = dateFormat.format(date);
    }

    public HashMap<ProductTestData, Integer> getProductsAndAmountInPurchase() {
        return productsAndAmountInPurchase;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }
}
