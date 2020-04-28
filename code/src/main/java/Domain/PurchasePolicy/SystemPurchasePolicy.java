package Domain.PurchasePolicy;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import java.util.LinkedList;
import java.util.List;


public class SystemPurchasePolicy implements PurchasePolicy {

    private int age;

    public SystemPurchasePolicy(int age) {
        this.age = age;
    }

    @Override
    public boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData) {
        return (paymentData.getAge() >= age);
    }

    @Override
    public boolean isValid() {
        return (age >= 0);
    }

    @Override
    public List<String> getProducts() {
        return new LinkedList<>();
    }
}
