package Domain.PurchasePolicy;

import DataAPI.PaymentData;
import Domain.Product;
import Domain.ProductInCart;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class SystemPurchasePolicy implements PurchasePolicy {

    private int age;

    public SystemPurchasePolicy(int age) {
        this.age = age;
    }

    @Override
    public boolean isValid() {
        return (age >= 0);
    }

    @Override
    public List<String> getProducts() {
        return new LinkedList<>();
    }

    /**
     * check if the age is greater than the minimum age
     * @param paymentData - the data of the payment
     * @param country - the country of the user
     * @param products - the products of the basket
     * @return - true if stands in the policy
     */
    @Override
    public boolean standInPolicy(PaymentData paymentData, String country,
                                 HashMap<String, ProductInCart> products) {
        return (paymentData.getAge() >= age);
    }
}
