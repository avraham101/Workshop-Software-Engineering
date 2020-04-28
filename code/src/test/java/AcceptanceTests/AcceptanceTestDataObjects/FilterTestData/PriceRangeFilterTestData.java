package AcceptanceTests.AcceptanceTestDataObjects.FilterTestData;

public class PriceRangeFilterTestData extends FilterTestData {
    private double lowestPrice;
    private double highestPrice;

    public PriceRangeFilterTestData(double lowestPrice, double highestPrice) {
        super(FilterType.PRICE_RANGE);
        this.lowestPrice = lowestPrice;
        this.highestPrice = highestPrice;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public void setLowestPrice(double lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public void setHighestPrice(double highestPrice) {
        this.highestPrice = highestPrice;
    }
}
