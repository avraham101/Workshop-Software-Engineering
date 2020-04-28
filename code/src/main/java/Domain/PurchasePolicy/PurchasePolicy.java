package Domain.PurchasePolicy;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import Domain.Product;

import java.util.HashMap;
import java.util.List;


public interface PurchasePolicy {

    /**
     * check if the buy is stand in the policy terms
     * @param paymentData - the data of the payment
     * @param deliveryData - the data of the delivery
     * @return - true if stand, false if not
     */
    boolean standInPolicy(PaymentData paymentData, DeliveryData deliveryData);

    /**
     * check that the policy is valid
     * @return - true if valid, false if not
     */
    boolean isValid();

    /**
     * get the list of the products of each policy
     * @return - List of products
     */
    List<String> getProducts();

    boolean standInPolicy(PaymentData paymentData, String country, HashMap<Product, Integer> products);

}
