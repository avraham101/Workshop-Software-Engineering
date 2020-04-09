package Stubs;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.HashMap;

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
    public boolean addReview(Review review) {
        return true;
    }

    /**
     * use case 2.8 - buy cart
     * @param list - the products
     * @return
     */
    @Override
    public boolean isAvailableProducts(HashMap<Product, Integer> list) {
        return true;
    }

    @Override
    public Purchase purches(PaymentData paymentData, DeliveryData deliveryData) {
        return null;
    }
}
