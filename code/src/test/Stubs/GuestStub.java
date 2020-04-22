package Stubs;

import DataAPI.DeliveryData;
import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.LinkedList;
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
    public Store openStore(StoreData storeDetails) {
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

    @Override
    public void buyCart(PaymentData paymentData, DeliveryData addresToDeliver) {
    }

    /**
     * ues case 3.7 - watch my purchase history
     */
    @Override
    public List<Purchase> watchMyPurchaseHistory() {
        return null;
    }
}
