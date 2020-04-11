package DataAPI;

import Domain.DiscountPolicy;
import Domain.PurchasePolicy;

public class StoreData {

    private String name;
    private PurchasePolicy purchasePolicy;
    private DiscountPolicy discountPolicy;

    public StoreData(String name, PurchasePolicy purchasePolicy, DiscountPolicy discountPolicy) {
        this.name = name;
        this.purchasePolicy = purchasePolicy;
        this.discountPolicy = discountPolicy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PurchasePolicy getPurchasePolicy() {
        return purchasePolicy;
    }

    public void setPurchasePolicy(PurchasePolicy purchasePolicy) {
        this.purchasePolicy = purchasePolicy;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    // Overriding equals() to compare two StoreData objects
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof StoreData)) {
            return false;
        }
        StoreData storeData = (StoreData) obj;
        return (storeData.getName().equals(this.getName()));
        /**
         * TODO - need to add equals for the policy.
         */
    }
}
