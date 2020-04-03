package AcceptanceTests.AcceptanceTestDataObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class PurchaseTestData {
    private HashMap<ProductTestData,Integer> productsAndAmountInPurchase;
    private String purchaseDate;
    private double totalAmount;

    public PurchaseTestData(HashMap<ProductTestData,Integer> productsAndAmountInPurchase,Date date,Double totalAmount){
        this.productsAndAmountInPurchase = productsAndAmountInPurchase;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        this.purchaseDate = dateFormat.format(date);
        this.totalAmount=totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public HashMap<ProductTestData, Integer> getProductsAndAmountInPurchase() {
        return productsAndAmountInPurchase;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

}
