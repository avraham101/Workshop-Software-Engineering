package AcceptanceTests.AcceptanceTestDataObjects.FilterTestData;

public class ProductNameFilterTestData extends FilterTestData {
    private String productName;

    public ProductNameFilterTestData(String productName) {
        super("product name");
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
