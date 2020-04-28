package AcceptanceTests.AcceptanceTestDataObjects;

public class DiscountTestData {
    private double percentage;
    private String product;

    public DiscountTestData(double percentage, String product) {
        this.percentage = percentage;
        this.product=product;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DiscountTestData that = (DiscountTestData) o;

        if (Double.compare(that.percentage, percentage) != 0) return false;
        return product != null ? product.equals(that.product) : that.product == null;
    }
}
