package Stubs;

import DataAPI.DeliveryData;
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
    public void buy(PaymentData paymentData, DeliveryData addresToDeliver) {
    }
}
