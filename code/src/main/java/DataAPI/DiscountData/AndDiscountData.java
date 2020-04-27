package DataAPI.DiscountData;

import java.util.List;

public class AndDiscountData implements DiscountData {
    private List<DiscountData> discounts;

    public AndDiscountData(List<DiscountData> discounts) {
        this.discounts=discounts;
    }

    public List<DiscountData> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountData> discounts) {
        this.discounts = discounts;
    }
}
