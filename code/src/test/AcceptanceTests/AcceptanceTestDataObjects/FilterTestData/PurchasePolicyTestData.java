package AcceptanceTests.AcceptanceTestDataObjects.FilterTestData;

public class PurchasePolicyTestData {

    private int maxAmount;

    public PurchasePolicyTestData(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }
}
