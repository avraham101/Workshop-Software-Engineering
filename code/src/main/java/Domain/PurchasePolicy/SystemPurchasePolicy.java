package Domain.PurchasePolicy;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;


public class SystemPurchasePolicy implements PurchasePolicy {

    private int age;

    public SystemPurchasePolicy(int age) {
        this.age = age;
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData) {
        return (paymentData.getAge() >= age);
    }
}
