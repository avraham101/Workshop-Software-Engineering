package Stubs;

import DataAPI.PaymentData;
import Domain.*;

import java.util.LinkedList;
import java.util.List;

public class CartStub extends Cart {

    /**
     * use case 2.7 - add product
     * @param store - the store to add to cart
     * @param product - the product to add
     * @param amount - the amount of the prduct
     * @return
     */
    @Override
    public boolean addProduct(Store store, Product product, int amount) {
        return true;
    }

    /**
     * use case - 2.8 buy cart
     * the function buy this cart
     * @param paymentData - the payment info
     * @param addressToDeliver  - the address to shift
     * @return
     */
    @Override
    public List<Purchase> buy(PaymentData paymentData, String addressToDeliver) {
        return new LinkedList<>();
    }
}
