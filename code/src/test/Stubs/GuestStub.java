package Stubs;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.List;

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
    public boolean removeProductFromStore(String storeName, String productName) {
        return false;
    }

    @Override
    public boolean addProductToStore(ProductData productData){
        return false;
    }

    @Override
    public boolean editProductFromStore(ProductData productData) {
        return super.editProductFromStore(productData);
    }

    @Override
    public boolean addManager(Subscribe youngOwner, String storeName) {
        return false;
    }

    @Override
    public boolean addPermissions(List<PermissionType> permissions, String storeName, String userName) {
        return false;
    }

    @Override
    public boolean removePermissions(List<PermissionType> permissions, String storeName, String userName) {
        return false;
    }

    @Override
    public boolean removeManager(String userName, String storeName) {
        return false;
    }
}
