package DataAPI;

import Domain.DiscountPolicy;
import Domain.PurchasePolicy1;

public class StoreData {

    private String name;
    private PurchasePolicy1 purchasePolicy1;
    private DiscountPolicy discountPolicy;

    public StoreData(String name, PurchasePolicy1 purchasePolicy1, DiscountPolicy discountPolicy) {
        this.name = name;
        this.purchasePolicy1 = purchasePolicy1;
        this.discountPolicy = discountPolicy;
    }

    // ============================ getters & setters ============================ //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PurchasePolicy1 getPurchasePolicy1() {
        return purchasePolicy1;
    }

    public void setPurchasePolicy1(PurchasePolicy1 purchasePolicy1) {
        this.purchasePolicy1 = purchasePolicy1;
    }

    public DiscountPolicy getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    // ============================ getters & setters ============================ //

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
