package Stubs;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.List;

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

    @Override
    public boolean removeProductFromStore(String storeName, String productName) {
        return true;
    }

    @Override
    public boolean editProductFromStore(ProductData productData) {
        return true;
    }

    @Override
    public boolean addManager(Subscribe youngOwner, String storeName) {
        return true;
    }

    @Override
    public boolean addPermissions(List<PermissionType> permissions, String storeName, String userName) {
        return true;
    }

    @Override
    public boolean removePermissions(List<PermissionType> permissions, String storeName, String userName) {
        return true;
    }

    @Override
    public boolean removeManager(String userName, String storeName) {
        return true;
    }

    /**
     * use case 6.4.1 -watch user history
     * @return
     */
    @Override
    public boolean canWatchUserHistory() {
        return false;
    }

    /**
     * use case 4.10, 6.4.2 - watch store history
     * @param storeName - the store name to watch history
     * @return
     */
    @Override
    public boolean canWatchStoreHistory(String storeName) {
        return true;
    }


}
