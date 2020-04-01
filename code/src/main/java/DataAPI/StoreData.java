package DataAPI;

import Domain.DiscountPolicy;
import Domain.PurchesPolicy;

public class StoreData {

    private String name;
    private PurchesPolicy purchesPolicy;
    private DiscountPolicy discountPolicy;

    public StoreData(String name, PurchesPolicy purchesPolicy, DiscountPolicy discountPolicy) {
        this.name = name;
        this.purchesPolicy = purchesPolicy;
        this.discountPolicy = discountPolicy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PurchesPolicy getPurchesPolicy() {
        return purchesPolicy;
    }

    public void setPurchesPolicy(PurchesPolicy purchesPolicy) {
        this.purchesPolicy = purchesPolicy;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
