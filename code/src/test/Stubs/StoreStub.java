package Stubs;

import DataAPI.ProductData;
import Domain.DiscountPolicy;
import Domain.Permission;
import Domain.PurchesPolicy;
import Domain.Store;
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
}
