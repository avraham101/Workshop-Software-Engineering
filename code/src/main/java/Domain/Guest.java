package Domain;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.List;

public class Guest extends UserState {

    /**
     * use case 2.3 - Login
     * @param user - The user who using the system
     * @param subscribe - The user state who need to be set
     * @return always true. The user state changed from Guest to Subscribe
     */
    @Override
    public boolean login(User user, Subscribe subscribe) {
        user.setState(subscribe);
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    /**
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return return always false, guest cant be logged from the system, he already offline
     */
    @Override
    public boolean logout(User user) {
        return false;
    }

    /**
     * use case 3.2
     * @param storeDetails - the details of the store
     * @param paymentSystem - the payment system
     * @param supplySystem - the supply system
     * @return always null. guest cant open store.
     */
    @Override
    public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
        return null;
    }

    /**
     * use case 3.5
     * @param storeName - The id of the store
     * @param content - The content of the request
     * @return true if success, false else
     */
    @Override
    public Request addRequest(String storeName, String content){
        return null;
    }

    /**
     * use case 4.1.1 -add product
     * @param productData
     * @return false for guest
     */
    @Override
    public boolean addProductToStore(ProductData productData) {
        return false;
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
     * use case 4.1.3
     * @param productData
     * @return
     */
    @Override
    public boolean editProductFromStore(ProductData productData) {
        return false;
    }

    /**
     * use case 3.3 - write review
     * the function check if the product is purchased
     * @param storeName - the store name
     * @param productName - the product name
     * @return always false, dosent have purchase history
     */
    @Override
    public boolean isItPurchased(String storeName, String productName) {
        return false;
    }

    /**
     * use case 4.5
     * @param youngOwner user to be owner
     * @param storeName
     * @return
     */
    @Override
    public boolean addManager(Subscribe youngOwner, String storeName) {
        return false;
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
        return false;
    }

    @Override
    public boolean removePermissions(List<PermissionType> permissions, String storeName, String userName) {
        return false;
    }
}
