package Stubs;

import DataAPI.PaymentData;
import Domain.Basket;
import Domain.Cart;
import Domain.Purchase;
import Domain.Store;

import java.util.List;

public class CartStub extends Cart {

    @Override
    public Basket getBasket(String storeName) {
        return null;
    }

    @Override
    public List<Purchase> buy(PaymentData paymentData, String addresToDeliver) {
        return null;
    }
}
