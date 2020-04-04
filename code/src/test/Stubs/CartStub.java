package Stubs;

import DataAPI.PaymentData;
import Domain.*;

import java.util.LinkedList;
import java.util.List;

public class CartStub extends Cart {

    @Override
    public boolean addProduct(Store store, Product product, int amount) {
        return true;
    }

    @Override
    public List<Purchase> buy(PaymentData paymentData, String addresToDeliver) {
        return new LinkedList<>();
    }
}
