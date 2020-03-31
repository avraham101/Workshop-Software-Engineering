package Domain;

import DataAPI.ProductData;
import DataAPI.StoreData;
import Systems.PaymentSystem.PaymentSystem;
import Systems.SupplySystem.SupplySystem;

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

    public abstract boolean login(User user,Subscribe subscribe);

    public abstract String getName();

    public abstract String getPassword();

    public abstract boolean logout(User user);

    //TODO use case 3.2
    public abstract Store openStore(StoreData storeDetails, PaymentSystem paymentSystem, SupplySystem supplySystem);

    public abstract boolean addProductToStore(ProductData productData);
}
