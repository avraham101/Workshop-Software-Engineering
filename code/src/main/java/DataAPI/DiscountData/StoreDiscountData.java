package DataAPI.DiscountData;

public class StoreDiscountData implements DiscountData {
    private int minAmount;
    private double percentage;

    public StoreDiscountData(int minAmount, double percentage) {
        this.minAmount = minAmount;
        this.percentage = percentage;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
