package Domain;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

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

    public void setState(UserState state) {
        this.state = state;
    }

    public boolean login(Subscribe subscribe) {
        return state.login(this, subscribe);
    }

    public String getUserName() {
        return this.state.getName();
    }

    public String getPassword() {
        return this.state.getPassword();
    }

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
}
