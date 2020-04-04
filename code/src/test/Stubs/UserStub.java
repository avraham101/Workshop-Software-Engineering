package Stubs;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;
import UserTests.UserAllStubsTest;

import java.util.List;

public class UserStub extends User {

    @Override
    public boolean login(Subscribe subscribe) {
        return true;
    }

    @Override
    public boolean logout(){
        return true;
    }

    @Override
    public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
        return new Store(storeDetails.getName(),new PurchesPolicy(),new DiscountPolicy(),
                new Permission(new Subscribe(this.getUserName(),this.getPassword())),
                supplySystem,paymentSystem);
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
    public boolean addManager(Subscribe subscribe, String storeName) {
        return true;
    }

    @Override
    public boolean addProductToCart(Store store, Product product, int amount) { return true; }

    /**
     * use case 3.3 - write review
     * the function check if a product is perchesed
     * @param storeName - the store name
     * @param productName
     * @return
     */
    @Override
    public boolean isItPurchased(String storeName, String productName) {
        return true;
    }

    /**
     * use case 4.6.1
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    @Override
    public boolean addPermissions(List<PermissionType> permissions, String storeName, String userName) {
        return true;
    }

    /**
     * use case 4.6.2
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    @Override
    public boolean removePermissions(List<PermissionType> permissions, String storeName, String userName) {
        return true;
    }

    /**
     * use case 4.7 - remove manager
     * @param userName
     * @param storeName
     * @return
     */
    @Override
    public boolean removeManager(String userName, String storeName) {
        return true;
    }
}
