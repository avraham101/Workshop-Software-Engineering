package Stubs;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.Guest;
import Domain.Store;
import Domain.Subscribe;
import Domain.User;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

public class GuestStub extends Guest {
    @Override
    public boolean login(User user, Subscribe subscribe) {
        return true;
    }

    @Override
    public boolean logout(User user){
        return false;
    }

    @Override
    public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
        return null;
    }

    @Override
    public boolean addProductToStore(ProductData productData){
        return false;
    }
}
