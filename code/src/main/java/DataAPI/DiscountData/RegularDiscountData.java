package DataAPI.DiscountData;

public class RegularDiscountData implements DiscountData{
    private String product;
    private double percentage;

    public RegularDiscountData(String product, double percentage) {
        this.product = product;
        this.percentage = percentage;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
