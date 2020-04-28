package AcceptanceTests.AcceptanceTestDataObjects;

public class DiscountTestData {
    private double percentage;
    private String product;
    private int id;

    public DiscountTestData(double percentage, String product) {
        this.percentage = percentage;
        this.product=product;
        this.id=-1;
    }

    public DiscountTestData(double percentage, String product, int id) {
        this.percentage = percentage;
        this.product = product;
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
