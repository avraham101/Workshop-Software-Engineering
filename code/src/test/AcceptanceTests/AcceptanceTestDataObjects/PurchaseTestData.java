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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseTestData that = (PurchaseTestData) o;

        if (Double.compare(that.totalAmount, totalAmount) != 0) return false;
        if (productsAndAmountInPurchase != null ? !productsAndAmountInPurchase.equals(that.productsAndAmountInPurchase) : that.productsAndAmountInPurchase != null)
            return false;
        return purchaseDate != null ? purchaseDate.equals(that.purchaseDate) : that.purchaseDate == null;
    }
}
