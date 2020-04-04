package Stubs;

import DataAPI.PaymentData;
import DataAPI.ProductData;
import DataAPI.StoreData;
import Domain.*;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;
import UserTests.UserAllStubsTest;

import java.util.LinkedList;
import java.util.List;

public class UserStub extends User {

    @Override
    public boolean login(Subscribe subscribe) {
        return true;
    }

    @Override
    public boolean logout() {
        return true;
    }

    @Override
    public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
        return new Store(storeDetails.getName(), new PurchesPolicy(), new DiscountPolicy(),
                new Permission(new Subscribe(this.getUserName(), this.getPassword())),
                supplySystem, paymentSystem);
    }

    @Override
    public boolean addProductToStore(ProductData productData) {
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
    public boolean addProductToCart(Store store, Product product, int amount) {
        return true;
    }

    /**
     * use case 3.3 - write review
     * the function check if a product is perchesed
     *
     * @param storeName   - the store name
     * @param productName
     * @return
     */
    @Override
    public boolean isItPurchased(String storeName, String productName) {
        return true;
    }

    @Override
    public boolean buyCart(PaymentData paymentData, String addresToDeliver) {
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

    /**
     * use case 4.10, 6.4.2 watch store history
     * @param storeName - the store name to watch history
     * @return
     */
    @Override
    public boolean canWatchStoreHistory(String storeName) {
        return true;
    }

    /**
     * use case 6.4.1 - watch user history
     * @return
     */
    @Override
    public boolean canWatchUserHistory() {
        return true;
    }

    @Override
    public Request addRequest(String storeName, String content) {
        return new Request(getUserName(), storeName, "temp", 10);
    }

    @Override
    public List<Request> viewRequest(String storeName) {
        List<Request> requests = new LinkedList<>();
        requests.add(new Request(getUserName(), storeName, "temp", 10));
        return requests;
    }

    //@Override
    public Request replayToRequest(String storeName, int requestID, String content) {
        return new Request(getUserName(), storeName, "temp", 10);
    }
}
