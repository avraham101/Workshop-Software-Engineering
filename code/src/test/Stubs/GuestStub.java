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

    /**
     * use case 2.3 - Login
     * @param user - The user who using the system
     * @param subscribe - The user state who need to be set
     * @return always true. The user state changed from Guest to Subscribe
     */
    @Override
    public boolean login(User user, Subscribe subscribe) {
        return true;
    }

    /**
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return
     */
    @Override
    public boolean logout(User user){
        return false;
    }

    /**
     * use case 3.2 - open store
     * @param storeDetails - the details of the store
     * @return always null. guest cant open store.
     */
    @Override
    public Store openStore(StoreData storeDetails) {
        return null;
    }

    /**
     * use case 4.1.2  - remove product
     * @param storeName
     * @param productName
     * @return false always
     */
    @Override
    public boolean removeProductFromStore(String storeName, String productName) {
        return false;
    }

    /**
     * use case 4.1.1 -add product
     * @param productData
     * @return false for guest
     */
    @Override
    public boolean addProductToStore(ProductData productData){
        return false;
    }

    /**
     * use case 4.1.3 - edit product details
     * @param productData
     * @return
     */
    @Override
    public boolean editProductFromStore(ProductData productData) {
        return super.editProductFromStore(productData);
    }

    /**
     * use case 4.5 - add manager to store
     * @param youngOwner user to be owner
     * @param storeName
     * @return
     */
    @Override
    public boolean addManager(Subscribe youngOwner, String storeName) {
        return false;
    }

    /**
     * use case 4.6.1 - add permissions
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    @Override
    public boolean addPermissions(List<PermissionType> permissions, String storeName, String userName) {
        return false;
    }

    /**
     * use case 4.6.2 - remove permissions
     * @param permissions
     * @param storeName
     * @param userName
     * @return
     */
    @Override
    public boolean removePermissions(List<PermissionType> permissions, String storeName, String userName) {
        return false;
    }

    /**
     * use case 4.7 - remove manager
     * @param userName
     * @param storeName
     * @return
     */
    @Override
    public boolean removeManager(String userName, String storeName) {
        return false;
    }

    /**
     * use case 2.8 - purchase cart
     * @param paymentData - the payment details
     * @param addresToDeliver - the address to shift
     * @return
     */
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
