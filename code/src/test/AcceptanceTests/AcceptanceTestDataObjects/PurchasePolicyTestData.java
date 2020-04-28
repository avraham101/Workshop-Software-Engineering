package AcceptanceTests.AcceptanceTestDataObjects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchasePolicyTestData that = (PurchasePolicyTestData) o;

        if (that.maxAmount != maxAmount) return false;
        return true;
    }
}
