package Stubs;

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
