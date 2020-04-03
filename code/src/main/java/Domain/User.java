package Domain;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.List;

public class User {
    private UserState state;

    public User() {
        state=new Guest();
    }

    public User(UserState userState) {
        state = userState;
    }

    public UserState getState() {
        return state;
    }

    /**
     * The function update the user state.
     * use case 2.3 - Login
     * @param state the new State to update
     */
    public void setState(UserState state) {
        this.state = state;
    }

    /**
     * use case 2.3 - Login
     * @param subscribe - The new Subscribe State
     * @return true if succeed to change state, otherwise false.
     */
    public boolean login(Subscribe subscribe) {
        return state.login(this, subscribe);
    }

    public String getUserName() {
        return this.state.getName();
    }

    public String getPassword() {
        return this.state.getPassword();
    }

    /**
     * use case 3.1 - Logout
     * @return true if the user state changed back to guest
     */
    public boolean logout() {
        return state.logout(this);
    }

    /**
     * use case 3.2 - Open Store
     * @param storeDetails - the details of the the store
     * @param paymentSystem - the external payment system.
     * @param supplySystem - the external supply system.
     * @return The Store that we open, otherwise null;
     */
    public Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem) {
        return state.openStore(storeDetails,paymentSystem,supplySystem);
    }

    public boolean addProductToStore(ProductData productData) {
        return state.addProductToStore(productData);
    }

    public boolean removeProductFromStore(String storeName, String productName) {
        return state.removeProductFromStore(storeName,productName);
    }

    public boolean editProductFromStore(ProductData productData) {
        return state.editProductFromStore(productData);
    }

    /**
     * use case 3.5
     * @param storeName
     * @param content
     * @return
     */

    public Request addRequest(String storeName, String content) { return state.addRequest(storeName, content); }

    /**
     * use case 4.5
     * @param subscribe
     * @param storeName
     * @return
     */
    public boolean addManager(Subscribe subscribe, String storeName) {
        return state.addManager(subscribe,storeName);
    }

    /**
     * use case 4.9.1
     * @param storeName
     */
    public List<Request> viewRequest(String storeName) {
        return state.viewRequest(storeName);
    }

    /**
     * use case 4.9.2 -replay to Request
     * @param storeName
     * @param requestID
     * @param content
     * @return true if replay, false else
     */
    public Request replayToRequest(String storeName, int requestID, String content) {
        return state.replayToRequest(storeName, requestID, content);
    }
}
