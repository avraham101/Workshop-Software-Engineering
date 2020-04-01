package Stubs;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

public class SubscribeStub extends Subscribe{

    public SubscribeStub(String userName, String password) {
            super(userName, password);
        }

    @Override
    public boolean login(User user, Subscribe subscribe) {
        return false;
    }

    @Override
    public boolean logout(User user){
        return true;
    }

    @Override
    public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
        return new Store(storeDetails.getName(), new PurchesPolicy(), new DiscountPolicy(),
                new Permission(this), supplySystem, paymentSystem);
    }

    @Override
    public boolean addProductToStore(ProductData productData){
        return true;
    }

}
