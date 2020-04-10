package AcceptanceTests.AcceptanceTestDataObjects;

import Discount.DiscountTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseTestData {
    private HashMap<ProductTestData,Integer> productsAndAmountInPurchase;
    private String purchaseDate;
    private double totalAmount;

    public PurchaseTestData(HashMap<ProductTestData,Integer> productsAndAmountInPurchase,Date date,Double totalAmount){
        this.productsAndAmountInPurchase = productsAndAmountInPurchase;
        this.purchaseDate = formatDate(date);
        this.totalAmount = totalAmount;
    }

    private String formatDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return dateFormat.format(date);
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

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = formatDate(purchaseDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaseTestData that = (PurchaseTestData) o;

        if (Double.compare(that.totalAmount, totalAmount) != 0) return false;
        return productsAndAmountInPurchase != null ? productsAndAmountInPurchase.equals(that.productsAndAmountInPurchase) : that.productsAndAmountInPurchase == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = productsAndAmountInPurchase.hashCode();
        temp = Double.doubleToLongBits(totalAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public double calculateTotalAmount() {
        double totalAmount = 0;
        for(Map.Entry<ProductTestData,Integer> prodAndAmounts : productsAndAmountInPurchase.entrySet()) {
            double productPrice = prodAndAmounts.getKey().getPrice();
            int amount = prodAndAmounts.getValue();
            for (DiscountTestData discount : prodAndAmounts.getKey().getDiscounts())
                productPrice = productPrice * (1 - discount.getPercentage());
            totalAmount+= productPrice * amount;
        }
        return totalAmount;
    }
}
