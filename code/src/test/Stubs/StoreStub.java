package Stubs;

import DataAPI.ProductData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

public class StoreStub extends Store {

    public StoreStub(String name, PurchesPolicy purchesPolicy, DiscountPolicy discount, Permission permissions, SupplySystem supplySystem, PaymentSystem paymentSystem) {
        super(name, purchesPolicy, discount, permissions, supplySystem, paymentSystem);
    }

    @Override
    public boolean addProduct(ProductData productData) {
        return true;
    }

    @Override
    public boolean removeProduct(String productName) {
        return true;
    }

    @Override
    public boolean editProduct(ProductData productData) {
        return true;
    }

    /**
     * use case 3.3 write review
     * @param review - the review
     * @return ture if the review add to store, otherwise false;
     */
    @Override
    public void addReview(Review review) {

    }
}
