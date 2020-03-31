package Domain;

import DataAPI.StoreData;

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
    public abstract Store openStore(StoreData storeDetails);
}
