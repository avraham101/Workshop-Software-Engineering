package Domain;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

import java.util.List;

public abstract class UserState {
    private Cart cart;

    public UserState() {
        this.cart = new Cart();
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    /**
     * use case 2.3 - - Login
     * @param user - The user who using the system
     * @param subscribe - The user state who need to be set
     * @return true if the user changed his state, otherwise false
     */
    public abstract boolean login(User user,Subscribe subscribe);

    public abstract String getName();

    public abstract String getPassword();

    /**
     * use case 3.1 - Logout
     * @param user - the user who using the system
     * @return true if the user changed his state, otherwise false
     */
    public abstract boolean logout(User user);

    /**
     * use case 3.2 - Open Store
     * @param storeDetails - the details of the the store
     * @param paymentSystem - the external payment system.
     * @param supplySystem - the external supply system.
     * @return The Store that we open, otherwise null;
     */
    public abstract Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem);

    /**
     * use case 3.5
     * @param storeName - The id of the store
     * @param content - The content of the request
     * @return request if success, null else
     */
    public abstract Request addRequest(String storeName, String content);

    /**
     * use case 4.1.1
     * @param productData
     * @return
     */
    public abstract boolean addProductToStore(ProductData productData);

    /**
     * use case 4.1.2
     * @param storeName
     * @param productName
     * @return
     */
    public abstract boolean removeProductFromStore(String storeName, String productName);

    /**
     * use case 4.1.3
     * @param productData
     * @return
     */
    public abstract boolean editProductFromStore(ProductData productData);

    /**
     * use case 4.3
     * @param subscribe
     * @param storeName
     * @return
     */
    public abstract boolean addManager(Subscribe subscribe, String storeName);

    /**
     * use case 4.9.1
     * @param storeName
     */
    public abstract List<Request> viewRequest(String storeName);
}
